package ru.geekbrains.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class ListOfNotes extends Fragment {

    private static final Notes NOTES = new Notes();
    private boolean isLandscape;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListOfNotes(view);

    }

    private void initListOfNotes(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        NOTES.clear();
        NOTES.addNote("First note", "First note description",
                "First note large text");
        NOTES.addNote("Second note", "Second note description",
                "Second note large text");
        NOTES.addNote("Third note", "Third note description",
                "Third note large text");
        NOTES.addNote("Fourth note", "Fourth note description",
                "Fourth note large text");

        LinkedList<LinkedList<String>> allNotes = NOTES.getAllNotes();

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void showNoteDetails(LinkedList<String> note) {
        if (isLandscape) {
            showLandNoteDetails(note);
        } else {
            showPortNoteDetails(note);
        }
    }

    private void showLandNoteDetails(LinkedList<String> note) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_fragment_container, new NoteDetails(note));
        fragmentTransaction.commit();
//        NoteDetails details = NoteDetails.newInstance(new ArrayList<>(note));
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_note_details, details);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.commit();
    }

    private void showPortNoteDetails(LinkedList<String> note) {
//        FragmentManager fragmentManager = getParentFragmentManager();
//        fragmentManager.popBackStack();
        addFragment(new NoteDetails(note));
//        Intent showNoteDetails = new Intent();
//        showNoteDetails.setClass(getActivity(), NoteDetailsActivity.class);
//        showNoteDetails.putExtra(NoteDetails.ARG_PARAM1, note);
//        startActivity(showNoteDetails);
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
