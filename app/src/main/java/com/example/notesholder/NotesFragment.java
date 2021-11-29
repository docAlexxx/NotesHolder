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

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private Notes currentNote = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = (Notes) savedInstanceState.getParcelable(CURRENT_NOTE);
        }

        makeList(view);

        if (Utils.isLandscape(getResources())) {
            showDescription(currentNote);
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
                currentNote = new Notes(index, "Note" + (index + 1), "description" + (index + 1) + " and many other different words about something", "2" + (index + 1) + ".11.2021");
                showDescription(currentNote);
            });
        }
    }

    private void showDescription(Notes notes) {
        if (Utils.isLandscape(getResources())) {
            showDescriptionLandscape(notes);
        } else {
            showDescriptionPortrait(notes);
        }
    }

    private void showDescriptionPortrait(Notes notes) {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, DescriptionActivity.class);
        intent.putExtra(ARG_INDEX, notes);
        activity.startActivity(intent);
    }

    private void showDescriptionLandscape(Notes notes) {
        descriptionFragment fragment = descriptionFragment.newInstance(notes);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.description_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ((FragmentTransaction) transaction).commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}