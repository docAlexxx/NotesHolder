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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link descriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class descriptionFragment extends Fragment {

    static final String ARG_INDEX = "index";
    private Notes notes;

    public descriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notes number of note.
     * @return A new instance of fragment descriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static descriptionFragment newInstance(Notes notes) {
        descriptionFragment fragment = new descriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (Notes) getArguments().getParcelable(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (notes != null) {
            TextView tvName = view.findViewById(R.id.text_view_name);
            TextView tvDescription = view.findViewById(R.id.text_view_description);
            TextView tvDate = view.findViewById(R.id.text_view_date);


            Notes[] fullNotes = new Notes[7];
            for (int i = 0; i < fullNotes.length; i++) {
                fullNotes[i] = new Notes(i, "Note" + (i + 1), "description" + (i + 1) + " and many other different words about something", "2" + (i + 1) + ".11.2021");
            }

            tvName.setText(fullNotes[notes.noteIndex].name);
            tvDescription.setText(fullNotes[notes.noteIndex].description);
            tvDate.setText(fullNotes[notes.noteIndex].date);

            ImageView buttonBack = view.findViewById(R.id.back_button1);
            buttonBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();

            });

            ImageView buttonEdit = view.findViewById(R.id.edit_button);
            buttonBack.setOnClickListener(v -> {
                showEditScreen(notes);
            });
        }
    }
    private void showEditScreen(Notes notes) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, EditFragment.newInstance(notes))
                .addToBackStack("")
                .commit();
    }

}