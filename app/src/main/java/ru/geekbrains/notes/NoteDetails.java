package ru.geekbrains.notes;

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

    private void initNoteDetails(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < mNote.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(mNote.get(i));
            tv.setTextSize(30);
            layoutView.addView(tv);
        }
    }
}