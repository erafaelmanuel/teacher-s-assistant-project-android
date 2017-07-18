package com.remswork.classmanager.service.impl;

import com.remswork.classmanager.model.Clazz;
import com.remswork.classmanager.model.Subject;
import com.remswork.classmanager.model.Teacher;
import com.remswork.classmanager.service.ClazzService;

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
    public boolean updateClazz(Clazz clazz) {
        return false;
    }

    @Override
    public boolean updateClazzesByTeacher(Teacher teacher) {
        return false;
    }

    @Override
    public boolean deleteClazzById(int id) {
        return false;
    }

    @Override
    public boolean deleteClazzezByTeacher(Teacher teacher) {
        return false;
    }
}
