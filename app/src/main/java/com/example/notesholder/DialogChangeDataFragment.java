package com.example.notesholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;

public class DialogChangeDataFragment extends DialogFragment {
    private SharedPreferences sharedPref = null;
    DatePicker editDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customView = inflater.inflate(R.layout.datachange_view, null);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        ChangeResult dialogResult = (ChangeResult) requireActivity();
        EditText editName = customView.findViewById(R.id.edit_text_name);
        editDate = customView.findViewById(R.id.inputDate);
        editName.setText((CharSequence) Notes.notes.get(Notes.currentIndex).name);
        initDatePicker(Notes.notes.get(Notes.currentIndex).date);
        // editDate.setText((CharSequence) Notes.notes.get(Notes.currentIndex).date);

        customView.findViewById(R.id.button_submit).setOnClickListener(view -> {
            String name = editName.getText().toString();
            //String date = editDate.getText().toString();
            Date date = getDateFromDatePicker();
            dialogResult.onChangeResult(name, date);
            String jsonNotes = new GsonBuilder().create().toJson(Notes.notes);
            sharedPref.edit().putString(Notes.KEY, jsonNotes).apply();
            dismiss();
        });

        customView.findViewById(R.id.button_cancel).setOnClickListener(view -> {
            dismiss();
        });

        setCancelable(false);
        return customView;
    }



    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.editDate.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.editDate.getYear());
        cal.set(Calendar.MONTH, this.editDate.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.editDate.getDayOfMonth());
        return cal.getTime();
    }

}
