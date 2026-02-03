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
import com.example.myapplication.models.students.Student;

public class StudentDialogFragment extends DialogFragment {

    private static final String ARG_STUDENT = "student";

    public static StudentDialogFragment newInstance(@Nullable Student student) {
        StudentDialogFragment fragment = new StudentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STUDENT, student);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean validate(
            EditText etId,
            EditText etFirst,
            EditText etLast,
            EditText etCourse,
            EditText etYear
    ) {
        boolean valid = true;

        if (etId.getText().toString().trim().isEmpty()) {
            etId.setError("Student ID required");
            valid = false;
        }

        if (etFirst.getText().toString().trim().isEmpty()) {
            etFirst.setError("First name required");
            valid = false;
        }

        if (etLast.getText().toString().trim().isEmpty()) {
            etLast.setError("Last name required");
            valid = false;
        }

        if (etCourse.getText().toString().trim().isEmpty()) {
            etCourse.setError("Course required");
            valid = false;
        }

        String yearStr = etYear.getText().toString().trim();
        if (yearStr.isEmpty()) {
            etYear.setError("Year required");
            valid = false;
        } else {
            try {
                int year = Integer.parseInt(yearStr);
                if (year < 1 || year > 6) {
                    etYear.setError("Year must be between 1 and 6");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                etYear.setError("Invalid number");
                valid = false;
            }
        }

        return valid;
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

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(existing == null ? "Add Student" : "Edit Student")
                .setView(view)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(v -> {
                        if (!validate(etId, etFirst, etLast, etCourse, etYear)) {
                            return;
                        }

                        StudentViewModel vm =
                                new ViewModelProvider(requireParentFragment())
                                        .get(StudentViewModel.class);

                        Student updated = new Student(
                                etId.getText().toString().trim(),
                                etFirst.getText().toString().trim(),
                                "",
                                etLast.getText().toString().trim(),
                                etCourse.getText().toString().trim(),
                                Integer.parseInt(etYear.getText().toString().trim())
                        );

                        if (finalExisting != null) {
                            updated.id = finalExisting.id;
                            vm.updateStudent(updated);
                        } else {
                            vm.addStudent(updated);
                        }
                        dialog.dismiss();
                    });
        });

        return dialog;
    }
}
