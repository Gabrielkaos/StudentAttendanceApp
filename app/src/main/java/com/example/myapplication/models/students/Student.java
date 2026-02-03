package com.example.myapplication.models.students;

import java.io.Serializable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
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
