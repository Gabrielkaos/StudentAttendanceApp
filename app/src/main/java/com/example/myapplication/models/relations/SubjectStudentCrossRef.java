package com.example.myapplication.models.relations;

import androidx.room.Entity;

@Entity(primaryKeys = {"subjectId","studentId"})
public class SubjectStudentCrossRef {

    public int subjectId;
    public int studentId;

    public SubjectStudentCrossRef(int subjectId, int studentId){
        this.subjectId = subjectId;
        this.studentId = studentId;
    }
}
