package com.example.notesholder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;


public class Notes implements Parcelable {
    public String name;
    public String description;
    public Date date;
    public int noteIndex;
    static ArrayList<Notes> notes;
    static int currentIndex = -1;
    public static final String KEY = "key";

    public Notes(int noteIndex, String name, String description, Date date) {
        this.noteIndex = noteIndex;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    protected Notes(Parcel in) {
        name = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
        noteIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(date.getTime());
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

}


