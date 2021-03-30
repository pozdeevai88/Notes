package ru.geekbrains.notes;

import android.util.Log;
import android.widget.Adapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.perfmark.Link;
import ru.geekbrains.notes.ui.home.HomeListAdapter;

public class FireBaseData extends Thread {
    private final String TAG = "NOTE_LOG";
    private final FirebaseFirestore db;

    public FireBaseData() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void readAllNotes() {
        db.collection("notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            LinkedList<String> n = new LinkedList<>();
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            n.add(document.getString("date"));
                            n.add(document.getString("noteName"));
                            n.add(document.getString("noteDescr"));
                            n.add(document.getString("noteText"));
                            Notes.allNotes.add(n);
                            ListOfNotes.adapter.notifyItemInserted(Notes.allNotes.size());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public void addNewNote(LinkedList<String> note) {
        Map<String, Object> newNote = new HashMap<>();
        newNote.put("date", note.get(0));
        newNote.put("noteName", note.get(1));
        newNote.put("noteDescr", note.get(2));
        newNote.put("noteText", note.get(3));

        db.collection("notes")
                .add(newNote)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    ListOfNotes.adapter.notifyItemInserted(Notes.allNotes.size());
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public void removeNote(LinkedList<String> note, int position) {
        Log.d(TAG, "Log to deleting = " + note.toString());
        Log.d(TAG, "Position to deleting = " + position);
        db.collection("notes")
                .whereEqualTo("noteName", note.get(1))
                .whereEqualTo("noteDescr", note.get(2))
                .whereEqualTo("noteText", note.get(3))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String id = document.getId();
                            Log.d(TAG, "Deleted id = " + id);
                            db.collection("notes").document(id).delete();
                            ListOfNotes.adapter.notifyItemRemoved(position);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}
