package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.TeacherDatabaseHelperException;
import com.remswork.classmanager.model.clazz.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafael on 7/4/2017.
 */

public class TeacherDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_teacher";
    private static final String COL_1 = "id";
    private static final String COL_2 = "first_name";
    private static final String COL_3 = "last_name";
    private static final String COL_4 = "email";
    private static final String COL_5 = "password";

    public TeacherDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addTeacher(Teacher teacher){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, teacher.getId());
            contentValues.put(COL_2, teacher.getFirstName());
            contentValues.put(COL_3, teacher.getLastName());
            contentValues.put(COL_4, teacher.getEmail());
            contentValues.put(COL_5, teacher.getPassword());

            if (db.insert(TABLE_NAME, null, contentValues) != -1)
                return true;
            else
                throw new TeacherDatabaseHelperException("Teacher can't be added");
        }catch (TeacherDatabaseHelperException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }
    }

    public List<Teacher> getListOfTeacher(){
        List<Teacher> listOfTeacher = new ArrayList<Teacher>();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE 1;", TABLE_NAME);
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Teacher teacher = new Teacher();
                        teacher.setId(cursor.getInt(0));
                        teacher.setFirstName(cursor.getString(1));
                        teacher.setLastName(cursor.getString(2));
                        teacher.setEmail(cursor.getString(3));
                        teacher.setPassword(cursor.getString(4));
                        listOfTeacher.add(teacher);
                    }
                    cursor.close();
                    return listOfTeacher;
                } else
                    throw new TeacherDatabaseHelperException("No Teacher found");
            }catch (RuntimeException  e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return listOfTeacher;
        }catch (TeacherDatabaseHelperException e){
            e.printStackTrace();
            return listOfTeacher;
        }
    }

    public Teacher getTeacherById(final int id){
        Teacher teacher = new Teacher();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ?;", TABLE_NAME, COL_1);
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        teacher.setId(cursor.getInt(0));
                        teacher.setFirstName(cursor.getString(1));
                        teacher.setLastName(cursor.getString(2));
                        teacher.setEmail(cursor.getString(3));
                        teacher.setPassword(cursor.getString(4));
                    }
                    cursor.close();
                    return teacher;
                } else
                    throw new TeacherDatabaseHelperException("No Teacher found with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return teacher;
        }catch(TeacherDatabaseHelperException e){
            e.printStackTrace();
            return teacher;
        }
    }

    public Teacher getTeacherByEmail(final String email){
        Teacher teacher = new Teacher();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ?;", TABLE_NAME, COL_4);
            Cursor cursor = db.rawQuery(query, new String[]{email});
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        teacher.setId(cursor.getInt(0));
                        teacher.setFirstName(cursor.getString(1));
                        teacher.setLastName(cursor.getString(2));
                        teacher.setEmail(cursor.getString(3));
                        teacher.setPassword(cursor.getString(4));
                    }
                    cursor.close();
                    return teacher;
                } else
                    throw new TeacherDatabaseHelperException(
                            "No Teacher found with Email : " + email);
            }catch (RuntimeException e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return teacher;
        }catch(TeacherDatabaseHelperException e){
            e.printStackTrace();
            return teacher;
        }
    }

    public HashMap<String, Object> getTeacherAuthenticate(String email, String password){
        HashMap<String, Object> map = new HashMap<String, Object>();
        try{
            Teacher teacher = new Teacher();
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? LIMIT 1;",
                    TABLE_NAME, COL_4, COL_5);
            Cursor cursor = db.rawQuery(query, new String[]{email, password});
            try {
                if (cursor.moveToNext()) {
                    teacher.setId(cursor.getInt(0));
                    teacher.setFirstName(cursor.getString(1));
                    teacher.setLastName(cursor.getString(2));
                    teacher.setEmail(cursor.getString(3));
                    teacher.setPassword(cursor.getString(4));

                    map.put("isSuccess", true);
                    map.put("getTeacher", teacher);
                    cursor.close();
                    return map;
                } else
                    throw new TeacherDatabaseHelperException("Email or password fail to match");
            }catch (RuntimeException e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            map.put("isSuccess",false);
            return  map ;
        }catch(TeacherDatabaseHelperException e){
            e.printStackTrace();
            map.put("isSuccess",false);
            return  map ;
        }
    }

    public boolean updateTeacherById(final int id, final Teacher newTeacher){
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newTeacher.getId());
            contentValues.put(COL_2, newTeacher.getFirstName());
            contentValues.put(COL_3, newTeacher.getLastName());
            contentValues.put(COL_4, newTeacher.getEmail());
            contentValues.put(COL_5, newTeacher.getPassword());
            try{
                if(db.update(TABLE_NAME, contentValues, whereClause, null) > 0)
                    return true;
                else
                    throw new TeacherDatabaseHelperException("Failed to update Teacher");
            }catch (RuntimeException e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch(SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (TeacherDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTeacherById(int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (db.delete(TABLE_NAME, COL_1 + " = ?", new String[]{String.valueOf(id)}) > 0)
                    return true;
                else
                    throw new TeacherDatabaseHelperException("Failed to delete Teacher");
            }catch (RuntimeException e){
                throw new SQLiteException("Teacher table encountered unknown error");
            }
        }catch(SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (TeacherDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }
}