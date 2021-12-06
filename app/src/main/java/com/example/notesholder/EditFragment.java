package com.example.notesholder;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    static final String ARG_INDEX_2 = "index";
    private Notes notes;

    public EditFragment() {
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
    public static EditFragment newInstance(Notes notes) {
        EditFragment fragment2 = new EditFragment();
        Bundle args2 = new Bundle();
        args2.putParcelable(ARG_INDEX_2, notes);
        fragment2.setArguments(args2);
        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (Notes) getArguments().getParcelable(ARG_INDEX_2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setSubtitle("Edit");
        }
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (notes != null) {
            TextView tvName = view.findViewById(R.id.text_view_name2);
            EditText editScreen = view.findViewById(R.id.edit_screen);

            tvName.setText(notes.name);
            editScreen.setText(notes.description);

            ImageView buttonBack = view.findViewById(R.id.back_button2);
            buttonBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });

            ImageView buttonSafe = view.findViewById(R.id.save_button);
            buttonSafe.setOnClickListener(v -> {
                dialogOnSafe();
               // notes.description = editScreen.getText().toString();
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }
    }

    private void dialogOnSafe() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("The note is changed")
                .setMessage("Do you want to change note's name or date?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}