package com.example.notesholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChangeResult {

    private Resources resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   if (Notes.currentIndex == -1) {
     //       int length = Notes.startLength;
     //       for (int i=0; i < length; i++){
     //          Notes.notes.add( new Notes(i));
     //       }
     //   }

        Notes.notes = new ArrayList<>();

        if (savedInstanceState == null) {
            addFragment(new NotesFragment());
        }
        setupToolbar();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupNavigationDrawer(toolbar);
    }

    private void setupNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.about_point:
                    openAboutFragment();
                    drawer.closeDrawers();
                    return true;
                case R.id.settings_point:
                    openSettingsFragment();
                    drawer.closeDrawers();
                    return true;
                case R.id.exit_point:
                    dialogBeforeExit();
                    return true;
            }
            return false;
        });
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
                openAboutFragment();
                return true;
            case R.id.settings_point:
                openSettingsFragment();
                return true;
            case R.id.exit_point:
                dialogBeforeExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, new InfoFragment())
                .commit();
    }

    private void openSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "NotesHolder has closed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    private void dialogBeforeExit() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setIcon(R.drawable.exit)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    Snackbar.make(findViewById(R.id.container), "You still stay in the App", Snackbar.LENGTH_LONG).show();
                })
                .show();
    }

    @Override
    public void onChangeResult(String name, String date) {
        Notes.notes.get(Notes.currentIndex).name=name;
        Notes.notes.get(Notes.currentIndex).date = date;
    }
}