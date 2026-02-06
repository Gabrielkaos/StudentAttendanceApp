package com.example.myapplication.ui.subject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.subjects.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> subjects = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(Subject subject);
        void onLongClick(Subject subject);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public void submitList(List<Subject> list) {
        subjects = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView name, desc;

        SubjectViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvSubjectName);
            desc = itemView.findViewById(R.id.tvSubjectDesc);

            itemView.setOnClickListener(v ->
                    listener.onClick(subjects.get(getAdapterPosition()))
            );

            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(subjects.get(getAdapterPosition()));
                return true;
            });
        }

        void bind(Subject s) {
            name.setText(s.name + " id = " + s.id);
            desc.setText(s.description);
        }
    }

}
