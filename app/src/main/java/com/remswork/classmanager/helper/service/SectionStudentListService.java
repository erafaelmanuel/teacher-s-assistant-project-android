package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Student;

import java.util.List;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public interface SectionStudentListService {

    boolean addStudentId(int sectionId, int studentId);
    int[] getSectionIdByStudentId(int studentId);
    Student getStudentBySectionAndStudentId(int sectionId, int studentId);
    List<Student> getStudentBySectionId(int sectionId);
    boolean updateStudentFromListById(int sectionId, int studentId);
    boolean deleteStudentFromListByStudentAndSectionId(int sectionId, int studentId);
    boolean deleteAllStudentFromListBySectionId(int sectionId);
    boolean deleteStudentFromListByStudentId(int studentId);
}
