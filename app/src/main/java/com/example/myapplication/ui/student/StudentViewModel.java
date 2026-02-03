package com.example.myapplication.ui.student;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.students.StudentDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StudentViewModel extends AndroidViewModel {

    private final StudentDao studentDao;
    private final LiveData<List<Student>> students;

    public StudentViewModel(@NonNull Application application){
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        studentDao = db.studentDao();
        students = studentDao.getAllStudents();
    }

    public LiveData<List<Student>> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        Executors.newSingleThreadExecutor().execute(()->{
            studentDao.insert(student);
        });
    }

    public void deleteStudent(Student student){
        Executors.newSingleThreadExecutor().execute(()->{
            studentDao.delete(student);
        });
    }

    public void updateStudent(Student student){
        Executors.newSingleThreadExecutor().execute(()->{
            studentDao.update(student);
        });
    }


}
