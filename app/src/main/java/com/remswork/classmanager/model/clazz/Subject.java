package com.remswork.classmanager.model.clazz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 7/5/2017.
 */

public class Subject implements Parcelable {

    private int id;
    private String name;
    private String code;
    private String desc;
    private int unit;
    private String category;

    public Subject(){
        super();
    }

    public Subject(int id, String name, String code, String desc, int unit, String category) {
        this();
        this.id = id;
        this.name = name;
        this.code = code;
        this.desc = desc;
        this.unit = unit;
        this.category = category;
    }

    protected Subject(Parcel in) {
        id = in.readInt();
        name = in.readString();
        code = in.readString();
        desc = in.readString();
        unit = in.readInt();
        category = in.readString();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unit=" + unit +
                ", category=" + category +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(desc);
        dest.writeInt(unit);
        dest.writeString(category);
    }
}
