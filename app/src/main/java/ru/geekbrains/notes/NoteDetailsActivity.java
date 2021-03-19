package ru.geekbrains.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import java.util.ArrayList;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            finish();
//            return;
//        }
//
//        if (savedInstanceState == null) {
//            ArrayList<String> details  = getIntent().getStringArrayListExtra("note");
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_note_details, NoteDetails.newInstance(details)).commit();
//        }

    }
}