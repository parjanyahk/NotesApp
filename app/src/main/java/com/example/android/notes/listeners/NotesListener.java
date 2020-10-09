package com.example.android.notes.listeners;

import com.example.android.notes.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
