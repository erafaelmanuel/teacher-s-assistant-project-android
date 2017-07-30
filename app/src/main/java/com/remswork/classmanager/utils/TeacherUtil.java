package com.remswork.classmanager.utils;

import com.remswork.classmanager.model.clazz.Teacher;

/**
 * Created by Rem-sama on 7/30/2017.
 */

public class TeacherUtil {

    private static Teacher teacher;

    public static Teacher putTeacher(Teacher teacher){
        return TeacherUtil.teacher = teacher;
    }

    public static Teacher getTeacher(){
        return teacher;
    }
}
