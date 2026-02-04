package com.example.myapplication.models.relations;


import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.subjects.Subject;

import java.util.List;


public class SubjectWithStudents {

    @Embedded
    public Subject subject;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = SubjectStudentCrossRef.class,
                    parentColumn = "subjectId",
                    entityColumn = "studentId"
            )
    )
    public List<Student> students;
}
