package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Subject;
import com.remswork.classmanager.model.clazz.Teacher;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public interface ClazzService {

    boolean addClazz(Clazz clazz);
    Clazz getClazzById(int id);
    Clazz getClazzesBySubject(Subject subject);
    Clazz getClazzesByTeacher(Teacher teacher);
    boolean updateClazzById(int id, Clazz clazz);
    boolean updateClazzTeacherId(int teacherId, Clazz clazz);
    boolean deleteClazzById(int id);
    boolean deleteClazzezByTeacher(int teacherId);
}
