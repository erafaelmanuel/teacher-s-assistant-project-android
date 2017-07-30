package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.SyllabusDatabaseHelperException;
import com.remswork.classmanager.model.grade.Syllabus;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Rafael on 7/23/2017.
 */

public class SyllabusDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_syllabus";
    private static final String COL_1 = "id";
    private static final String COL_2 = "activity";
    private static final String COL_3 = "assignment";
    private static final String COL_4 = "attendance";
    private static final String COL_5 = "behavior";
    private static final String COL_6 = "exam";
    private static final String COL_7 = "finalExam";
    private static final String COL_8 = "performance";
    private static final String COL_9 = "quiz";
    private static final String COL_10 = "recitation";

    public SyllabusDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
               "AUTOINCREMENT, %s REAL, %s REAL, %s REAL, %s REAL, %s REAL, %s REAL, %s REAL, " +
               "%s REAL, %s REAL);", TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7,
               COL_8, COL_9, COL_10);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addSyllabus(final Syllabus syllabus){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
//            contentValues.put(COL_1, syllabus.getId());
            contentValues.put(COL_2, syllabus.getActivity());
            contentValues.put(COL_3, syllabus.getAssignment());
            contentValues.put(COL_4, syllabus.getAttendance());
            contentValues.put(COL_5, syllabus.getBehavior());
            contentValues.put(COL_6, syllabus.getExam());
            contentValues.put(COL_7, syllabus.getFinalExam());
            contentValues.put(COL_8, syllabus.getPerformance());
            contentValues.put(COL_9, syllabus.getQuiz());
            contentValues.put(COL_10, syllabus.getRecitation());

            if(getSyllabusById(syllabus.getId()) == null) {
                db.insertOrThrow(TABLE_NAME, null, contentValues);
                return true;
            }else
                throw new SyllabusDatabaseHelperException(
                        "Syllabus is Already exists with ID : " + syllabus.getId());

        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public Syllabus getSyllabusById(final int id){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.moveToNext()) {
                    Syllabus syllabus = new Syllabus();
                    syllabus.setId(cursor.getInt(0));
                    syllabus.setActivity(cursor.getDouble(1));
                    syllabus.setAssignment(cursor.getDouble(2));
                    syllabus.setAttendance(cursor.getDouble(3));
                    syllabus.setBehavior(cursor.getDouble(4));
                    syllabus.setExam(cursor.getDouble(5));
                    syllabus.setFinalExam(cursor.getDouble(6));
                    syllabus.setPerformance(cursor.getDouble(7));
                    syllabus.setQuiz(cursor.getDouble(8));
                    syllabus.setRecitation(cursor.getDouble(9));
                    cursor.close();
                    return syllabus;
                } else
                    throw new SyllabusDatabaseHelperException("No syllabus found");
            }catch (RuntimeException e){
                throw new SQLiteException("Syllabus table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Syllabus> getAllSyllabus(){
        List<Syllabus> syllabuses = new ArrayList<Syllabus>();
        try{
            String query = String.format("SELECT * FROM %s WHERE 1;",
                    TABLE_NAME, COL_1);
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.getCount() > 0) {
                    while(cursor.moveToNext()) {
                        Syllabus syllabus = new Syllabus();
                        syllabus.setId(cursor.getInt(0));
                        syllabus.setActivity(cursor.getDouble(1));
                        syllabus.setAssignment(cursor.getDouble(2));
                        syllabus.setAttendance(cursor.getDouble(3));
                        syllabus.setBehavior(cursor.getDouble(4));
                        syllabus.setExam(cursor.getDouble(5));
                        syllabus.setFinalExam(cursor.getDouble(6));
                        syllabus.setPerformance(cursor.getDouble(7));
                        syllabus.setQuiz(cursor.getDouble(8));
                        syllabus.setRecitation(cursor.getDouble(9));
                        syllabuses.add(syllabus);
                    }
                    cursor.close();
                    return syllabuses;
                } else
                    throw new SyllabusDatabaseHelperException("No syllabus found");
            }catch (RuntimeException e){
                throw new SQLiteException("Syllabus table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return syllabuses;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return syllabuses;
        }
    }

    public boolean updateSyllabusById(final int id, final Syllabus newSyllabus){
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newSyllabus.getId());
            contentValues.put(COL_2, newSyllabus.getActivity());
            contentValues.put(COL_3, newSyllabus.getAssignment());
            contentValues.put(COL_4, newSyllabus.getAttendance());
            contentValues.put(COL_5, newSyllabus.getBehavior());
            contentValues.put(COL_6, newSyllabus.getExam());
            contentValues.put(COL_7, newSyllabus.getFinalExam());
            contentValues.put(COL_8, newSyllabus.getPerformance());
            contentValues.put(COL_9, newSyllabus.getQuiz());
            contentValues.put(COL_10, newSyllabus.getRecitation());
            try {
                if (db.update(TABLE_NAME, null, whereClause, null) > 0)
                    return true;
                else
                    throw new SyllabusDatabaseHelperException(
                            "Failed to update syllabus with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Syllabus table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSyllabusById(final int id){
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (db.delete(TABLE_NAME, whereClause, null) > 0)
                    return true;
                else
                    throw new SyllabusDatabaseHelperException(
                            "Failed to delete syllabus with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Syllabus table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int deleteAllSyllabus(){
        int counter = 0;
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            try {
                if ((counter = db.delete(TABLE_NAME, whereClause, null)) > 0)
                    return counter;
                else
                    throw new SyllabusDatabaseHelperException(
                            "No syllabus to delete");
            }catch (RuntimeException e){
                throw new SQLiteException("Syllabus table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return counter;
        }catch (SyllabusDatabaseHelperException e){
            e.printStackTrace();
            return counter;
        }
    }
}
