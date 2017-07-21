package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Teacher;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafael on 7/13/2017.
 */

public interface TeacherService {

    boolean addTeacher(Teacher teacher);
    List<Teacher> getListOfTeacher();
    Teacher getTeacherById(int id);
    Teacher getTeacherByEmail(String email);
    HashMap getTeacherAuthenticate(String email, String password);
    boolean updateTeacherById(int id, Teacher newTeacher);
    boolean deleteTeacherById(int id);
}
