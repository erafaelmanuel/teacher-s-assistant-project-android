package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.ClazzDatabaseHelperException;
import com.remswork.classmanager.exception.SubjectDatabaseHelperException;
import com.remswork.classmanager.helper.service.ScheduleService;
import com.remswork.classmanager.helper.service.SectionService;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.SyllabusService;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.helper.service.TermService;
import com.remswork.classmanager.helper.service.impl.ScheduleServiceImpl;
import com.remswork.classmanager.helper.service.impl.SectionServiceImpl;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.helper.service.impl.SyllabusServiceImpl;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;
import com.remswork.classmanager.helper.service.impl.TermServiceImpl;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Schedule;
import com.remswork.classmanager.model.clazz.Section;
import com.remswork.classmanager.model.clazz.Subject;
import com.remswork.classmanager.model.clazz.Teacher;
import com.remswork.classmanager.model.grade.Term;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Rafael on 7/18/2017.
 */

public class ClazzDatabaseHelper extends DatabaseHelper {

    protected static final String TABLE_NAME = "tbl_class";
    protected static final String COL_1 = "id";
    protected static final String COL_2 = "teacher_id";
    protected static final String COL_3 = "subject_id";
    protected static final String COL_4 = "section_id";
    protected static final String COL_5 = "termType";
    protected static final String COL_6 = "syllabus_id";

    private TeacherService teacherService;
    private SubjectService subjectService;
    private SectionService sectionService;
    private ScheduleService scheduleService;
    private ScheduleDatabaseHelper scheduleDatabaseHelper;

    private TermService termService;
    private SyllabusService syllabusService;

    public ClazzDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        teacherService = new TeacherServiceImpl(context);
        subjectService = new SubjectServiceImpl(context);
        sectionService = new SectionServiceImpl(context);
        scheduleService = new ScheduleServiceImpl(context);
        termService = new TermServiceImpl(context);
        syllabusService = new SyllabusServiceImpl(context);
        scheduleDatabaseHelper = new ScheduleDatabaseHelper(context);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addClazz(Clazz clazz){
        try{
            onUpgrade(getWritableDatabase(),VERSION-1, VERSION);

            //Temporary
            int id =(int) (Math.random() * 900000);
            Section section = new Section();
            section.setId((int) (Math.random() * 900000));

            if(clazz.getSection() != null) {
                section.setSectionName(clazz.getSection().getSectionName());
                section.setDepartment(clazz.getSection().getDepartment());
                section.setYear(clazz.getSection().getYear());
            }

            //Temp but not
            sectionService.addSection(section);
            //Temp and yes
            scheduleDatabaseHelper.onUpgrade(getWritableDatabase(),VERSION-1, VERSION);
            for(Schedule schedule : clazz.getListOfSchedule()){
                schedule.setClazzId(id);
                scheduleDatabaseHelper.addSchedule(schedule);
            }

            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, id);
            contentValues.put(COL_2, clazz.getTeacher().getId());
            contentValues.put(COL_3, clazz.getSubject() != null ? clazz.getSubject().getId(): 0);
            contentValues.put(COL_4, section.getId());
            contentValues.put(COL_5, clazz.getTermType());

            if(db.insert(TABLE_NAME, null, contentValues) != -1)
                return true;
            else
                throw new ClazzDatabaseHelperException("Class can't be added");
        }catch (ClazzDatabaseHelperException e){
            onUpgrade(getWritableDatabase(), VERSION - 1, VERSION);
            e.printStackTrace();
            return false;
        }
    }

    public Clazz getClazzById(final int id){
        try{
            Clazz clazz = new Clazz();
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            try {
                if (cursor.moveToNext()) {
                    int clazzId = cursor.getInt(0);
                    Teacher teacher = teacherService.getTeacherById(cursor.getInt(1));
                    Subject subject = subjectService.getSubjectById(cursor.getInt(2));
                    Section section = sectionService.getSectionById(cursor.getInt(3));
                    List<Schedule> listOfSchedule = scheduleService.getScheduleByClazzId(clazzId);
                    String termType = cursor.getString(4);
                    List<Term> listOfTerm = null;

                    clazz.setId(clazzId);
                    clazz.setTeacher(teacher);
                    clazz.setSubject(subject);
                    clazz.setSection(section);
                    clazz.setListOfSchedule(listOfSchedule);
                    clazz.setTermType(termType);
                    clazz.setListOfTerm(listOfTerm);
                    cursor.close();
                    return clazz;
                } else
                    throw new ClazzDatabaseHelperException("No class found with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Class table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION - 1, VERSION);
            e.printStackTrace();
            return null;
        }catch (ClazzDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Clazz> getAllClazz(){
        List<Clazz> clazzes = new ArrayList<Clazz>();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE 1;", TABLE_NAME);
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.getCount() > 0) {
                    while(cursor.moveToNext()) {
                        int clazzId = cursor.getInt(0);
                        Teacher teacher = teacherService.getTeacherById(cursor.getInt(1));
                        Subject subject = subjectService.getSubjectById(cursor.getInt(2));
                        Section section = sectionService.getSectionById(cursor.getInt(3));
                        List<Schedule> listOfSchedule = scheduleService
                                .getScheduleByClazzId(clazzId);
                        String termType = cursor.getString(4);
                        List<Term> listOfTerm = null;

                        Clazz clazz = new Clazz();
                        clazz.setId(clazzId);
                        clazz.setTeacher(teacher);
                        clazz.setSubject(subject);
                        clazz.setSection(section);
                        clazz.setListOfSchedule(listOfSchedule);
                        clazz.setTermType(termType);
                        clazz.setListOfTerm(listOfTerm);
                        clazzes.add(clazz);
                    }
                    cursor.close();
                    return clazzes;
                } else
                    throw new ClazzDatabaseHelperException("No class found");
            }catch (RuntimeException e){
                throw new SQLiteException("Class table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION - 1, VERSION);
            e.printStackTrace();
            return clazzes;
        }catch (ClazzDatabaseHelperException e){
            e.printStackTrace();
            return clazzes;
        }
    }

    public boolean deleteClazzById(final int id){
        try{
            String whereClause = String.format("%s = ?", COL_1);
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (db.delete(TABLE_NAME, whereClause, new String[]{String.valueOf(id)}) > 0)
                    return true;
                else
                    throw new ClazzDatabaseHelperException("Failed to delete clazz");
            }catch (RuntimeException e){
                throw new SQLiteException("Clazz table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (ClazzDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }
}
