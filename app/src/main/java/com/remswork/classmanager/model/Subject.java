package com.remswork.classmanager.model;

/**
 * Created by Rafael on 7/5/2017.
 */

public class Subject {

    private int id;
    private String name;
    private String code;
    private String desc;
    private int unit;
    private int imageIcon;

    public Subject(){
        super();
    }

    public Subject(int id, String name, String code, String desc, int unit, int imageIcon) {
        this();
        this.id = id;
        this.name = name;
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
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unit=" + unit +
                ", imageIcon=" + imageIcon +
                '}';
    }
}
