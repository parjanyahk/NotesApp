package com.example.android.notes;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {
    public Notes notes;
    private static NotesRepository repository = null;
    private NotesDao notesDao;
    private static int PAGE_SIZE=15;
    private ExecutorService executor= Executors.newSingleThreadExecutor();
    public NotesRepository(Application application){
        NotesDatabase db = NotesDatabase.getInstance(application);
        notesDao = db.notesDao();
    }
}
