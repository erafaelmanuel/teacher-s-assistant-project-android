package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.SyllabusDatabaseHelper;
import com.remswork.classmanager.helper.service.SyllabusService;
import com.remswork.classmanager.model.grade.Syllabus;

import java.util.List;

/**
 * Created by Rafael on 7/23/2017.
 */

public class SyllabusServiceImpl implements SyllabusService {

    private SyllabusDatabaseHelper syllabusDatabaseHelper;

    public SyllabusServiceImpl(Context context){
        syllabusDatabaseHelper = new SyllabusDatabaseHelper(context);
    }

    @Override
    public boolean addSyllabus(Syllabus syllabus) {
        return syllabusDatabaseHelper.addSyllabus(syllabus);
    }

    @Override
    public Syllabus getSyllabusById(int id) {
        return syllabusDatabaseHelper.getSyllabusById(id);
    }

    @Override
    public List<Syllabus> getAllSyllabus() {
        return syllabusDatabaseHelper.getAllSyllabus();
    }

    @Override
    public boolean updateSyllabusById(int id, Syllabus newSyllabus) {
        return syllabusDatabaseHelper.updateSyllabusById(id, newSyllabus);
    }

    @Override
    public boolean deleteSyllabusById(int id) {
        return syllabusDatabaseHelper.deleteSyllabusById(id);
    }

    @Override
    public int deleteAllSyllabus() {
        return syllabusDatabaseHelper.deleteAllSyllabus();
    }
}
