package com.example.myapplication.ui.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.models.Student;

public class StudentDialogFragment extends DialogFragment {

    private static final String ARG_STUDENT = "student";

    public static StudentDialogFragment newInstance(@Nullable Student student) {
        StudentDialogFragment fragment = new StudentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STUDENT, student);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_student,null);

        EditText etId = view.findViewById(R.id.etStudentId);
        EditText etFirst = view.findViewById(R.id.etFirstName);
        EditText etLast = view.findViewById(R.id.etLastName);
        EditText etCourse = view.findViewById(R.id.etCourse);
        EditText etYear = view.findViewById(R.id.etYear);

        Student existing = null;
        if(getArguments() != null){
            existing = (Student) getArguments().getSerializable(ARG_STUDENT);
        }

        if (existing != null) {
            etId.setText(existing.studentId);
            etFirst.setText(existing.firstName);
            etLast.setText(existing.lastName);
            etCourse.setText(existing.course);
            etYear.setText(String.valueOf(existing.year));
        }

        Student finalExisting = existing;

        return new AlertDialog.Builder(requireContext())
                .setTitle(existing == null ? "Add Student" : "Edit Student")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {

                    StudentViewModel vm =
                            new ViewModelProvider(requireParentFragment())
                                    .get(StudentViewModel.class);

                    Student updated = new Student(
                            etId.getText().toString(),
                            etFirst.getText().toString(),
                            "",
                            etLast.getText().toString(),
                            etCourse.getText().toString(),
                            Integer.parseInt(etYear.getText().toString())
                    );

                    if (finalExisting == null) {
                        vm.addStudent(updated);
                    } else {
                        vm.updateStudent(finalExisting, updated);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
