package com.remswork.classmanager.model.grade;

/**
 * Created by Rafael on 7/21/2017.
 */

public class Syllabus {


    private int id;
    private double assignment;
    private double attendance;
    private double activity;
    private double behavior;
    private double exam;
    private double finalExam;
    private double performance;
    private double quiz;
    private double recitation;

    private Syllabus(){
        super();
    }

    public Syllabus(int id, double activity, double assignment, double attendance,
                    double behavior, double exam, double finalExam, double performance,
                    double quiz, double recitation) {
        this();
        this.id = id;
        this.activity = activity;
        this.assignment = assignment;
        this.attendance = attendance;
        this.behavior = behavior;
        this.exam = exam;
        this.finalExam = finalExam;
        this.performance = performance;
        this.quiz = quiz;
        this.recitation = recitation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public double getAssignment() {
        return assignment;
    }

    public void setAssignment(double assignment) {
        this.assignment = assignment;
    }

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public double getBehavior() {
        return behavior;
    }

    public void setBehavior(double behavior) {
        this.behavior = behavior;
    }

    public double getExam() {
        return exam;
    }

    public void setExam(double exam) {
        this.exam = exam;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public double getQuiz() {
        return quiz;
    }

    public void setQuiz(double quiz) {
        this.quiz = quiz;
    }

    public double getRecitation() {
        return recitation;
    }

    public void setRecitation(double recitation) {
        this.recitation = recitation;
    }

    @Override
    public String toString() {
        return "Syllabus{" +
                "id=" + id +
                ", activity=" + activity +
                ", assignment=" + assignment +
                ", attendance=" + attendance +
                ", behavior=" + behavior +
                ", exam=" + exam +
                ", finalExam=" + finalExam +
                ", performance=" + performance +
                ", quiz=" + quiz +
                ", recitation=" + recitation +
                '}';
    }
}
