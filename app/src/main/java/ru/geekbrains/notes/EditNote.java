package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
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

public class EditNote extends Fragment {
    private HomeListAdapter mAdapter;
    private LinkedList<String> mNote;
    private int idx;

    public EditNote(LinkedList<String> note, int idx, HomeListAdapter adapter) {
        this.mNote = note;
        this.idx = idx;
        this.mAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        ExtendedFloatingActionButton fab_save_note = view.findViewById(R.id.fab_save_note);
        EditText noteTitle = view.findViewById(R.id.note_add_title);
        EditText noteAbout = view.findViewById(R.id.note_add_about);
        EditText noteContent = view.findViewById(R.id.note_add_content);
        noteTitle.setHint(mNote.get(1));
        noteAbout.setHint(mNote.get(2));
        noteContent.setHint(mNote.get(3));
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
//                Notes.allNotes.remove(idx);
//                Notes.allNotes.add(idx, note);
                Notes.editNote(idx, note);
//                mAdapter.notifyDataSetChanged();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) fragmentManager.popBackStack();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}