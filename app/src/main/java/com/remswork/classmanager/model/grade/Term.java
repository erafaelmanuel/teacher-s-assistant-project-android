package com.remswork.classmanager.model.grade;

/**
 * Created by Rafael on 7/21/2017.
 */

public class Term {

    private int id;
    private String code;
    private String name;
    private int clazzId;

    public Term(){
        super();
    }

    public Term(int id, String code, String name, int clazzId) {
        this();
        this.id = id;
        this.code = code;
        this.name = name;
        this.clazzId = clazzId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", clazzId=" + clazzId +
                '}';
    }
}