package ru.geekbrains.notes;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Notes {
    private static final LinkedList<LinkedList<String>> allNotes = new LinkedList<>();

    @SuppressLint("SimpleDateFormat")
    public void addNote(String noteName, String noteDescription, String noteText) {
        LinkedList<String> note = new LinkedList<>();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        note.add(formatForDateNow.format(dateNow));
        note.add(noteName);
        note.add(noteDescription);
        note.add(noteText);
        allNotes.add(note);
    }

    public LinkedList<LinkedList<String>> getAllNotes() {
        return allNotes;
    }

}
