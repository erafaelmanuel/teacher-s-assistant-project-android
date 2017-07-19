package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.StudentDatabaseHelper;
import com.remswork.classmanager.helper.service.StudentService;
import com.remswork.classmanager.model.Student;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class StudentServiceImpl implements StudentService {

    private StudentDatabaseHelper studentDatabaseHelper;

    public StudentServiceImpl(Context context){
        studentDatabaseHelper = new StudentDatabaseHelper(context);
    }

    @Override
    public boolean addStudent(final Student student) {
        return studentDatabaseHelper.addStudent(student);
    }

    @Override
    public Student getStudentById(final int id) {
        return studentDatabaseHelper.getStudentById(id);
    }

    @Override
    public List<Student> getStudentsByLastName(final String lastName) {
        return studentDatabaseHelper.getStudentsByLastName(lastName);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentDatabaseHelper.getAllStudent();
    }

    @Override
    public boolean updateStudentById(final int id, final Student newStudent) {
        return studentDatabaseHelper.updateStudentById(id, newStudent);
    }

    @Override
    public boolean deleteStudentById(final int id) {
        return studentDatabaseHelper.deleteStudentById(id);
    }
}
