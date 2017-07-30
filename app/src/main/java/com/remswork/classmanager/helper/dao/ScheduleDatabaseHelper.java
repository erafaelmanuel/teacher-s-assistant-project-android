package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.ScheduleDatabaseHelperException;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/20/2017.
 */

public class ScheduleDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_NAME = "tbl_schedule";
    private static final String COL_1 = "id";
    private static final String COL_2 = "day";
    private static final String COL_3 = "time";
    private static final String COL_4 = "hour";
    private static final String COL_5 = "room";
    private static final String COL_6 = "class_id";
    private static final String COL_7 = "subject_name";

    public ScheduleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, VERSION);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s INTEGER, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME +"'");
        onCreate(db);
    }

    public boolean addSchedule(final Schedule schedule) {
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, schedule.getId());
            contentValues.put(COL_2, schedule.getDay());
            contentValues.put(COL_3, schedule.getTime());
            contentValues.put(COL_4, schedule.getHour());
            contentValues.put(COL_5, schedule.getRoom());
            contentValues.put(COL_6, schedule.getClazzId());
            if(getScheduleById(schedule.getId()) == null) {
                if (db.insertOrThrow(TABLE_NAME, null, contentValues) != -1)
                    return true;
                else
                    throw new SQLiteException("Schedule table encountered an unknown error");
            }else
                throw new ScheduleDatabaseHelperException(
                        "Schedule already exists with ID : " + schedule.getId());
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int addSchedule(final List<Schedule> listOfSchedule){
        int counter = 0;
        for(Schedule schedule : listOfSchedule){
            if(addSchedule(schedule))
                counter ++;
        }
        return counter;
    }
    public int addSchedule(final Schedule... schedules){
        int counter = 0;
        for(Schedule schedule : schedules){
            if(addSchedule(schedule))
                counter ++;
        }
        return counter;
    }

    public Schedule getScheduleById(final int id){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.moveToNext()) {
                    Schedule schedule = new Schedule();
                    schedule.setId(cursor.getInt(0));
                    schedule.setDay(cursor.getString(1));
                    schedule.setTime(cursor.getString(2));
                    schedule.setHour(cursor.getInt(3));
                    schedule.setRoom(cursor.getString(4));
                    schedule.setClazzId(cursor.getInt(5));
                    cursor.close();
                    return schedule;
                } else
                    throw new ScheduleDatabaseHelperException(
                            "No schedule found with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (ScheduleDatabaseHelperException e){
            //e.printStackTrace();
            return null;
        }
    }

    public List<Schedule> getScheduleByClazzId(final int classId){
        List<Schedule> listOfSchedule = new ArrayList<Schedule>();
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL_6);
            String args[] = new String[]{String.valueOf(classId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Schedule schedule = new Schedule();
                        schedule.setId(cursor.getInt(0));
                        schedule.setDay(cursor.getString(1));
                        schedule.setTime(cursor.getString(2));
                        schedule.setHour(cursor.getInt(3));
                        schedule.setRoom(cursor.getString(4));
                        schedule.setClazzId(cursor.getInt(5));
                        listOfSchedule.add(schedule);
                    }
                    cursor.close();
                    return listOfSchedule;
                } else
                    throw new ScheduleDatabaseHelperException(
                            "No schedule found with CLass ID : " + classId);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return listOfSchedule;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return listOfSchedule;
        }
    }

    public List<Schedule> getScheduleByTeacherId(final int teacherId){
        List<Schedule> listOfSchedule = new ArrayList<Schedule>();
        try{
            String query = String.format("SELECT SE.%s, SE.%s, SE.%s, SE.%s, SE.%s, SE.%s, SU.%s " +
                    "FROM %s as T JOIN %s as C ON T.%s = C.%s " +
                    "JOIN %s as SU ON C.%s = SU.%s JOIN %s as SE ON C.%s = SE.%s WHERE T.%s = ?;",
                    COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, SubjectDatabaseHelper.COL_2,
                    TeacherDatabaseHelper.TABLE_NAME, ClazzDatabaseHelper.TABLE_NAME,
                    TeacherDatabaseHelper.COL_1, ClazzDatabaseHelper.COL_2, SubjectDatabaseHelper
                            .TABLE_NAME, ClazzDatabaseHelper.COL_3, SubjectDatabaseHelper.COL_1,
                    TABLE_NAME, ClazzDatabaseHelper.COL_1, COL_6, TeacherDatabaseHelper.COL_1);

            String args[] = new String[]{String.valueOf(teacherId)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Schedule schedule = new Schedule();
                        schedule.setId(cursor.getInt(0));
                        schedule.setDay(cursor.getString(1));
                        schedule.setTime(cursor.getString(2));
                        schedule.setHour(cursor.getInt(3));
                        schedule.setRoom(cursor.getString(4));
                        schedule.setClazzId(cursor.getInt(5));
                        schedule.setSubjectName(cursor.getString(6));
                        listOfSchedule.add(schedule);
                    }
                    cursor.close();
                    return listOfSchedule;
                } else
                    throw new ScheduleDatabaseHelperException(
                            "No schedule found with Teacher ID : " + teacherId);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return listOfSchedule;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return listOfSchedule;
        }
    }

    public List<Schedule> getAllSchedule(){
        List<Schedule> listOfSchedule = new ArrayList<Schedule>();
        try{
            String query = String.format("SELECT * FROM %s WHERE 1;", TABLE_NAME);
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Schedule schedule = new Schedule();
                        schedule.setId(cursor.getInt(0));
                        schedule.setDay(cursor.getString(1));
                        schedule.setTime(cursor.getString(2));
                        schedule.setHour(cursor.getInt(3));
                        schedule.setRoom(cursor.getString(4));
                        schedule.setClazzId(cursor.getInt(5));
                        listOfSchedule.add(schedule);
                    }
                    cursor.close();
                    return listOfSchedule;
                } else
                    throw new ScheduleDatabaseHelperException("No schedule found");
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return listOfSchedule;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return listOfSchedule;
        }
    }

    public boolean updateScheduleById(final int id, final Schedule newSchedule){
        try{
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = COL_1 + " = " + id;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newSchedule.getId());
            contentValues.put(COL_2, newSchedule.getDay());
            contentValues.put(COL_3, newSchedule.getTime());
            contentValues.put(COL_4, newSchedule.getHour());
            contentValues.put(COL_5, newSchedule.getRoom());
            contentValues.put(COL_6, newSchedule.getClazzId());
            try {
                if (db.update(TABLE_NAME, contentValues, whereClause, null) > 0)
                    return true;
                else
                    throw new ScheduleDatabaseHelperException(
                            "Failed to update Schedule with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int updateScheduleByClassId(final int classId, final Schedule newSchedule){
        int counter = 0;
        try{
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = COL_1 + " = " + classId;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newSchedule.getId());
            contentValues.put(COL_2, newSchedule.getDay());
            contentValues.put(COL_3, newSchedule.getTime());
            contentValues.put(COL_4, newSchedule.getHour());
            contentValues.put(COL_5, newSchedule.getRoom());
            contentValues.put(COL_6, newSchedule.getClazzId());
            try {
                if ((counter = db.update(TABLE_NAME, contentValues, whereClause, null)) > 0)
                    return counter;
                else
                    throw new ScheduleDatabaseHelperException(
                            "Failed to update Schedule with Class ID : " + classId);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return counter;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return counter;
        }
    }

    public boolean deleteScheduleById(final int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = COL_1 + " = " + id;

            try {
                if (db.delete(TABLE_NAME, whereClause, null) > 0)
                    return true;
                else
                    throw new ScheduleDatabaseHelperException(
                            "Failed to delete Schedule with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public int deleteScheduleByClassId(final int classId){
        int counter = 0;
        try{
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = COL_1 + " = " + classId;
            try {
                if ((counter = db.delete(TABLE_NAME, whereClause, null)) > 0)
                    return counter;
                else
                    throw new ScheduleDatabaseHelperException(
                            "Failed to delete Schedule with Class ID : " + classId);
            }catch (RuntimeException e){
                throw new SQLiteException("Schedule table encountered an unknown error");
            }
        }catch (SQLiteException e) {
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return counter;
        }catch (ScheduleDatabaseHelperException e){
            e.printStackTrace();
            return counter;
        }
    }

}
