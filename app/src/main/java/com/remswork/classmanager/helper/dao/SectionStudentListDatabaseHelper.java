package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remswork.classmanager.exception.SectionStudentListDatabaseHelperException;
import com.remswork.classmanager.helper.service.StudentService;
import com.remswork.classmanager.helper.service.impl.StudentServiceImpl;
import com.remswork.classmanager.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class SectionStudentListDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_student_list";
    private static final String COL_1 = "id";
    private static final String COL_2 = "section_id";
    private static final String COL_3 = "student_id";
    private StudentService studentService;

    public SectionStudentListDatabaseHelper(Context context){
        super(context, DATABASE_NAME, VERSION);
        studentService = new StudentServiceImpl(context);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s INTEGER, %s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s), " +
                "FOREIGN KEY (%s) REFERENCES %s(%s));", TABLE_NAME, COL_1, COL_2, COL_3, COL_2,
                SectionDatabaseHelper.TABLE_NAME, SectionDatabaseHelper.COL_1, COL_3,
                StudentDatabaseHelper.TABLE_NAME, StudentDatabaseHelper.COL_1);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_NAME +"'");
        onCreate(db);
    }

    public boolean addStudentId(final int sectionId, final int studentId){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, sectionId);
            contentValues.put(COL_3, studentId);

            if(db.insert(TABLE_NAME, null, contentValues) != -1)
                return true;
            else
                throw new SectionStudentListDatabaseHelperException("Student can't be register");
        }catch (SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public int[] getSectionIdByStudentId(final int studentId){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ?;", TABLE_NAME, COL_3);
            String args[] = new String[]{String.valueOf(studentId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);

            if(cursor.getCount() > 0){
                int studentIds[] = new int[cursor.getCount()];
                int counter = 0;
                while(cursor.moveToNext()){
                    studentIds[counter] = cursor.getInt(0);
                    counter ++;
                }
                cursor.close();
                return studentIds;
            }else
                throw new SectionStudentListDatabaseHelperException(
                        "No section found from the list");
        }catch(SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return new int[]{};
        }
    }

    public Student getStudentBySectionAndStudentId(final int sectionId, final int studentId){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;",
                    TABLE_NAME, COL_2, COL_3);
            String args[] = new String[]{String.valueOf(sectionId), String.valueOf(studentId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            if(cursor.moveToNext()){
                Student student;
                if((student = studentService.getStudentById(studentId)) != null) {
                    cursor.close();
                    return student;
                }
            }
            throw new SectionStudentListDatabaseHelperException(
                    "No student found from the list");
        }catch(SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return null;
        }
    }

    public List<Student> getStudentBySectionId(final int sectionId){
        List<Student> listOfStudent = new ArrayList<Student>();
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ?;", TABLE_NAME, COL_2);
            String args[] = new String[]{String.valueOf(sectionId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);

            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    Student student;
                    if((student = studentService.getStudentById(cursor.getInt(2))) != null)
                        listOfStudent.add(student);
                }
                cursor.close();
                return listOfStudent;
            }else
                throw new SectionStudentListDatabaseHelperException(
                        "No student found from the list");
        }catch(SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return listOfStudent;
        }
    }


    public boolean updateStudentFromListById(final int sectionId, final int studentId){
        try{
            String whereClause = String.format("%s = ? AND %s = ?", COL_2, COL_3);
            String args[] = new String[]{String.valueOf(sectionId), String.valueOf(studentId)};
            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, sectionId);
            contentValues.put(COL_3, studentId);

            if(db.update(TABLE_NAME, contentValues, whereClause, args) > 0)
                return true;
            else
                throw new SectionStudentListDatabaseHelperException(
                        "Failed to update student from list");
        }catch (SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public boolean deleteStudentFromListByStudentAndSectionId(
            final int sectionId, final int studentId){
        try{
            String whereClause = String.format("%s = ? AND %s = ?", COL_2, COL_3);
            String args[] = new String[]{String.valueOf(sectionId), String.valueOf(studentId)};
            SQLiteDatabase db = getWritableDatabase();

            if(db.delete(TABLE_NAME, whereClause, args) > 0)
                return true;
            else
                throw new SectionStudentListDatabaseHelperException(
                        "Failed to delete student from list");
        }catch (SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public boolean deleteAllStudentFromListBySectionId(final int sectionId){
        try{
            String whereClause = String.format("%s = ?", COL_2);
            String args[] = new String[]{String.valueOf(sectionId)};
            SQLiteDatabase db = getWritableDatabase();

            if(db.delete(TABLE_NAME, whereClause, args) > 0)
                return true;
            else
                throw new SectionStudentListDatabaseHelperException(
                        "Failed to delete student from list");
        }catch (SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public boolean deleteStudentFromListByStudentId(final int studentId){
        try{
            String whereClause = String.format("%s = ?", COL_3);
            String args[] = new String[]{String.valueOf(studentId)};
            SQLiteDatabase db = getWritableDatabase();

            if(db.delete(TABLE_NAME, whereClause, args) > 0)
                return true;
            else
                throw new SectionStudentListDatabaseHelperException(
                        "Failed to delete student from list");
        }catch (SectionStudentListDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }
}