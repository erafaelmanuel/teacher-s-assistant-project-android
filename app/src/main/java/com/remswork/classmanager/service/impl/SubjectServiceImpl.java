package com.remswork.classmanager.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.SubjectDatabaseHelper;
import com.remswork.classmanager.model.Subject;
import com.remswork.classmanager.service.SubjectService;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class SubjectServiceImpl implements SubjectService {

    private SubjectDatabaseHelper subjectDatabaseHelper;

    public SubjectServiceImpl(Context context){
        subjectDatabaseHelper = new SubjectDatabaseHelper(context);
    }

    @Override
    public boolean addSubject(Subject subject) {
        return subjectDatabaseHelper.addSubject(subject);
    }

    @Override
    public Subject getSubjectById(int id) {
        return subjectDatabaseHelper.getSubjectById(id);
    }

    @Override
    public Subject getSubjectByName(String name) {
        return subjectDatabaseHelper.getSubjectByName(name);
    }

    @Override
    public List<Subject> getAllSubject() {
        return subjectDatabaseHelper.getAllSubject();
    }

    @Override
    public boolean updateSubjectById(int id, Subject newSubject) {
        return subjectDatabaseHelper.updateSubjectById(id, newSubject);
    }

    @Override
    public boolean deleteSubjectById(int id) {
        return subjectDatabaseHelper.deleteSubjectById(id);
    }
}
