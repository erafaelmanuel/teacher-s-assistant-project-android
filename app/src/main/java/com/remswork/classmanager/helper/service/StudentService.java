package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.Student;

import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public interface StudentService {

    boolean addStudent(Student student);
    int addStudents(List<Student> listOfStudents);
    int addStudents(Student... students);
    Student getStudentById(int id);
    List<Student> getStudentsByLastName(String lastName);
    List<Student> getAllStudent();
    boolean updateStudentById(int id, Student newStudent);
    boolean deleteStudentById(int id);
    int deleteAllStudent();
}
