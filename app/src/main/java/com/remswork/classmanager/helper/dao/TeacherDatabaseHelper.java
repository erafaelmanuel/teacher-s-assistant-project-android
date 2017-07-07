package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.remswork.classmanager.model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafael on 7/4/2017.
 */

public class TeacherDatabaseHelper extends DatabaseHelper {

    public static final String TABLE_NAME = "tbl_teacher";
    public static final String COL_1 = "id";
    public static final String COL_2 = "first_name";
    public static final String COL_3 = "last_name";
    public static final String COL_4 = "email";
    public static final String COL_5 = "password";

    public TeacherDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public void upgradeTable(boolean isYes){
        if(isYes)
            onUpgrade(getWritableDatabase(), VERSION - 1, VERSION);
    }

    public boolean addTeacher(Teacher teacher){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, teacher.getFirstName());
            contentValues.put(COL_3, teacher.getLastName());
            contentValues.put(COL_4, teacher.getEmail());
            contentValues.put(COL_5, teacher.getPassword());

            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Teacher> getListOfTeacher(){
        List<Teacher> listOfTeacher = new ArrayList<Teacher>();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE 1", TABLE_NAME);
            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setEmail(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));
                listOfTeacher.add(teacher);
            }

            return listOfTeacher;
        }catch (Exception e){
            e.printStackTrace();
            return listOfTeacher;
        }
    }

    public Teacher getTeacherById(final int id){
        Teacher teacher = new Teacher();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL_1);
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            while (cursor.moveToNext()){
                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setEmail(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));
            }
            return teacher;
        }catch(Exception e){
            e.printStackTrace();
            return teacher;
        }
    }

    public Teacher getTeacherByEmail(final String email){
        Teacher teacher = new Teacher();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL_4);
            Cursor cursor = db.rawQuery(query, new String[]{email});

            while (cursor.moveToNext()){
                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setEmail(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));
            }
            return teacher;
        }catch(Exception e){
            e.printStackTrace();
            return teacher;
        }
    }

    public HashMap getTeacherAuthenticate(String email, String password){
        HashMap map = new HashMap();

        try{
            Teacher teacher = new Teacher();
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? LIMIT 1", TABLE_NAME, COL_4, COL_5);
            Cursor cursor = db.rawQuery(query, new String[]{email, password});

            if(cursor.moveToNext()){

                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setEmail(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));

                map.put("isSuccess", true);
                map.put("getTeacher", teacher);
                return map;
            }else {
                map.put("isSuccess",false);
                return map;
            }

        }catch(Exception e){
            e.printStackTrace();
            map.put("isSuccess",false);
            return  map ;
        }

    }

    public boolean deleteTeacherById(int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, COL_1 +" = ?",new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
