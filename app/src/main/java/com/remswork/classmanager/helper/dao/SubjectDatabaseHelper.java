package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.remswork.classmanager.helper.dao.TeacherDatabaseHelper.TABLE_NAME;

/**
 * Created by Rafael on 7/9/2017.
 */

public class SubjectDatabaseHelper extends DatabaseHelper {


    private static final String TABLE_NAME = "tbl_subject";
    private static final String COL_1 = "id";
    private static final String COL_2 = "title";
    private static final String COL_3 = "code";
    private static final String COL_4 = "desc";
    private static final String COL_5 = "unit";
    private static final String COL_6 = "imageIcon";

    public SubjectDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%S TEXT,%S TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_NAME+"'");
        onCreate(db);
    }

    public Subject getSubjectById(final int id){
        Subject subject = new Subject();
        String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABLE_NAME, COL_1);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if(cursor.moveToNext()){
                subject.setId(cursor.getInt(0));
                subject.setTitle(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                return subject;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Subject getSubjectByTitle(final String title){
        Subject subject = new Subject();
        String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABLE_NAME, COL_2);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{title});
            if(cursor.moveToNext()){
                subject.setId(cursor.getInt(0));
                subject.setTitle(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                return subject;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Subject getSubjectByCode(final String code){
        Subject subject = new Subject();
        String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABLE_NAME, COL_3);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{code});
            if(cursor.moveToNext()){
                subject.setId(cursor.getInt(0));
                subject.setTitle(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
                return subject;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Subject> getAllSubject(){
        List<Subject> listOfSubject = new ArrayList<Subject>();
        String query = String.format("SELECT * FROM %s;", TABLE_NAME);
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
           while(cursor.moveToNext()){
                Subject subject = new Subject();
                subject.setId(cursor.getInt(0));
                subject.setTitle(cursor.getString(1));
                subject.setCode(cursor.getString(2));
                subject.setDesc(cursor.getString(3));
                subject.setUnit(cursor.getInt(4));
                subject.setImageIcon(cursor.getInt(5));
               listOfSubject.add(subject);
            }
            return listOfSubject;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSubject(Subject subject){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_1, subject.getId());
            contentValues.put(COL_2, subject.getTitle());
            contentValues.put(COL_3, subject.getCode());
            contentValues.put(COL_4, subject.getDesc());
            contentValues.put(COL_5, subject.getUnit());
            contentValues.put(COL_6, subject.getImageIcon());

            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
