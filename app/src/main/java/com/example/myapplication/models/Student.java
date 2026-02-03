package com.example.myapplication.models;

import java.io.Serializable;

public class Student implements Serializable {

    public String studentId;
    public String firstName;
    public String lastName;
    public String middleName;
    public String course;
    public int year;

    public Student(String studentId, String firstName, String middleName,
                   String lastName, String course, int year) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.course = course;
        this.year = year;
    }


}
