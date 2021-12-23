package com.example.notesholder

import android.content.Context
import com.example.notesholder.Utils.setSubtitleName
import com.example.notesholder.DescriptionFragment.Companion.newInstance
import com.example.notesholder.EditFragment.Companion.newInstance
import androidx.recyclerview.widget.RecyclerView
import com.example.notesholder.ui.NotesAdapter
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.notesholder.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesholder.DescriptionFragment
import android.view.ContextMenu.ContextMenuInfo
import androidx.fragment.app.Fragment
import com.example.notesholder.EditFragment
import java.util.*

class NotesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    var adapter: NotesAdapter? = null
    private var sharedPref: SharedPreferences? = null
    var current = Date()
    private fun toSharedPref() {
        val jsonNotes = GsonBuilder().create().toJson(Notes.notes)
        sharedPref!!.edit().putString(Notes.KEY, jsonNotes).apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        setSubtitleName(actionBar, "Notes List")
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        recyclerView = root.findViewById(R.id.recycler_notes_lines)
        initRecyclerView()
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return root
    }

    private fun initRecyclerView() {
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        adapter = NotesAdapter(Notes.notes, this)
        recyclerView!!.adapter = adapter
        adapter!!.setOnItemClickListener { view, position ->
            Notes.currentIndex = position
            val currentNote = Notes.notes!![position]
            showDescription(currentNote)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showDescription(notes: Notes) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, DescriptionFragment.newInstance(notes))
            .addToBackStack("")
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_point -> {
                val size = Notes.notes!!.size
                Notes.notes!!.add(Notes(size, "Note " + (size + 1), "", current))
                adapter!!.notifyItemInserted(size)
                recyclerView!!.scrollToPosition(size)
                Notes.currentIndex = size
                showEditScreen(Notes.notes!![size])
                toSharedPref()
                return true
            }
            R.id.clear_point -> {
                Notes.notes!!.clear()
                adapter!!.notifyDataSetChanged()
                toSharedPref()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.notes_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter!!.menuPosition
        when (item.itemId) {
            R.id.delete_point -> {
                Notes.notes!!.removeAt(position)
                adapter!!.notifyItemRemoved(position)
                toSharedPref()
                return true
            }
            R.id.change_point -> {
                showEditScreen(Notes.notes!![position])
                toSharedPref()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    fun showEditScreen(notes: Notes?) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EditFragment.newInstance(notes))
            .addToBackStack("")
            .commit()
    }
}