package com.remswork.classmanager.helper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rafael on 7/20/2017.
 */

public class ScheduleDatabaseHelper extends DatabaseHelper {

    public ScheduleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
