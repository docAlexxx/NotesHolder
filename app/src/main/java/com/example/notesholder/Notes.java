package com.example.notesholder;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes {
    public String name;
    public String description;
    public String date;

    public Notes(String name, String description, String date) {
        this.name=name;
        this.description=description;
        this.date=date;
    }

    @NonNull
    @Override
    public String toString() {
      //  SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        return name + "\n\n" + description + "\n\n" + date;
    }
}
