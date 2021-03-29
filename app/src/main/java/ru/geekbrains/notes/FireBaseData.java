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
    public static boolean isEOL = false;

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
                            FireBaseData.isEOL = true;
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    });
    }

    public void addNewNote() {
        Map<String, Object> newNote = new HashMap<>();
        newNote.put("first", "Ada");
        newNote.put("last", "Lovelace");
        newNote.put("born", 1815);

        db.collection("notes")
                .add(newNote)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
