package com.remswork.classmanager.model.clazz;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by Rafael on 7/13/2017.
 */

public class Schedule implements Parcelable{

    private int id;
    private String day;
    private String time;
    private int hour;
    private String room;
    private int clazzId;
    private String subjectName;


    public Schedule(){
        super();
    }

    public Schedule(int id, String day, String time, int hour, String room,
                    int clazzId) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.hour = hour;
        this.room = room;
        this.clazzId = clazzId;
    }

    public Schedule(int id, String day, String time, int hour, String room,
                    int clazzId, String subjectName) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.hour = hour;
        this.room = room;
        this.clazzId = clazzId;
        this.subjectName = subjectName;
    }

    protected Schedule(Parcel in) {
        id = in.readInt();
        day = in.readString();
        time = in.readString();
        hour = in.readInt();
        room = in.readString();
        clazzId = in.readInt();
        subjectName = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

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

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", hour=" + hour +
                ", room='" + room + '\'' +
                ", clazzId=" + clazzId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(day);
        dest.writeString(time);
        dest.writeInt(hour);
        dest.writeString(room);
        dest.writeInt(clazzId);
        dest.writeString(subjectName);
    }
}
