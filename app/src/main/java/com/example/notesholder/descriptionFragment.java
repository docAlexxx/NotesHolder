package com.example.notesholder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link descriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class descriptionFragment extends Fragment {

    static final String ARG_INDEX = "index";
    private int index;

    public descriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index number of note.
     * @return A new instance of fragment descriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static descriptionFragment newInstance(int index) {
        descriptionFragment fragment = new descriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
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

        TextView tvName = view.findViewById(R.id.text_view_name);
        TextView tvDescription = view.findViewById(R.id.text_view_description);
        TextView tvDate = view.findViewById(R.id.text_view_date);


        Notes[] fullNotes = new Notes[7];
        for (int i = 0; i < fullNotes.length; i++) {
            fullNotes[i] = new Notes("Note" + (i + 1), "description" + (i + 1) + " and many other different words about something", "2" + (i + 1) + ".11.2021");
        }

        tvName.setText(fullNotes[index].name);
        tvDescription.setText(fullNotes[index].description);
        tvDate.setText(fullNotes[index].date);


    }
}