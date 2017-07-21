package com.remswork.classmanager.helper.service.impl;

import com.remswork.classmanager.helper.service.ClazzService;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Subject;
import com.remswork.classmanager.model.clazz.Teacher;

/**
 * Created by Rafael on 7/18/2017.
 */

public class ClazzServiceImpl implements ClazzService {

    @Override
    public boolean addClazz(Clazz clazz) {
        return false;
    }

    @Override
    public Clazz getClazzById(int id) {
        return null;
    }

    @Override
    public Clazz getClazzesBySubject(Subject subject) {
        return null;
    }

    @Override
    public Clazz getClazzesByTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public boolean updateClazzById(int id, Clazz clazz) {
        return false;
    }

    @Override
    public boolean updateClazzTeacherId(int teacherId, Clazz clazz) {
        return false;
    }

    @Override
    public boolean deleteClazzById(int id) {
        return false;
    }

    @Override
    public boolean deleteClazzezByTeacher(int teacherId) {
        return false;
    }
}
