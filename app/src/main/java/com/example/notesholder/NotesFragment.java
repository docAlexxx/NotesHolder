package com.example.notesholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesholder.ui.NotesAdapter;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Date;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
  //  private ArrayList<Notes> data;
    public NotesAdapter adapter;
    private SharedPreferences sharedPref = null;
    Date current = new Date();

    private void toSharedPref() {
        String jsonNotes = new GsonBuilder().create().toJson(Notes.notes);
        sharedPref.edit().putString(Notes.KEY, jsonNotes).apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Utils.setSubtitleName(actionBar, "Notes List");
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = root.findViewById(R.id.recycler_notes_lines);
        initRecyclerView();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return root;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(Notes.notes, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Notes.currentIndex = position;
                Notes currentNote = Notes.notes.get(position);
                showDescription(currentNote);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showDescription(Notes notes) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, DescriptionFragment.newInstance(notes))
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_point:
                int size = Notes.notes.size();
                Notes.notes.add(new Notes(size, "Note " + (size + 1), "", current));
                adapter.notifyItemInserted(size);
                recyclerView.scrollToPosition(size);
                Notes.currentIndex = size;
                showEditScreen(Notes.notes.get(size));
                toSharedPref();
                return true;
            case R.id.clear_point:
                Notes.notes.clear();
                adapter.notifyDataSetChanged();
                toSharedPref();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.delete_point:
                Notes.notes.remove(position);
                adapter.notifyItemRemoved(position);
                toSharedPref();
                return true;
            case R.id.change_point:
                showEditScreen(Notes.notes.get(position));
                toSharedPref();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void showEditScreen(Notes notes) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, EditFragment.newInstance(notes))
                .addToBackStack("")
                .commit();
    }


}