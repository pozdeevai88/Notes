package ru.geekbrains.notes;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

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
        db.collection("notes")
                .whereEqualTo("date", note.get(0))
                .whereEqualTo("noteName", note.get(1))
                .whereEqualTo("noteDescr", note.get(2))
                .whereEqualTo("noteText", note.get(3))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String id = document.getId();
                            db.collection("notes").document(id).delete();
                            ListOfNotes.adapter.notifyItemRemoved(position);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public void editNote(LinkedList<String> oldNote, LinkedList<String> newNote) {
        Map<String, Object> editedNote = new HashMap<>();
        editedNote.put("date", newNote.get(0));
        editedNote.put("noteName", newNote.get(1));
        editedNote.put("noteDescr", newNote.get(2));
        editedNote.put("noteText", newNote.get(3));

        db.collection("notes")
                .whereEqualTo("date", oldNote.get(0))
                .whereEqualTo("noteName", oldNote.get(1))
                .whereEqualTo("noteDescr", oldNote.get(2))
                .whereEqualTo("noteText", oldNote.get(3))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String id = document.getId();
                            db.collection("notes")
                                    .document(id)
                                    .update(editedNote);
                            ListOfNotes.adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}
