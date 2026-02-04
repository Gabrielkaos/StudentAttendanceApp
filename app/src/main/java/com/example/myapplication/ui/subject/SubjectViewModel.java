package com.example.myapplication.ui.subject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.myapplication.data.repository.SubjectRepo;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.subjects.Subject;

import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private final SubjectRepo repository;
    private final LiveData<List<Subject>> subjects;

    public SubjectViewModel(@NonNull Application app) {
        super(app);
        repository = new SubjectRepo(app);
        subjects = repository.getSubjects();
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public LiveData<List<Student>> getStudentsForSubject(int subjectId) {
        return Transformations.map(repository.getSubjectWithStudents(subjectId),
                subjectWithStudents -> subjectWithStudents.students);
    }

    public void removeStudentFromSubject(int subjectId, int studentId) {
        repository.removeStudentFromSubject(subjectId, studentId);
    }


    public void addSubject(Subject subject) {
        repository.insert(subject);
    }

    public void updateSubject(Subject subject) {
        repository.update(subject);
    }

    public void deleteSubject(Subject subject) {
        repository.delete(subject);
    }
}