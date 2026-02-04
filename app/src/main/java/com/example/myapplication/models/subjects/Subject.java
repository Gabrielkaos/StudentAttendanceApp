package com.example.myapplication.models.subjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "subject")
public class Subject implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;

    public int getId() {
        return id;
    }


    public Subject(String name, String description){
        this.name = name;
        this.description = description;
    }
}
