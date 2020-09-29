package com.example.android.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.android.notes.Notes;
import com.example.android.notes.NotesDao;
import com.example.android.notes.NotesDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {

    public Notes notes;

    private static NotesRepository repository = null;

    private NotesDao notesDao;

    private static int PAGE_SIZE = 15;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NotesRepository(Application application) {
        NotesDatabase db = NotesDatabase.getInstance(application);
        notesDao = db.notesDao();
    }
    public static NotesRepository getRepository(Application application) {
        if (repository == null) {
            synchronized (NotesRepository.class) {
                if (repository == null) {
                    repository = new NotesRepository(application);
                }
            }
        }
        return repository;
    }

    public void insertNotes(final Notes notes){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.insertNotes(notes);
            }
        });
    }

    public void updateNotes(final Notes notes){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.updateNotes(notes);

            }
        });
    }

    public void deleteContact(final Notes notes){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.deleteNotes(notes);
            }
        });
    }

    public LiveData<PagedList<Notes>> getAllContacts(){
        return new LivePagedListBuilder<>(
                notesDao.getAllNotes(), PAGE_SIZE
        ).build();
    }
}