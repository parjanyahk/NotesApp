package com.example.android.notes;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = (Notes.class),version = 1,exportSchema = false)

public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    public static NotesDatabase INSTANCE=null;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    public static NotesDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class,
                            "NotesDatabase"
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    prepopulateDb(context.getAssets(), INSTANCE.notesDao());
                                }
                            });
                        }
                    }).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
    private static void prepopulateDb(AssetManager assetManager, NotesDao NotesDao){
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open("todo.json"))
            );
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null){
                stringBuilder.append(mLine);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try{
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray todos = jsonObject.getJSONArray("todo");
            for (int i = 0; i < todos.length(); i++){
                JSONObject todo = todos.getJSONObject(i);
                String title = todo.getString("title");
                String content = todo.getString("content");
                int priority = todo.getInt("priority");
                NotesDao.insertNotes(new Notes(0L,title, content));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
