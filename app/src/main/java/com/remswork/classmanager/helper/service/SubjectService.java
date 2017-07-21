package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Subject;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public interface SubjectService {

    boolean addSubject(Subject subject);
    Subject getSubjectById(int id);
    Subject getSubjectByName(String name);
    List<Subject> getAllSubject();
    boolean updateSubjectById(int id, Subject newSubject);
    boolean deleteSubjectById(int id);

}
