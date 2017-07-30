package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.grade.Syllabus;

import java.util.List;

/**
 * Created by Rafael on 7/23/2017.
 */

public interface SyllabusService {

    boolean addSyllabus(Syllabus syllabus);
    Syllabus getSyllabusById(int id);
    List<Syllabus> getAllSyllabus();
    boolean updateSyllabusById(int id, Syllabus newSyllabus);
    boolean deleteSyllabusById(int id);
    int deleteAllSyllabus();
}
