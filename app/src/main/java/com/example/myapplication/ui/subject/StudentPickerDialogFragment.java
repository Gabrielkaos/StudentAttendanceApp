package com.example.myapplication.ui.subject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.models.relations.SubjectStudentCrossRef;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.students.StudentDao;
import com.example.myapplication.ui.student.StudentViewModel;

import java.util.ArrayList;
import java.util.List;

public class StudentPickerDialogFragment extends DialogFragment {

    private static final String ARG_SUBJECT_ID = "subject_id";

    private int subjectId;
    private SubjectViewModel subjectViewModel;

    private AlertDialog dialog;

    public static StudentPickerDialogFragment newInstance(int subjectId) {
        StudentPickerDialogFragment fragment = new StudentPickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SUBJECT_ID, subjectId);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            subjectId = getArguments().getInt(ARG_SUBJECT_ID);
        }

        subjectViewModel = new ViewModelProvider(requireActivity()).get(SubjectViewModel.class);

        ScrollView scrollView = new ScrollView(requireContext());
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);

        dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Add Students to Subject")
                .setView(scrollView)
                .setPositiveButton("Add", null) // we’ll override later
                .setNegativeButton("Cancel", null)
                .create();

        AppDatabase db = AppDatabase.getInstance(requireContext());
        StudentDao studentDao = db.studentDao();
        studentDao.getAllStudents().observe(this, allStudents -> {
            layout.removeAllViews();
            if(allStudents == null) return;

            subjectViewModel.getStudentsForSubject(subjectId).observe(this, alreadyInSubject -> {
                List<CheckBox> checkBoxes = new ArrayList<>();

                List<Student> pickerList = new ArrayList<>();
                for (Student s : allStudents) {
                    boolean inSubject = false;
                    for (Student subStu : alreadyInSubject) {
                        if (subStu.getId() == s.getId()) {
                            inSubject = true;
                            break;
                        }
                    }
                    if (!inSubject) {
                        pickerList.add(s);  // only students not already in the subject
                        CheckBox cb = new CheckBox(requireContext());
                        cb.setText(s.firstName + " " + s.lastName + " - " + s.course + " id="+s.id);
                        layout.addView(cb);
                        checkBoxes.add(cb);
                    }
                }

                // Add checked students


                // Override Add button to handle selections
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isChecked()) {
                            Student selected = pickerList.get(i);  // <-- use filtered list
                            subjectViewModel.addStudentToSubject(subjectId, selected.getId());
                        }
                    }
                    dialog.dismiss();
                });
            });
        });

        return dialog;
    }
}
