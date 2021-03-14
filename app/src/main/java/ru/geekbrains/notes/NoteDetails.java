package ru.geekbrains.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class NoteDetails extends Fragment {

    private static final String ARG_PARAM1 = "note";

    private ArrayList<String> note;

    public static NoteDetails newInstance(ArrayList<String> param1) {
        NoteDetails fragment = new NoteDetails();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNoteDetails(view);
    }

    private void initNoteDetails(View view) {
        LinearLayout layoutView = (LinearLayout) view;

        for (int i = 1; i < note.size()-1; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(note.get(i));
            tv.setTextSize(30);
            layoutView.addView(tv);
        }

    }
}