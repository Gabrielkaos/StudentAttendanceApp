package com.example.myapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.models.relations.SubjectStudentCrossRef;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.students.StudentDao;
import com.example.myapplication.models.subjects.Subject;
import com.example.myapplication.models.subjects.SubjectDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Student.class,
        Subject.class,
        SubjectStudentCrossRef.class,
    },
        version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);
    private static AppDatabase INSTANCE;

    public abstract StudentDao studentDao();
    public abstract SubjectDao subjectDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;
    }
}
