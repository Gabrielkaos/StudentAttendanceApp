package com.example.myapplication.ui.student;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.FabControl;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentStudentsBinding;
import com.example.myapplication.ui.subject.SubjectDialogFragment;

public class StudentFragment extends Fragment {

    private FragmentStudentsBinding binding;
    private StudentAdapter adapter;
    private FabControl fabControl;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        StudentViewModel studentViewModel =
                new ViewModelProvider(this).get(StudentViewModel.class);
        binding = FragmentStudentsBinding.inflate(inflater, container,false);

        fabControl = (FabControl) requireActivity();

        fabControl.showFab();
        fabControl.setFabIcon(R.drawable.id_add);
        fabControl.setFabClickListener(()-> {
            StudentDialogFragment
                    .newInstance(null)
                    .show(getChildFragmentManager(), "AddStudent");
        });

        adapter = new StudentAdapter();
        binding.studentRecyclerView.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(getContext())
        );
        binding.studentRecyclerView.setAdapter(adapter);
        adapter.setOnStudentLongClickListener( student -> {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete "
                            + student.firstName + " " + student.lastName + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        studentViewModel.deleteStudent(student);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        adapter.setOnStudentClickListener(student -> {
            StudentDialogFragment
                    .newInstance(student)
                    .show(getChildFragmentManager(), "EditStudent");
        });




        studentViewModel.getStudents().observe(getViewLifecycleOwner(),
                students -> adapter.setStudents(students));

        return binding.getRoot();
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if (fabControl != null) {
            fabControl.hideFab(); // hide when leaving fragment
        }
        binding = null;
    }
}
