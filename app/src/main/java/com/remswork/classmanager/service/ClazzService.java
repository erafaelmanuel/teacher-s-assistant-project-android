package com.remswork.classmanager.service;

import com.remswork.classmanager.model.Clazz;
import com.remswork.classmanager.model.Subject;
import com.remswork.classmanager.model.Teacher;

import static android.R.attr.id;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public interface ClazzService {

    boolean addClazz(Clazz clazz);
    Clazz getClazzById(int id);
    Clazz getClazzesBySubject(Subject subject);
    Clazz getClazzesByTeacher(Teacher teacher);
    boolean updateClazz(Clazz clazz);
    boolean updateClazzesByTeacher(Teacher teacher);
    boolean deleteClazzById(int id);
    boolean deleteClazzezByTeacher(Teacher teacher);
}
