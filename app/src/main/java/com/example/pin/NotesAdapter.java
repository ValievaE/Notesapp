package com.example.pin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{
    private List<Note> notes;
    private NotesListener notesListener;


    public NotesAdapter(List<Note> notes, NotesListener notesListener) {

        this.notes = notes;
        this.notesListener = notesListener;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item, parent, false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListener.onNoteClicked(notes.get(position), position);
            }
        });
        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notesListener.onLongNoteClicked(notes.get(position), position);

                return true;

            }
        });



    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewText, textViewDate;
        LinearLayout layoutNote;


        NoteViewHolder(@NonNull View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewText = itemView.findViewById(R.id.textViewText);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            layoutNote = itemView.findViewById(R.id.layoutNote);
        }


        void setNote(Note note) {
            if (note.getTitle().trim().isEmpty()) {
                textViewTitle.setVisibility(View.GONE);
            } else {
                textViewTitle.setText(note.getTitle());
            }
            if (note.getNoteText().trim().isEmpty()) {
                textViewText.setVisibility(View.GONE);
            } else {
                textViewText.setText(note.getNoteText());
            }
            if (note.getDate().trim().isEmpty()) {
                textViewDate.setVisibility(View.GONE);
            } else {
                textViewDate.setText(note.getDate());
            }
        }
    }




}
