package com.example.myapplication.ui.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.students.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> students = new ArrayList<>();
    private OnStudentLongClickListener onStudentLongClickListener;

    private OnStudentClickListener clickListener;

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        this.clickListener = listener;
    }


    public void setOnStudentLongClickListener(OnStudentLongClickListener onStudentLongClickListener) {
        this.onStudentLongClickListener = onStudentLongClickListener;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Student student = students.get(position);
        holder.name.setText(student.firstName + "" + student.lastName);
        holder.info.setText(student.course + " - Year " + student.year);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(student);
            }
        });


        holder.itemView.setOnLongClickListener(v -> {
            if(onStudentLongClickListener != null){
                onStudentLongClickListener.onLongClick(student);
            }
            return true;
        });
    }

    @Override
    public int getItemCount(){
        return students.size();
    }

    public interface OnStudentClickListener {
        void onClick(Student student);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, info;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            info = itemView.findViewById(R.id.studentInfo);
        }
    }

    public interface OnStudentLongClickListener {
        void onLongClick(Student student);
    }

}
