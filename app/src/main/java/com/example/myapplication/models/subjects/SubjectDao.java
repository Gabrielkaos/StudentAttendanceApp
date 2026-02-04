package com.example.myapplication.models.subjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.models.relations.SubjectStudentCrossRef;
import com.example.myapplication.models.relations.SubjectWithStudents;
import com.example.myapplication.models.students.Student;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subject")
    LiveData<List<Subject>> getAllSubjects();

    @Transaction
    @Query("SELECT * FROM subject WHERE id = :subjectId")
    LiveData<SubjectWithStudents> getSubjectWithStudents(int subjectId);

    @Transaction
    @Query("SELECT students.id as id, students.studentId, students.firstName, students.middleName, students.lastName, students.course, students.year FROM students INNER JOIN SubjectStudentCrossRef ON students.id = SubjectStudentCrossRef.studentId WHERE SubjectStudentCrossRef.subjectId = :subjectId")
    LiveData<List<Student>> getStudentsForSubject(int subjectId);

    @Insert
    void insert(Subject subject);

    @Update
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Insert
    void insertCrossRef(SubjectStudentCrossRef crossRef);

    @Query("DELETE FROM SubjectStudentCrossRef WHERE subjectId = :subjectId AND studentId = :studentId")
    void removeStudentFromSubject(int subjectId, int studentId);
}
