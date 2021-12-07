package com.example.notesholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogChangeDataFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customView = inflater.inflate(R.layout.datachange_view, null);

        ChangeResult dialogResult = (ChangeResult) requireActivity();
        EditText editName = customView.findViewById(R.id.edit_text_name);
        EditText editDate = customView.findViewById(R.id.edit_text_date);
        editName.setText((CharSequence) Notes.notes[Notes.currentIndex].name);
        editDate.setText((CharSequence) Notes.notes[Notes.currentIndex].date);

        customView.findViewById(R.id.button_submit).setOnClickListener(view -> {
            String name = editName.getText().toString();
            String date = editDate.getText().toString();
            dialogResult.onChangeResult(name, date);

            dismiss();
        });

        customView.findViewById(R.id.button_cancel).setOnClickListener(view -> {
            dismiss();
        });

        setCancelable(false);
        return customView;
    }

}
