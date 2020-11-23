package com.example.pin;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, exportSchema = false, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase noteDataBase;


    public static synchronized NoteDataBase getNoteDataBase(Context context) {
        if (noteDataBase == null) {
            noteDataBase = Room.databaseBuilder(context, NoteDataBase.class, "notes_db").build();
        }
        return noteDataBase;
    }

    public abstract NoteDao noteDao();


}
