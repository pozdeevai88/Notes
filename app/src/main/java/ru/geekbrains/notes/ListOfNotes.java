package ru.geekbrains.notes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import ru.geekbrains.notes.ui.home.HomeListAdapter;

public class ListOfNotes extends Fragment {

    private static final Notes NOTES = new Notes();
    private boolean isLandscape;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
//            Snackbar.make(view, "Добавление заметок пока недоступно", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            showNoteAdd();
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView(recyclerView);
        return view;
    }

    private void showNoteAdd() {
        if (isLandscape) {
            showLandNoteAdd();
        } else {
            showPortNoteAdd();
        }
    }

    private void showLandNoteAdd() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_fragment_container, new AddNote());
        fragmentTransaction.commit();
    }

    private void showPortNoteAdd() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new AddNote());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        HomeListAdapter adapter = new HomeListAdapter(NOTES);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.SetOnItemClickListener((view, position) -> showNoteDetails(NOTES.getAllNotes().get(position)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }

    private void showPortNoteDetails(LinkedList<String> note) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new NoteDetails(note));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
