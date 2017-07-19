package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.exception.StudentDatabaseHelperException;
import com.remswork.classmanager.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class StudentDatabaseHelper extends DatabaseHelper {

    public static final String TABLE_NAME = "tbl_student";
    public static final String COL_1 = "id";
    public static final String COL_2 = "first_name";
    public static final String COL_3 = "last_name";
    public static final String COL_4 = "middle_name";
    public static final String COL_5 = "age";
    public static final String COL_6 = "gender";
    public static final String COL_7 = "year";
    public static final String COL_8 = "image";

    public StudentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT, %s TEXT , %s TEXT, %s INTEGER, %s TEXT, %s INTEGER, " +
                "%s INTEGER);", TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7,
                COL_8);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addStudent(Student student) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, student.getId());
            contentValues.put(COL_2, student.getFirstName());
            contentValues.put(COL_3, student.getLastName());
            contentValues.put(COL_4, student.getMiddleName());
            contentValues.put(COL_5, student.getAge());
            contentValues.put(COL_6, student.getGender());
            contentValues.put(COL_7, student.getYear());
            contentValues.put(COL_8, student.getImage());

            if (db.insert(TABLE_NAME, null, contentValues) != -1)
                return true;
            else
                throw new StudentDatabaseHelperException("Student can't be added");
        } catch (StudentDatabaseHelperException e) {
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public Student getStudentById(final int id) {
        try {
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            if (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setMiddleName(cursor.getString(3));
                student.setAge(cursor.getInt(4));
                student.setGender(cursor.getString(5));
                student.setYear(cursor.getInt(6));
                student.setImage(cursor.getInt(7));
                cursor.close();
                return student;
            } else
                throw new StudentDatabaseHelperException("No Student found with ID :" + id);
        } catch (StudentDatabaseHelperException e) {
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return null;
        }
    }

    public List<Student> getStudentsByLastName(final String lastName) {
        List<Student> listOfStudent = new ArrayList<Student>();
        try {
            String query = String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, COL_3, lastName);
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Student student = new Student();
                    student.setId(cursor.getInt(0));
                    student.setFirstName(cursor.getString(1));
                    student.setLastName(cursor.getString(2));
                    student.setMiddleName(cursor.getString(3));
                    student.setAge(cursor.getInt(4));
                    student.setGender(cursor.getString(5));
                    student.setYear(cursor.getInt(6));
                    student.setImage(cursor.getInt(7));
                    listOfStudent.add(student);
                }
                cursor.close();
                return listOfStudent;
            } else
                throw new StudentDatabaseHelperException("No Student found with Last Name : " +
                        lastName);
        } catch (StudentDatabaseHelperException e) {
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return listOfStudent;
        }
    }

    public List<Student> getAllStudent() {
        List<Student> listOfStudent = new ArrayList<Student>();
        try {
            String query = String.format("SELECT * FROM %s WHERE 1;", TABLE_NAME);
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Student student = new Student();
                    student.setId(cursor.getInt(0));
                    student.setFirstName(cursor.getString(1));
                    student.setLastName(cursor.getString(2));
                    student.setMiddleName(cursor.getString(3));
                    student.setAge(cursor.getInt(4));
                    student.setGender(cursor.getString(5));
                    student.setYear(cursor.getInt(6));
                    student.setImage(cursor.getInt(7));
                    listOfStudent.add(student);
                }
                cursor.close();
                return listOfStudent;
            } else
                throw new StudentDatabaseHelperException("No Student");
        } catch (StudentDatabaseHelperException e) {
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return listOfStudent;
        }
    }

    public boolean updateStudentById(final int id, final Student student) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = COL_1 + " = " + id;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, student.getId());
            contentValues.put(COL_2, student.getFirstName());
            contentValues.put(COL_3, student.getLastName());
            contentValues.put(COL_4, student.getMiddleName());
            contentValues.put(COL_5, student.getAge());
            contentValues.put(COL_6, student.getGender());
            contentValues.put(COL_7, student.getYear());
            contentValues.put(COL_8, student.getImage());

            if (db.update(TABLE_NAME, contentValues, whereClause, null) > 0)
                return true;
            else
                throw new StudentDatabaseHelperException("Failed to update Student with ID : "
                        + id);
        } catch (StudentDatabaseHelperException e) {
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public boolean deleteStudentById(final int id){
        try{
            String whereClause = COL_1 + " = " + id;
            SQLiteDatabase db = getWritableDatabase();
            if(db.delete(TABLE_NAME,  whereClause, null) > 0)
                return true;
            else
                throw new StudentDatabaseHelperException("Failed to delete Student with ID : "
                        + id);
        }catch (StudentDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

}