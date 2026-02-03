package com.example.myapplication.models.students;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Query ("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();

    @Update
    void update(Student student);

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);


}
