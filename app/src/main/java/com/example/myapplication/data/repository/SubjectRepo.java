package com.example.myapplication.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.models.relations.SubjectStudentCrossRef;
import com.example.myapplication.models.relations.SubjectWithStudents;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.subjects.Subject;
import com.example.myapplication.models.subjects.SubjectDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubjectRepo {
    private final SubjectDao subjectDao;
    private final LiveData<List<Subject>> subjects;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SubjectRepo(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        subjectDao = db.subjectDao();
        subjects = subjectDao.getAllSubjects();
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public LiveData<List<Student>> getStudentsForSubject(int subjectId) {
        return subjectDao.getStudentsForSubject(subjectId);
    }

    public LiveData<SubjectWithStudents> getSubjectWithStudents(int subjectId) {
        return subjectDao.getSubjectWithStudents(subjectId);
    }



    public void removeStudentFromSubject(int subjectId, int studentId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                subjectDao.removeStudentFromSubject(subjectId, studentId)
        );
    }

    public void insertCrossRef(int subjectId, int studentId){
        Log.d("CROSS_REF",
                "Inserting subjectId=" + subjectId + ", studentId=" + studentId);
        executor.execute(() -> {
                subjectDao.insertCrossRef(
                        new SubjectStudentCrossRef(subjectId, studentId)
                );
        });

    }


    public void insert(Subject subject) {
        executor.execute(() -> subjectDao.insert(subject));
    }

    public void update(Subject subject) {
        executor.execute(() -> subjectDao.update(subject));
    }

    public void delete(Subject subject) {
        executor.execute(() -> subjectDao.delete(subject));
    }
}
