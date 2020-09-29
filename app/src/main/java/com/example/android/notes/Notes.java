package com.example.android.notes;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")

public class Notes {
    @PrimaryKey (autoGenerate = true)
    private long ID;

    @ColumnInfo (name = "Title")
    private String title;

    @ColumnInfo (name = "Content")
    private String content;

    public Notes(long ID, String title, String content) {
        this.ID = ID;
        this.title = title;
        this.content = content;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}