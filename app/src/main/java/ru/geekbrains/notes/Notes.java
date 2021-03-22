package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Notes {
    private static final LinkedList<LinkedList<String>> allNotes = new LinkedList<>();

    public Notes() {
        this.clear();
        // По нормальому всё должно храниться в базе, а не в ресурсах в виде строк. И не так
        // Но пока так
        this.addNote("First note", "First note description",
                "First note large text");
        this.addNote("Second note", "Second note description",
                "Second note large text");
        this.addNote("Third note", "Third note description",
                "Third note large text");
        this.addNote("Fourth note", "Fourth note description",
                "Fourth note large text");
    }

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

    public void clear() {
        allNotes.clear();
    }

}
