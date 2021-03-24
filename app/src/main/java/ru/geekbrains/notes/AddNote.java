package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import ru.geekbrains.notes.ui.home.HomeListAdapter;

public class AddNote extends Fragment {
    private HomeListAdapter mAdapter;

    public AddNote(HomeListAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        ExtendedFloatingActionButton fab_save_note = view.findViewById(R.id.fab_save_note);
        EditText noteTitle = view.findViewById(R.id.note_add_title);
        EditText noteAbout = view.findViewById(R.id.note_add_about);
        EditText noteContent = view.findViewById(R.id.note_add_content);
        fab_save_note.setOnClickListener(v -> {
            if (TextUtils.isEmpty(noteTitle.getText())) {
                noteTitle.setError("Заполните заголовок");
                noteTitle.setFocusableInTouchMode(true);
                noteTitle.requestFocus();
            } else {
                FragmentManager fragmentManager = getParentFragmentManager();
                LinkedList<String> note = new LinkedList<>();
                Date dateNow = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                note.add(formatForDateNow.format(dateNow));
                note.add(noteTitle.getText().toString());
                note.add(noteAbout.getText().toString());
                note.add(noteContent.getText().toString());
                Notes.allNotes.add(note);
                mAdapter.notifyItemInserted(Notes.allNotes.size());
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}