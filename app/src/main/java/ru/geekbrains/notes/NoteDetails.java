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

public class NoteDetails extends Fragment {

    public static final String ARG_PARAM1 = "note";
    private ArrayList<String> mNote;

    public static NoteDetails newInstance(ArrayList<String> note) {
        NoteDetails fragment = new NoteDetails();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
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