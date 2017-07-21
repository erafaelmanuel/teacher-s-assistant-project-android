package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.TeacherDatabaseHelper;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.model.clazz.Teacher;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafael on 7/13/2017.
 */

public class TeacherServiceImpl implements TeacherService {

    private TeacherDatabaseHelper teacherDatabaseHelper;

    public TeacherServiceImpl(Context context){
        teacherDatabaseHelper = new TeacherDatabaseHelper(context);
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherDatabaseHelper.addTeacher(teacher);
    }

    @Override
    public List<Teacher> getListOfTeacher() {
        return teacherDatabaseHelper.getListOfTeacher();
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teacherDatabaseHelper.getTeacherById(id);
    }

    @Override
    public Teacher getTeacherByEmail(String email) {
        return teacherDatabaseHelper.getTeacherByEmail(email);
    }

    @Override
    public HashMap getTeacherAuthenticate(String email, String password) {
        return teacherDatabaseHelper.getTeacherAuthenticate(email, password);
    }

    @Override
    public boolean updateTeacherById(int id, Teacher newTeacher) {
        return teacherDatabaseHelper.updateTeacherById(id, newTeacher);
    }

    @Override
    public boolean deleteTeacherById(int id) {
        return teacherDatabaseHelper.deleteTeacherById(id);
    }
}