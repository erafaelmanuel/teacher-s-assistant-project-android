package com.remswork.classmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael24 on 7/5/2017.
 */

public class Subject implements Parcelable{

    private int id;
    private String name;
    private String desc;
    private int unit;

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public Subject(Parcel source){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
