package com.example.notesholder;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesholder.ui.NotesAdapter;

public class NotesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Utils.setSubtitleName(actionBar, "Notes List");

        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_notes_lines);
        String[] data = getResources().getStringArray(R.array.notes);
        initRecyclerView(recyclerView, data);
        return root;
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NotesAdapter adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                String note = data[position];
                Notes.currentIndex = position;
                Notes currentNote = Notes.notes[position];
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
        transaction.add(R.id.fragment_container, DescriptionFragment.newInstance(notes))
                .addToBackStack("")
                .commit();
    }
}