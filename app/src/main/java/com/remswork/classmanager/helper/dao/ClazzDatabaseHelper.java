package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.exception.ClazzDatabaseHelperException;
import com.remswork.classmanager.helper.service.SectionService;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.helper.service.impl.SectionServiceImpl;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;
import com.remswork.classmanager.model.Clazz;
import com.remswork.classmanager.model.Section;
import com.remswork.classmanager.model.Subject;
import com.remswork.classmanager.model.Teacher;

/**
 * Created by Rafael on 7/18/2017.
 */

public class ClazzDatabaseHelper extends DatabaseHelper {

    private final String TABLE_NAME = "tbl_class";
    private final String COL_1 = "id";
    private final String COL_2 = "teacher_id";
    private final String COL_3 = "subject_id";
    private final String COL_4 = "section_id";
    private TeacherService teacherService;
    private SubjectService subjectService;
    private SectionService sectionService;

    public ClazzDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        teacherService = new TeacherServiceImpl(context);
        subjectService = new SubjectServiceImpl(context);
        sectionService = new SectionServiceImpl(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s INTEGER, %s INTEGER, %s INTEGER);", TABLE_NAME, COL_1, COL_2, COL_3, COL_4);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public void upgradeTable(boolean isYes){
        if(isYes)
            onUpgrade(getWritableDatabase(), VERSION - 1, VERSION);
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

            if(cursor.moveToNext()){

                int clazzId = cursor.getInt(0);
                Teacher teacher = teacherService.getTeacherById(cursor.getInt(1));
                Subject subject = subjectService.getSubjectById(cursor.getInt(2));
                Section section = sectionService.getSectionById(cursor.getInt(3));

                clazz.setId(clazzId);
                clazz.setTeacher(teacher);
                clazz.setSubject(subject);
                clazz.setSection(section);
                cursor.close();
                return clazz;
            }else
                throw new ClazzDatabaseHelperException("No class found with ID : " + id);

        }catch (ClazzDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }
}
