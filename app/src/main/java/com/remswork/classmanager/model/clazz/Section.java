package com.remswork.classmanager.model.clazz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public class Section implements Parcelable{

    private int id;
    private String sectionName;
    private int year;
    private String department;
    private List<Student> students;

    public Section(){
        super();
        students = new ArrayList<Student>();
    }

    public Section(int id, String sectionName, int year, String department) {
        this();
        this.id = id;
        this.sectionName = sectionName;
        this.year = year;
        this.department = department;
    }

    public Section(int id, String sectionName, int year, String department,
                   List<Student> students) {
        this(id, sectionName, year, department);
        this.students = students;
    }

    protected Section(Parcel in) {
        id = in.readInt();
        sectionName = in.readString();
        year = in.readInt();
        department = in.readString();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", sectionName='" + sectionName + '\'' +
                ", year=" + year +
                ", department='" + department + '\'' +
                ", students size=" + students.size() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(sectionName);
        dest.writeInt(year);
        dest.writeString(department);
    }
}
