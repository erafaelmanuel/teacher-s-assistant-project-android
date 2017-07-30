package com.remswork.classmanager.model.clazz;

import com.remswork.classmanager.model.grade.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/18/2017.
 */

public class Clazz {

    private int id;
    private Teacher teacher;
    private Subject subject;
    private Section section;
    private List<Schedule> listOfSchedule;
    private String termType;
    private List<Term> listOfTerm;

    public Clazz() {
        super();
        listOfSchedule = new ArrayList<Schedule>();
        listOfTerm = new ArrayList<Term>();
    }

    public Clazz(int id, Teacher teacher, Subject subject, Section section,
                 String termType, List<Term> listOfTerm) {
        this();
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.section = section;
        this.termType = termType;
        this.listOfTerm = listOfTerm;
    }

    public Clazz(int id, Teacher teacher, Subject subject, Section section,
                 List<Schedule> listOfSchedule, String termType, List<Term> listOfTerm) {
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.section = section;
        this.listOfSchedule = listOfSchedule;
        this.termType = termType;
        this.listOfTerm = listOfTerm;
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

    public List<Schedule> getListOfSchedule() {
        return listOfSchedule;
    }

    public void setListOfSchedule(List<Schedule> listOfSchedule) {
        this.listOfSchedule = listOfSchedule;
    }

    public void addScheduleToList(Schedule schedule){
        listOfSchedule.add(schedule);
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public List<Term> getListOfTerm() {
        return listOfTerm;
    }

    public void setListOfTerm(List<Term> listOfTerm) {
        this.listOfTerm = listOfTerm;
    }

    public void addTermToList(Term term){
        listOfTerm.add(term);
    }


    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", section=" + section +
                ", listOfSchedule=" + listOfSchedule +
                ", termType='" + termType + '\'' +
                ", listOfTerm=" + listOfTerm +
                '}';
    }
}
