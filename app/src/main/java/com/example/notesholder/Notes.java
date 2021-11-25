package com.example.notesholder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes {
    public String name;
    public String description;
    public Date date;

    public Notes(String name, String description, Date date) {
        this.name=name;
        this.description=description;
        this.date=date;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        return name + "\n" + description + "\n" + formatDate.format(date);
    }
}
