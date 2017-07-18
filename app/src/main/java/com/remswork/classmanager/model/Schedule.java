package com.remswork.classmanager.model;

import java.util.Date;
/**
 * Created by Rafael on 7/13/2017.
 */

public class Schedule {

    private int id;
    private String day;
    private String time;
    private int hour;
    private String room;
    private Clazz clazz;


    public Schedule(){
        super();
    }

    public Schedule(int id, String day, String time, int hour, String room,
                    Clazz clazz) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.hour = hour;
        this.room = room;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", hour=" + hour +
                ", clazz=" + clazz +
                '}';
    }
}
