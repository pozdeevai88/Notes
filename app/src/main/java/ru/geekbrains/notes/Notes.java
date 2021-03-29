package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import ru.geekbrains.notes.ui.home.HomeListAdapter;

public class Notes {
    public static LinkedList<LinkedList<String>> allNotes = new LinkedList<>();
    public static FireBaseData fireBaseData;

    public Notes() {
        fireBaseData = new FireBaseData();
        fireBaseData.readAllNotes();
    }

    @SuppressLint("SimpleDateFormat")
    public static void addNote(LinkedList<String> newNote) {
        LinkedList<String> note = new LinkedList<>();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        note.add(formatForDateNow.format(dateNow));
        note.add(newNote.get(1));
        note.add(newNote.get(2));
        note.add(newNote.get(3));
        allNotes.add(note);
        fireBaseData.addNewNote(note);
    }

    public LinkedList<LinkedList<String>> getAllNotes() {
        return allNotes;
    }

    public void clear() {
        allNotes.clear();
    }

}
