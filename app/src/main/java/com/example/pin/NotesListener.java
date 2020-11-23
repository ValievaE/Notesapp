package com.example.pin;

public interface NotesListener {
    void onNoteClicked (Note note, int position);
    void onLongNoteClicked(Note note, int position);
}
