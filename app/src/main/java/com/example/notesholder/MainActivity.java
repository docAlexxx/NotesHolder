package com.example.notesholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < Notes.notes.length; i++) {
            Notes.notes[i] = new Notes(i, "Note" + (i + 1), "description" + (i + 1) + " and many other different words about something", "2" + (i + 1) + ".11.2021");
        };

        if (savedInstanceState == null) {
            NotesFragment notesFragment = new NotesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, notesFragment)
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about_point:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.fragment_container, new InfoFragment())
                        .commit();
                return true;
            case R.id.settings_point:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.fragment_container, new SettingsFragment())
                        .commit();
                return true;
            case R.id.exit_point:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}