package com.example.myapplication.ui.subject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FabControl;
import com.example.myapplication.R;
import com.example.myapplication.models.students.Student;
import com.example.myapplication.models.subjects.Subject;
import com.example.myapplication.ui.student.StudentAdapter;
import com.example.myapplication.ui.student.StudentDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SubjectDetailFragment extends Fragment {

    private static final String ARG_SUBJECT = "subject";

    private Subject subject;
    private SubjectViewModel viewModel;

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private FloatingActionButton fab;
    private FabControl fabControl;

    public static SubjectDetailFragment newInstance(Subject subject) {
        SubjectDetailFragment fragment = new SubjectDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SUBJECT, subject);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_subject_detail, container, false);

        //hide the global fab
        fabControl = (FabControl) requireActivity();
        fabControl.hideFab();

        recyclerView = view.findViewById(R.id.recyclerStudents);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        fab = view.findViewById(R.id.fabAddStudent);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        if (getArguments() != null) {
            subject = (Subject) getArguments().getSerializable(ARG_SUBJECT);
        }

        if (subject == null) {
            throw new IllegalStateException("SubjectDetailFragment requires a Subject");
        }

        observeStudents();
        setupFab();

        return view;
    }

    private void updateUI(List<Student> students) {
        if (students == null || students.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        tvEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        StudentAdapter adapter = new StudentAdapter(
                students,
                student -> {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Remove Student from Subject")
                            .setMessage("Are you sure you want to remove from subject "
                                    + student.lastName + ", " + student.firstName + "?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                viewModel.removeStudentFromSubject(subject.getId(),student.getId());
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                },
                student -> {
                    //TODO
                }
        );

        recyclerView.setAdapter(adapter);
    }

    private void observeStudents() {
        viewModel.getStudentsForSubject(subject.id)
                .observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void setupFab() {
        fab.setImageResource(R.drawable.id_add);
        fab.setOnClickListener(v -> {
            // Navigate to picker dialog / screen
            StudentPickerDialogFragment
                    .newInstance(subject.id)
                    .show(getParentFragmentManager(), "pick_students");
        });


    }
}
