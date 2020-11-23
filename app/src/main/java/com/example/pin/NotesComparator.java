package com.example.pin;

import java.util.Comparator;

public class NotesComparator implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {

        return o1.getDate().compareTo(o2.getDate());
    }

    @Override
    public Comparator<Note> reversed() {
        return null;
    }

}
