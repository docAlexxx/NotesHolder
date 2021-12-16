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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Notes> data;
    public NotesAdapter adapter;
    private SharedPreferences sharedPref = null;
    public static final String KEY = "key";
    ArrayList<Notes> userNotes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        final ArrayList<Notes> userNotes = new ArrayList<>();
        final NotesAdapter notesAdapter = new NotesAdapter(userNotes,this);

        Utils.setSubtitleName(actionBar, "Notes List");
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = root.findViewById(R.id.recycler_notes_lines);
        data = Notes.notes;
        initRecyclerView(notesAdapter);
        String savedNotes = sharedPref.getString(KEY, null);
        if (savedNotes == null || savedNotes.isEmpty()) {
            Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<ArrayList<Notes>>() {
                }.getType();
                notesAdapter.setNewData(new GsonBuilder().create().fromJson(savedNotes, type));
                for (int i =0; i<notesAdapter.getItemCount(); i++){
                    userNotes.add(notesAdapter.getData(i));
                }
            } catch (JsonSyntaxException e) {
                Toast.makeText(getActivity(), "Ошибка трансформации", Toast.LENGTH_SHORT).show();
            }
        }
        return root;
    }



    private void initRecyclerView(NotesAdapter notesAdapter) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    //    adapter = new NotesAdapter(userNotes, this);
        recyclerView.setAdapter(notesAdapter);


        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //  Notes note = data.get(position);
                Notes.currentIndex = position;
                Notes currentNote = data.get(position);
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
                int size = data.size();
                data.add(new Notes(size, "Note " + (size + 1), "", (size + 1) + ".11.2021"));
                adapter.notifyItemInserted(size);
                recyclerView.scrollToPosition(size);
                Notes.currentIndex=size;
                showEditScreen(data.get(size));
                return true;
            case R.id.clear_point:
                data.clear();
                adapter.notifyDataSetChanged();
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
                data.remove(position);
                adapter.notifyItemRemoved(position);
                return true;
            case R.id.change_point:
                showEditScreen(data.get(position));
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