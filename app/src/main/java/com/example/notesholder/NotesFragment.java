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

import com.example.notesholder.ui.NotesAdapter;

public class NotesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        Utils.setSubtitleName(actionBar,"Notes List");

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
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  makeList(view);
    }

    private void makeList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);


        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView tvNote = new TextView(getContext());
            tvNote.setText(note);
            tvNote.setTextSize(30);
            tvNote.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            layoutView.addView(tvNote);
            final int index = i;
            tvNote.setOnClickListener(v -> {
              //  Notes currentNote = new Notes(index, note, "description" + (index + 1) + " and many other different words about something", "2" + (index + 1) + ".11.2021");
                Notes currentNote= Notes.notes[index];
                Notes.currentIndex=index;
                showDescription(currentNote);
            });
        }
    }


    private void showDescription(Notes notes) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, DescriptionFragment.newInstance(notes))
                .addToBackStack("")
                .commit();
    }
}