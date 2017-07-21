package com.remswork.classmanager.model.grade.factor;

/**
 * Created by Rafael on 7/21/2017.
 */

public class Attendance {

    private int id;
    private String date;
    private int number;
    private int studentId;
    private int subjectId;
    private int sectionId;
    private int termId;

    public Attendance(){
        super();
    }

    public Attendance(int id, String date, int number, int studentId, int subjectId,
                      int sectionId, int termId) {
        this();
        this.id = id;
        this.date = date;
        this.number = number;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.sectionId = sectionId;
        this.termId = termId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", number=" + number +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", sectionId=" + sectionId +
                ", termId=" + termId +
                '}';
    }
}
