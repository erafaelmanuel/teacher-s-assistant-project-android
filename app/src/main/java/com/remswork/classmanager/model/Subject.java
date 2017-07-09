package com.remswork.classmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.name;
import static android.R.attr.targetId;

/**
 * Created by Rafael on 7/5/2017.
 */

public class Subject implements Parcelable{

    private int id;
    private String title;
    private String code;
    private String desc;
    private int unit;
    private int imageIcon;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(code);
        dest.writeString(desc);
        dest.writeInt(unit);
        dest.writeInt(imageIcon);
    }

    public Subject(){
        super();
    }

    public Subject(Parcel source){
        id = source.readInt();
        title = source.readString();
        code = source.readString();
        desc = source.readString();
        unit = source.readInt();
        imageIcon = source.readInt();
    }

    public Subject(int id, String title, String code, String desc, int unit, int imageIcon) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.desc = desc;
        this.unit = unit;
        this.imageIcon = imageIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(int imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unit=" + unit +
                ", imageIcon=" + imageIcon +
                '}';
    }
}
