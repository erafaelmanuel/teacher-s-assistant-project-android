package com.remswork.classmanager.model;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public class Clazz {

    private int id;
    private Teacher teacher;
    private Subject subject;
    private Section section;

    public Clazz(){
        super();
    }

    public Clazz(int id, Teacher teacher, Subject subject, Section section) {
        this();
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
