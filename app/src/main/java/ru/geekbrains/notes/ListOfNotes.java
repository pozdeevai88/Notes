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

import java.util.LinkedList;

public class ListOfNotes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListOfNotes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOfNotes.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfNotes newInstance(String param1, String param2) {
        ListOfNotes fragment = new ListOfNotes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        }

    }
}
