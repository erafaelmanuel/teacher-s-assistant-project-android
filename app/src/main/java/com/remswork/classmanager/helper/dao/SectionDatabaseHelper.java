package com.remswork.classmanager.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.remswork.classmanager.exception.SectionDatabaseHelperException;
import com.remswork.classmanager.helper.service.SectionStudentListService;
import com.remswork.classmanager.helper.service.impl.SectionStudentListServiceImpl;
import com.remswork.classmanager.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class SectionDatabaseHelper extends DatabaseHelper {

    public static final String TABLE_NAME = "tbl_section";
    public static final String COL_1 = "id";
    public static final String COL_2 = "section_name";
    public static final String COL_3 = "year";
    public static final String COL_4 = "department";
    private SectionStudentListService sectionStudentListService;

    public SectionDatabaseHelper(Context context){
        super(context , DATABASE_NAME, VERSION);
        sectionStudentListService = new SectionStudentListServiceImpl(context);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT, %s INTEGER, %s TEXT);",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME +"'");
        onCreate(db);
    }

    public boolean addSection(final Section section){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, section.getId());
            contentValues.put(COL_2, section.getSectionName());
            contentValues.put(COL_3, section.getYear());
            contentValues.put(COL_4, section.getDepartment());

            if(db.insert(TABLE_NAME, null, contentValues) != -1)
                return true;
            else
                throw new SectionDatabaseHelperException("Section can't be added");
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            return false;
        }
    }

    public Section getSectionById(final int id){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.moveToNext()) {
                    Section section = new Section();
                    section.setId(cursor.getInt(0));
                    section.setSectionName(cursor.getString(1));
                    section.setYear(cursor.getInt(2));
                    section.setDepartment(cursor.getString(3));
                    section.setStudents(sectionStudentListService.getStudentBySectionId(id));
                    cursor.close();
                    return section;
                } else
                    throw new SectionDatabaseHelperException("No Section found with ID :" + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Section table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public Section getSectionByIdWithoutStudentList(final int id){
        try{
            String query = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;",
                    TABLE_NAME, COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, args);
            try {
                if (cursor.moveToNext()) {
                    Section section = new Section();
                    section.setId(cursor.getInt(0));
                    section.setSectionName(cursor.getString(1));
                    section.setYear(cursor.getInt(2));
                    section.setDepartment(cursor.getString(3));
                    cursor.close();
                    return section;
                } else
                    throw new SectionDatabaseHelperException("No Section found with ID :" + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Section table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return null;
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Section> getAllSection(){
        List<Section> listOfSection = new ArrayList<Section>();
        try{
            String query = String.format("SELECT * FROM %s ;", TABLE_NAME);
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Section section = new Section();
                        section.setId(cursor.getInt(0));
                        section.setSectionName(cursor.getString(1));
                        section.setYear(cursor.getInt(2));
                        section.setDepartment(cursor.getString(3));
                        listOfSection.add(section);
                    }
                    cursor.close();
                    return listOfSection;
                } else
                    throw new SectionDatabaseHelperException("No section found");
            }catch (RuntimeException e){
                throw new SQLiteException("Section table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return listOfSection;
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            return listOfSection;
        }
    }

    public boolean updateSectionById(final int id, final Section newSection){
        try{
            String whereClause = String.format("%s = ?", COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, newSection.getId());
            contentValues.put(COL_2, newSection.getSectionName());
            contentValues.put(COL_3, newSection.getYear());
            contentValues.put(COL_4, newSection.getDepartment());
            try {
                if (db.update(TABLE_NAME, contentValues, whereClause, args) > 0)
                    return true;
                else
                    throw new SectionDatabaseHelperException(
                            "Failed to update section with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Section table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return false;
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSectionById(final int id){
        try{
            String whereClause = String.format("%s = ?", COL_1);
            String args[] = new String[]{String.valueOf(id)};
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (db.delete(TABLE_NAME, whereClause, args) > 0)
                    return true;
                else
                    throw new SectionDatabaseHelperException(
                            "Failed to delete section with ID : " + id);
            }catch (RuntimeException e){
                throw new SQLiteException("Section table encountered an unknown error");
            }
        }catch (SQLiteException e){
            onUpgrade(getWritableDatabase(), VERSION-1, VERSION);
            e.printStackTrace();
            return true;
        }catch (SectionDatabaseHelperException e){
            e.printStackTrace();
            return true;
        }
    }

}
