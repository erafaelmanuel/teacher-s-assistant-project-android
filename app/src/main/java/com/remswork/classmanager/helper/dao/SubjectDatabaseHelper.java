package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/9/2017.
 */

public class SubjectDatabaseHelper extends DatabaseHelper {


    private static final String TABLE_NAME = "tbl_subject";
    private static final String COL_1 = "id";
    private static final String COL_2 = "name";
    private static final String COL_3 = "code";
    private static final String COL_4 = "desc";
    private static final String COL_5 = "unit";
    private static final String COL_6 = "imageIcon";

    public SubjectDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %S TEXT,%S TEXT, %s TEXT, %s INTEGER, %s INTEGER);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_NAME+"'");
        onCreate(db);
    }

    public boolean addSubject(Subject subject){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, subject.getId());
            contentValues.put(COL_2, subject.getName());
            contentValues.put(COL_3, subject.getCode());
            contentValues.put(COL_4, subject.getDesc());
            contentValues.put(COL_5, subject.getUnit());
            contentValues.put(COL_6, subject.getImageIcon());

            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public Subject getSubjectById(final int id){
        Subject subject = new Subject();
        String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABLE_NAME, COL_1);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if(cursor.moveToNext()){
                subject.setId(cursor.getInt(0));
                subject.setName(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                cursor.close();
                return subject;
            }else
                throw new Exception();
        }catch(Exception e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return null;
        }
    }

    public Subject getSubjectByName(final String name){
        Subject subject = new Subject();
        String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABLE_NAME, COL_2);
        try{
            SQLiteDatabase db = getWritableDatabase();
            String[] args = new String[]{name};
            Cursor cursor = db.rawQuery(query, args);
            if(cursor.moveToNext()){
                subject.setId(cursor.getInt(0));
                subject.setName(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                cursor.close();
                return subject;
            }else
                throw new Exception();
        }catch(Exception e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return null;
        }
    }

    public List<Subject> getAllSubject(){
        List<Subject> listOfSubject = new ArrayList<Subject>();
        String query = String.format("SELECT * FROM %s ;", TABLE_NAME);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                Subject subject = new Subject();
                subject.setId(cursor.getInt(0));
                subject.setName(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                listOfSubject.add(subject);
            }
            cursor.close();
            return listOfSubject;
        }catch(Exception e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return null;
        }
    }

    public boolean updateSubjectById(final int id, final Subject newSubject){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newSubject.getId());
            contentValues.put(COL_2, newSubject.getName());
            contentValues.put(COL_3, newSubject.getCode());
            contentValues.put(COL_4, newSubject.getDesc());
            contentValues.put(COL_5, newSubject.getUnit());
            contentValues.put(COL_6, newSubject.getImageIcon());

            if(db.update(TABLE_NAME, contentValues, COL_1 + " = " + id, null) > 0)
                return true;
            else
                throw new Exception();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubjectById(final int id){
        try{
            String whereClause = String.format("%s = ?", COL_1);
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, whereClause, new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }
}
