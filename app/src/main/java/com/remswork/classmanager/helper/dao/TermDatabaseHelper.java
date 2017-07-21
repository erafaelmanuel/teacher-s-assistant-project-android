package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.TermDatabaseHelperException;
import com.remswork.classmanager.model.grade.Term;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Rafael on 7/21/2017.
 */

public class TermDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_term";
    private static final String COL_1 = "id";
    private static final String COL_2 = "code";
    private static final String COL_3 = "name";
    private static final String COL_4 = "class_id";

    public TermDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT , %s TEXT , %s INTEGER);", TABLE_NAME, COL_1, COL_2,
                COL_3, COL_4);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addTerm(final Term term) {
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, term.getId());
            contentValues.put(COL_2, term.getCode());
            contentValues.put(COL_3, term.getName());
            contentValues.put(COL_4, term.getClazzId());
            if(getTermById(term.getId()) == null) {
                db.insertOrThrow(TABLE_NAME, null, contentValues);
                return true;
            }else
                throw new TermDatabaseHelperException(
                        "Term already exist with ID : " + term.getId());
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-2, VERSION);
            e.printStackTrace();
            return false;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int addTerm(List<Term> terms) {
        int counter = 0;
        for (Term term : terms){
            if(addTerm(term))
                counter ++;
        }
        return counter;
    }

    public int addTerm(Term... terms) {
        int counter = 0;
        for (Term term : terms){
            if(addTerm(term))
                counter ++;
        }
        return counter;
    }

    public Term getTermById(final int id) {
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try{
                if(cursor.moveToNext()){
                    Term term = new Term();
                    term.setId(cursor.getInt(0));
                    term.setCode(cursor.getString(1));
                    term.setName(cursor.getString(2));
                    term.setClazzId(cursor.getInt(3));
                    cursor.close();
                    return term;
                }else
                    throw new TermDatabaseHelperException("Term not exist with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public Term getTermByCodeAndClazzId(final String code, final int clazzId) {
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? LIMIT 1;",
                    TABLE_NAME, COL_2, COL_4);
            String args[] = new String[]{code, String.valueOf(clazzId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try{
                if(cursor.moveToNext()){
                    Term term = new Term();
                    term.setId(cursor.getInt(0));
                    term.setCode(cursor.getString(1));
                    term.setName(cursor.getString(2));
                    term.setClazzId(cursor.getInt(3));
                    cursor.close();
                    return term;
                }else
                    throw new TermDatabaseHelperException("Term not exist");
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Term> getTermByClassId(int clazzId) {
        List<Term> listOfTerm = new ArrayList<Term>();
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? ;",
                    TABLE_NAME, COL_4);
            String args[] = new String[]{String.valueOf(clazzId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try{
                if(cursor.getCount() > 0){
                    while(cursor.moveToNext()) {
                        Term term = new Term();
                        term.setId(cursor.getInt(0));
                        term.setCode(cursor.getString(1));
                        term.setName(cursor.getString(2));
                        term.setClazzId(cursor.getInt(3));
                        listOfTerm.add(term);
                    }
                    cursor.close();
                    return listOfTerm;
                }else
                    throw new TermDatabaseHelperException("No Term found");
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateTermById(int id, Term term) {
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, term.getId());
            contentValues.put(COL_2, term.getCode());
            contentValues.put(COL_3, term.getName());
            contentValues.put(COL_4, term.getClazzId());
            try {
                if (db.update(TABLE_NAME, contentValues, whereClause, null) > 0)
                    return true;
                else
                    throw new TermDatabaseHelperException(
                            "Failed to update term with ID : " + term.getId());
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-2, VERSION);
            e.printStackTrace();
            return false;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTermById(int id) {
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (db.delete(TABLE_NAME, whereClause, null) > 0)
                    return true;
                else
                    throw new TermDatabaseHelperException(
                            "Failed to delete term with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-2, VERSION);
            e.printStackTrace();
            return false;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int deleteTermByClassId(int clazzId) {
        int counter = 0;
        try{
            String whereClause = COL_4 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            try {
                if ((counter = db.delete(TABLE_NAME, whereClause, null)) > 0)
                    return counter;
                else
                    throw new TermDatabaseHelperException(
                            "Failed to delete term with Class ID : " + clazzId);
            }catch (RuntimeException e){
                throw new SQLiteException("Term table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-2, VERSION);
            e.printStackTrace();
            return 0;
        }catch (TermDatabaseHelperException e){
            e.printStackTrace();
            return 0;
        }
    }
}
