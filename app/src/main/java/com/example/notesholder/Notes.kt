package com.example.notesholder

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import java.util.*

class Notes : Parcelable {
    @JvmField
    var name: String?
    var description: String?
    @JvmField
    var date: Date
    var noteIndex: Int

    constructor(noteIndex: Int, name: String?, description: String?, date: Date) {
        this.noteIndex = noteIndex
        this.name = name
        this.description = description
        this.date = date
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        description = `in`.readString()
        date = Date(`in`.readLong())
        noteIndex = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
        dest.writeLong(date.time)
        dest.writeInt(noteIndex)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        var notes: ArrayList<Notes>? = null
        @JvmField
        var currentIndex = -1
        const val KEY = "key"
        @JvmField
        val CREATOR: Creator<Notes?> = object : Creator<Notes?> {
            override fun createFromParcel(`in`: Parcel): Notes? {
                return Notes(`in`)
            }

            override fun newArray(size: Int): Array<Notes?> {
                return arrayOfNulls(size)
            }
        }
    }
}