package com.example.myapplication.ui.subject;

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
import com.example.myapplication.models.subjects.Subject;

public class SubjectDialogFragment extends DialogFragment {

    private static final String ARG_SUBJECT = "subject";

    public static SubjectDialogFragment newInstance(@Nullable Subject subject) {
        SubjectDialogFragment f = new SubjectDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_SUBJECT, subject);
        f.setArguments(b);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_subject, null);

        EditText etName = view.findViewById(R.id.etSubjectName);
        EditText etDesc = view.findViewById(R.id.etSubjectDesc);

        Subject existing = null;
        if (getArguments() != null) {
            existing = (Subject) getArguments().getSerializable(ARG_SUBJECT);
        }

        if (existing != null) {
            etName.setText(existing.name);
            etDesc.setText(existing.description);
        }

        Subject finalExisting = existing;

        return new AlertDialog.Builder(requireContext())
                .setTitle(existing == null ? "Add Subject" : "Edit Subject")
                .setView(view)
                .setPositiveButton("Save", (d, w) -> {
                    SubjectViewModel vm =
                            new ViewModelProvider(requireParentFragment())
                                    .get(SubjectViewModel.class);

                    Subject s = new Subject(
                            etName.getText().toString(),
                            etDesc.getText().toString()
                    );

                    if (finalExisting == null) {
                        vm.addSubject(s);
                    } else {
                        s.id = finalExisting.id;
                        vm.updateSubject(s);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
