package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.TermDatabaseHelper;
import com.remswork.classmanager.helper.service.TermService;
import com.remswork.classmanager.model.grade.Term;

import java.util.List;

/**
 * Created by Rafael on 7/21/2017.
 */

public class TermServiceImpl implements TermService {

    private TermDatabaseHelper teacherDatabaseHelper;

    public TermServiceImpl(Context context) {
        teacherDatabaseHelper = new TermDatabaseHelper(context);
    }

    @Override
    public boolean addTerm(final Term term) {
        return teacherDatabaseHelper.addTerm(term);
    }

    @Override
    public int addTerm(final List<Term> terms) {
        return teacherDatabaseHelper.addTerm(terms);
    }

    @Override
    public int addTerm(final Term... terms) {
        return teacherDatabaseHelper.addTerm(terms);
    }

    @Override
    public Term getTermById(final int id) {
        return teacherDatabaseHelper.getTermById(id);
    }

    @Override
    public Term getTermByCodeAndClazzId(final String code, final int clazzId) {
        return teacherDatabaseHelper.getTermByCodeAndClazzId(code, clazzId);
    }

    @Override
    public List<Term> getTermByClassId(int clazzId) {
        return teacherDatabaseHelper.getTermByClassId(clazzId);
    }

    @Override
    public boolean updateTermById(int id, Term term) {
        return teacherDatabaseHelper.updateTermById(id, term);
    }

    @Override
    public boolean deleteTermById(int id) {
        return teacherDatabaseHelper.deleteTermById(id);
    }

    @Override
    public int deleteTermByClassId(int clazzId) {
        return teacherDatabaseHelper.deleteTermByClassId(clazzId);
    }
}
