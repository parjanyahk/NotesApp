package com.example.android.notes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.notes.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Note> getALLNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}