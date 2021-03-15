package ru.geekbrains.notes;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class ListOfNotes extends Fragment {


    public ListOfNotes() {
        // Required empty public constructor
    }

    public static ListOfNotes newInstance() {
        ListOfNotes fragment = new ListOfNotes();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListOfNotes(view);
    }

    private void initListOfNotes(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        Notes notes = new Notes();
        notes.addNote("First note", "First note description",
                "First note large text");
        notes.addNote("Second note", "Second note description",
                "Second note large text");
        notes.addNote("Third note", "Third note description",
                "Third note large text");
        notes.addNote("Fourth note", "Fourth note description",
                "Fourth note large text");

        LinkedList<LinkedList<String>> allNotes = notes.getAllNotes();

        for (int i = 0; i < allNotes.size(); i++) {
            String noteName = allNotes.get(i).get(1);
            TextView tv = new TextView(getContext());
            tv.setText(noteName);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int noteIdx = i;
            tv.setOnClickListener(v -> showNoteDetails(allNotes.get(noteIdx)));
        }
    }

    private void showNoteDetails(LinkedList<String> strings) {
        Intent showNoteDetails = new Intent();
        showNoteDetails.setClass(getActivity(), NoteDetailsActivity.class);
        showNoteDetails.putExtra(NoteDetails.ARG_PARAM1, strings);
        startActivity(showNoteDetails);
    }
}
