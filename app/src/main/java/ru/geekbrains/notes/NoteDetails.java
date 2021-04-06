package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.LinkedList;
import ru.geekbrains.notes.ui.home.HomeListAdapter;

public class NoteDetails extends Fragment {

    private HomeListAdapter mAdapter;
    private LinkedList<String> mNote;
    private int idx;
    private boolean isLandscape;

    public NoteDetails(LinkedList<String> note, int position, HomeListAdapter adapter) {
        this.mNote = note;
        this.idx = position;
        this.mAdapter = adapter;
    }

    public NoteDetails() {
        LinkedList<String> emptyNote = new LinkedList<>();
        emptyNote.add("");
        this.mNote = emptyNote;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        ExtendedFloatingActionButton fab_edit_note = view.findViewById(R.id.fab_edit_note);
        if (mAdapter != null) fab_edit_note.setVisibility(View.VISIBLE);
        fab_edit_note.setOnClickListener(v -> {
            showNoteEdit(mNote, idx);
        });
        return view;
    }

    private void showNoteEdit(LinkedList<String> mNote, int idx) {
        if (isLandscape) {
            showLandNoteEdit(mNote, idx);
        } else {
            showPortNoteEdit(mNote, idx);
        }
    }

    private void showLandNoteEdit(LinkedList<String> mNote, int idx) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_fragment_container, new EditNote(mNote, idx, mAdapter));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showPortNoteEdit(LinkedList<String> mNote, int idx) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new EditNote(mNote, idx, mAdapter));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNoteDetails(view);
    }

    @SuppressLint("SetTextI18n")
    private void initNoteDetails(View view) {
        LinearLayout layoutView = view.findViewById(R.id.details_container);
        for (int i = 0; i < mNote.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setPadding(20,10,0,0);
            if (i == 0) {
                tv.setTextSize(22);
                tv.setText("Создано " + mNote.get(i));
            }
            if (i == 1) {
                tv.setTextSize(22);
                tv.setText("Название: " + mNote.get(i));
            }
            if (i == 2) {
                tv.setTextSize(22);
                tv.setText("Описание: " + mNote.get(i));
            }
            if (i == 3) {
                tv.setTextSize(20);
                tv.setText("Текст заметки:");
                tv.setPadding(20,60,0,0);
                layoutView.addView(tv);
                tv = new TextView(getContext());
                tv.setTextSize(30);
                tv.setText(mNote.get(i));
                tv.setPadding(20,10,0,0);
            }
            layoutView.addView(tv);
        }
    }
}