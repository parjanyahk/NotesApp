package com.example.android.notes;

import androidx.paging.DataSource;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface NotesDao {

    @Insert
    void insertNotes(Notes notes);
    @Update
    void updateNotes(Notes notes);
    @Delete
    void deleteNotes(Notes notes);
    @Query("Select * from Notes ORDER BY ID asc")
    DataSource.Factory<Integer,Notes> getAllNotes();

}
