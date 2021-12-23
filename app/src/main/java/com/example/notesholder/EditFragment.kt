package com.example.notesholder

import android.content.Context
import com.example.notesholder.Utils.setSubtitleName
import com.example.notesholder.ChangeResult
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import com.example.notesholder.EditFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.notesholder.R
import android.widget.TextView
import android.widget.EditText
import com.google.gson.GsonBuilder
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.notesholder.DialogChangeDataFragment
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment(), ChangeResult {
    private var notes: Notes? = null
    private var sharedPref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
        if (arguments != null) {
            notes = arguments!!.getParcelable<Parcelable>(ARG_INDEX_2) as Notes?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        setSubtitleName(actionBar, "Edit")
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (notes != null) {
            val tvName = view.findViewById<TextView>(R.id.text_view_name2)
            val editScreen = view.findViewById<EditText>(R.id.edit_screen)
            tvName.text = notes!!.name
            editScreen.setText(notes!!.description)
            val buttonBack = view.findViewById<ImageView>(R.id.back_button2)
            buttonBack.setOnClickListener { v: View? -> requireActivity().supportFragmentManager.popBackStack() }
            val buttonSafe = view.findViewById<ImageView>(R.id.save_button)
            buttonSafe.setOnClickListener { v: View? ->
                dialogOnSafe()
                notes!!.description = editScreen.text.toString()
                val jsonNotes = GsonBuilder().create().toJson(Notes.notes)
                sharedPref!!.edit().putString(Notes.KEY, jsonNotes).apply()
            }
        }
    }

    private fun dialogOnSafe() {
        AlertDialog.Builder(requireActivity())
            .setTitle("The note is changed")
            .setMessage("Do you want to change note's name or date?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialogInterface, i ->
                showDialogFragmentCustomView()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("No") { dialogInterface, i -> requireActivity().supportFragmentManager.popBackStack() }
            .show()
    }

    fun showDialogFragmentCustomView() {
        DialogChangeDataFragment().show(
            requireActivity().supportFragmentManager,
            "DialogFragmentTAG"
        )
    }

    override fun onChangeResult(name: String, date: Date) {}

    companion object {
        const val ARG_INDEX_2 = "index"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param notes number of note.
         * @return A new instance of fragment descriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(notes: Notes?): EditFragment {
            val fragment2 = EditFragment()
            val args2 = Bundle()
            args2.putParcelable(ARG_INDEX_2, notes)
            fragment2.arguments = args2
            return fragment2
        }
    }
}