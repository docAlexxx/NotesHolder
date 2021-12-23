package com.example.notesholder

import com.example.notesholder.Utils.setSubtitleName
import com.example.notesholder.EditFragment.Companion.newInstance
import android.os.Bundle
import android.os.Parcelable
import com.example.notesholder.DescriptionFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.notesholder.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notesholder.EditFragment
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {
    private var notes: Notes? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            notes = requireArguments().getParcelable<Parcelable>(ARG_INDEX) as Notes?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        setSubtitleName(actionBar, "Description")
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (notes != null) {
            val tvName = view.findViewById<TextView>(R.id.text_view_name1)
            val tvDescription = view.findViewById<TextView>(R.id.text_view_description)
            val tvDate = view.findViewById<TextView>(R.id.text_view_date)
            tvName.text = notes!!.name
            tvDescription.text = notes!!.description
            tvDate.text = SimpleDateFormat("dd-MMM-yyyy").format(notes!!.date)
            val buttonBack = view.findViewById<ImageView>(R.id.back_button1)
            buttonBack.setOnClickListener { v: View? -> requireActivity().supportFragmentManager.popBackStack() }
            val buttonEdit = view.findViewById<ImageView>(R.id.edit_button)
            buttonEdit.setOnClickListener { v: View? -> showEditScreen(notes) }
        }
    }

    fun showEditScreen(notes: Notes?) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EditFragment.newInstance(notes))
            .addToBackStack("")
            .commit()
    }

    companion object {
        const val ARG_INDEX = "index"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param notes number of note.
         * @return A new instance of fragment descriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(notes: Notes?): DescriptionFragment {
            val fragment = DescriptionFragment()
            val args = Bundle()
            args.putParcelable(ARG_INDEX, notes)
            fragment.arguments = args
            return fragment
        }
    }
}