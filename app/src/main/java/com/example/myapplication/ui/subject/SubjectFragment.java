package com.example.myapplication.ui.subject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.FabControl;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSubjectsBinding;
import com.example.myapplication.models.subjects.Subject;

public class SubjectFragment extends Fragment {
    private FragmentSubjectsBinding binding;
    private SubjectViewModel viewModel;
    private SubjectAdapter adapter;
    private FabControl fabControl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentSubjectsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        adapter = new SubjectAdapter();
        fabControl = (FabControl) requireActivity();


        fabControl.showFab();
        fabControl.setFabIcon(R.drawable.id_add);

        fabControl.setFabClickListener(() ->
                SubjectDialogFragment.newInstance(null)
                        .show(getChildFragmentManager(),"add_subject")
        );

        binding.subjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.subjectRecyclerView.setAdapter(adapter);

        viewModel.getSubjects().observe(getViewLifecycleOwner(),adapter::submitList);

        adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onClick(Subject subject) {

            }

            @Override
            public void onLongClick(Subject subject) {
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Delete Subject")
                        .setMessage("Are you sure you want to delete "
                                + subject.name + " " + subject.description + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            viewModel.deleteSubject(subject);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }


        });

        return binding.getRoot();
    }
}
