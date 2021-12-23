package com.example.notesholder.ui

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.notesholder.R
import android.widget.TextView
import androidx.cardview.widget.CardView
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.notesholder.Notes
import java.text.SimpleDateFormat
import java.util.ArrayList

class NotesAdapter(var dataSource: ArrayList<Notes>, private val fragment: Fragment?) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null
    var menuPosition = 0
        private set

    fun setNewData(dataSource: ArrayList<Notes>) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note, parent, false)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ViewHolder(view)
        } else {
            TODO("VERSION.SDK_INT < N")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun getData(i: Int): Notes {
        return dataSource[i]
    }

    @RequiresApi(Build.VERSION_CODES.N)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var date: TextView
        var card: CardView
        fun bind(cardData: Notes) {
            name.text = cardData.name
            date.text = SimpleDateFormat("dd-MMM-yyyy").format(cardData.date)
        }

        private fun registerContextMenu(itemView: View) {
            if (fragment != null) {
                itemView.setOnLongClickListener {
                    menuPosition = layoutPosition
                    false
                }
                fragment.registerForContextMenu(itemView)
            }
        }

        init {
            name = itemView.findViewById(R.id.tv_name)
            date = itemView.findViewById(R.id.tv_date)
            card = itemView.findViewById(R.id.note_card)
            registerContextMenu(itemView)
            card.setOnClickListener { view ->
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(view, adapterPosition)
                }
            }
            card.setOnLongClickListener {
                itemView.showContextMenu(50f, 50f)
                menuPosition = layoutPosition
                true
            }
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }
}