package com.example.myapplication.ui.student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentViewModel extends ViewModel {

    private final MutableLiveData<List<Student>> students = new MutableLiveData<>();

    public StudentViewModel(){
        List<Student> demo = new ArrayList<>();
        demo.add(new Student("2024-001", "Juan", "", "Dela Cruz", "BSCS", 3));
        demo.add(new Student("2024-002", "Maria", "S.", "Reyes", "BSIT", 2));
        students.setValue(demo);
    }

    public LiveData<List<Student>> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        List<Student> list = students.getValue();
        if(list==null)list = new ArrayList<>();
        list.add(student);
        students.setValue(list);
    }

    public void deleteStudent(Student student){
        List<Student> list = students.getValue();
        if(list==null)list = new ArrayList<>();
        list.remove(student);
        students.setValue(list);
    }

    public void updateStudent(Student oldStudent, Student newStudent){
        List<Student> list = students.getValue();
        if (list == null) return;

        int index = list.indexOf(oldStudent);
        if (index != -1) {
            list.set(index, newStudent);
            students.setValue(list);
        }
    }


}
