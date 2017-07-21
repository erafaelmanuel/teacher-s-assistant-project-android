package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.ClazzDatabaseHelperException;
import com.remswork.classmanager.helper.service.ScheduleService;
import com.remswork.classmanager.helper.service.SectionService;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.helper.service.impl.ScheduleServiceImpl;
import com.remswork.classmanager.helper.service.impl.SectionServiceImpl;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Schedule;
import com.remswork.classmanager.model.clazz.Section;
import com.remswork.classmanager.model.clazz.Subject;
import com.remswork.classmanager.model.grade.Syllabus;
import com.remswork.classmanager.model.clazz.Teacher;
import com.remswork.classmanager.model.grade.Term;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class ClazzDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_class";
    private static final String COL_1 = "id";
    private static final String COL_2 = "teacher_id";
    private static final String COL_3 = "subject_id";
    private static final String COL_4 = "section_id";
    private static final String COL_5 = "termType";
    private static final String COL_6 = "syllabus_id";

    private TeacherService teacherService;
    private SubjectService subjectService;
    private SectionService sectionService;
    private ScheduleService scheduleService;

    public ClazzDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        teacherService = new TeacherServiceImpl(context);
        subjectService = new SubjectServiceImpl(context);
        sectionService = new SectionServiceImpl(context);
        scheduleService = new ScheduleServiceImpl(context);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s INTEGER);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addClazz(Clazz clazz){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, clazz.getId());
            contentValues.put(COL_2, clazz.getId());
            contentValues.put(COL_3, clazz.getId());
            contentValues.put(COL_4, clazz.getId());

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
                    int clazzId;
                    Teacher teacher;
                    Subject subject;
                    Section section;
                    List<Schedule> listOfSchedule;
                    String termType;
                    List<Term> listOfTerm;
                    Syllabus syllabus;

                    clazzId = cursor.getInt(0);
                    teacher = teacherService.getTeacherById(cursor.getInt(1));
                    subject = subjectService.getSubjectById(cursor.getInt(2));
                    section = sectionService.getSectionById(cursor.getInt(3));
                    listOfSchedule = scheduleService.getScheduleByClazzId(clazzId);
                    termType = cursor.getString(4);
                    listOfTerm = null;
                    syllabus = null;


                    clazz.setId(clazzId);
                    clazz.setTeacher(teacher);
                    clazz.setSubject(subject);
                    clazz.setSection(section);
                    clazz.setListOfSchedule(listOfSchedule);
                    clazz.setTermType(termType);
                    clazz.setListOfTerm(listOfTerm);
                    clazz.setSyllabus(syllabus);

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
}
