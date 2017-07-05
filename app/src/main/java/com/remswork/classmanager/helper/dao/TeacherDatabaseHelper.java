package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafael on 7/4/2017.
 */

public class TeacherDatabaseHelper extends DatabaseHelper {
    /**
     *
     */
    public static final String TABLE_NAME = "tbl_teacher";

    /**
     *
     */
    public static final String COL1 = "id";

    /**
     *
     */
    public static final String COL2 = "first_name";

    /**
     *
     */
    public static final String COL3 = "last_name";

    /**
     *
     */
    public static final String COL4 = "username";

    /**
     *
     */
    public static final String COL5 = "password";

    /**
     *
     * @param context
     */
    public TeacherDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, %s TEXT, %s TEXT, %s TEXT);", TABLE_NAME, COL1, COL2, COL3, COL4, COL5);
        db.execSQL(query);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Save the Teacher object into database
     *
     * @param teacher
     * @return true if the data insertion was successful without any error
     */
    public boolean addTeacher(Teacher teacher){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, teacher.getFirstName());
            contentValues.put(COL3, teacher.getLastName());
            contentValues.put(COL4, teacher.getUsername());
            contentValues.put(COL5, teacher.getPassword());

            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */

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
                teacher.setUsername(cursor.getString(3));
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
            String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL1);
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            while (cursor.moveToNext()){
                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setUsername(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));
            }
            return teacher;
        }catch(Exception e){
            e.printStackTrace();
            return teacher;
        }
    }

    public Teacher getTeacherByUsername(final String username){
        Teacher teacher = new Teacher();
        try{
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL4);
            Cursor cursor = db.rawQuery(query, new String[]{username});

            while (cursor.moveToNext()){
                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setUsername(cursor.getString(3));
                teacher.setPassword(cursor.getString(4));
            }
            return teacher;
        }catch(Exception e){
            e.printStackTrace();
            return teacher;
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public HashMap getTeacherAuthenticate(String username, String password){
        HashMap map = new HashMap();

        try{
            Teacher teacher = new Teacher();
            SQLiteDatabase db = getWritableDatabase();
            String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? LIMIT 1", TABLE_NAME, COL4, COL5);
            Cursor cursor = db.rawQuery(query, new String[]{username, password});

            if(cursor.moveToNext()){

                teacher.setId(cursor.getInt(0));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setUsername(cursor.getString(3));
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
}
