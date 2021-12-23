package com.example.notesholder

import android.content.Context
import android.content.SharedPreferences
import android.widget.DatePicker
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.notesholder.R
import com.example.notesholder.ChangeResult
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.gson.GsonBuilder
import java.util.*

class DialogChangeDataFragment : DialogFragment() {
    private var sharedPref: SharedPreferences? = null
    var editDate: DatePicker? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.datachange_view, null)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val dialogResult = requireActivity() as ChangeResult
        val editName = customView.findViewById<EditText>(R.id.edit_text_name)
        editDate = customView.findViewById(R.id.inputDate)
        editName.setText(Notes.notes[Notes.currentIndex].name as CharSequence)
        initDatePicker(Notes.notes[Notes.currentIndex].date)
        customView.findViewById<View>(R.id.button_submit).setOnClickListener { view: View? ->
            val name = editName.text.toString()
            val date = dateFromDatePicker
            dialogResult.onChangeResult(name, date)
            val jsonNotes = GsonBuilder().create().toJson(Notes.notes)
            sharedPref!!.edit().putString(Notes.KEY, jsonNotes).apply()
            dismiss()
        }
        customView.findViewById<View>(R.id.button_cancel)
            .setOnClickListener { view: View? -> dismiss() }
        isCancelable = false
        return customView
    }

    private fun initDatePicker(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        editDate!!.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            null
        )
    }

    private val dateFromDatePicker: Date
        private get() {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = editDate!!.year
            cal[Calendar.MONTH] = editDate!!.month
            cal[Calendar.DAY_OF_MONTH] = editDate!!.dayOfMonth
            return cal.time
        }
}