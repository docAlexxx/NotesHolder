package com.example.notesholder;

import static com.example.notesholder.descriptionFragment.ARG_INDEX;

import android.app.Activity;
import android.content.Intent;
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

    private static final String CURRENT_NOTE = "CurrentNote";
    private int currentIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        makeList(view);

        if (Utils.isLandscape(getResources())){
            showDescription(currentIndex);
        }
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
                currentIndex = index;
                showDescription(index);
            });
        }
    }

    private void showDescription(int position) {
        if (Utils.isLandscape(getResources())) {
            showDescriptionLandscape(position);
        } else {
            showDescriptionPortrait(position);
        }
    }

    private void showDescriptionPortrait(int index) {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, DescriptionActivity.class);
        intent.putExtra(ARG_INDEX, index);
        activity.startActivity(intent);
    }

    private void showDescriptionLandscape(int index) {
        descriptionFragment fragment = descriptionFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.description_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ((FragmentTransaction) transaction).commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentIndex);
        super.onSaveInstanceState(outState);
    }
}