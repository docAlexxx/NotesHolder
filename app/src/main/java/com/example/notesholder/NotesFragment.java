package com.example.notesholder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeList(view);
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
                showDescription(index);
            });
        }
    }

    private void showDescription(int index) {
        descriptionFragment fragment = descriptionFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        ((FragmentTransaction) transaction).commit();
    }
}