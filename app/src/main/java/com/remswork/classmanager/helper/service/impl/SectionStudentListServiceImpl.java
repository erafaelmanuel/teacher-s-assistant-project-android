package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.SectionStudentListDatabaseHelper;
import com.remswork.classmanager.helper.service.SectionStudentListService;
import com.remswork.classmanager.model.clazz.Student;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class SectionStudentListServiceImpl implements SectionStudentListService {

    private SectionStudentListDatabaseHelper sectionStudentListDatabaseHelper;

    public SectionStudentListServiceImpl(Context context){
        sectionStudentListDatabaseHelper = new SectionStudentListDatabaseHelper(context);
    }

    @Override
    public boolean addStudentId(int sectionId, int studentId) {
        return sectionStudentListDatabaseHelper.addStudentId(sectionId, studentId);
    }

    @Override
    public int[] getSectionIdByStudentId(int studentId) {
        return sectionStudentListDatabaseHelper.getSectionIdByStudentId(studentId);
    }

    @Override
    public Student getStudentBySectionAndStudentId(int sectionId, int studentId) {
        return sectionStudentListDatabaseHelper.getStudentBySectionAndStudentId(
                sectionId, studentId);
    }

    @Override
    public List<Student> getStudentBySectionId(int sectionId) {
        return sectionStudentListDatabaseHelper.getStudentBySectionId(sectionId);
    }

    @Override
    public boolean updateStudentFromListById(int sectionId, int studentId) {
        return sectionStudentListDatabaseHelper.updateStudentFromListById(sectionId, studentId);
    }

    @Override
    public boolean deleteStudentFromListByStudentAndSectionId(int sectionId, int studentId) {
        return sectionStudentListDatabaseHelper.deleteStudentFromListByStudentAndSectionId(
                sectionId, studentId);
    }

    @Override
    public boolean deleteAllStudentFromListBySectionId(int sectionId) {
        return sectionStudentListDatabaseHelper.deleteAllStudentFromListBySectionId(sectionId);
    }

    @Override
    public boolean deleteStudentFromListByStudentId(int studentId) {
        return sectionStudentListDatabaseHelper.deleteStudentFromListByStudentId(studentId);
    }
}
