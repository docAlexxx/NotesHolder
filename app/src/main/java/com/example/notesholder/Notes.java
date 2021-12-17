package com.example.notesholder;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Notes implements Parcelable {
    public String name;
    public String description;
    public String date;
    public int noteIndex;
    static ArrayList<Notes> notes;
    static int currentIndex = -1;
    public static final String KEY = "key";

    public Notes(int noteIndex, String name, String description, String date) {
        this.noteIndex = noteIndex;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public Notes(int noteIndex) {
        this.noteIndex = noteIndex;
        this.name = "Note " + (noteIndex + 1);
        this.description = "description" + (noteIndex + 1) + " and many other different words about something";
        this.date = (noteIndex + 1) + ".11.2021";
    }

    protected Notes(Parcel in) {
        name = in.readString();
        description = in.readString();
        date = in.readString();
        noteIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeInt(noteIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        //  SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        return name + "\n\n" + description + "\n\n" + date;
    }

}


