package com.example.notesholder.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesholder.Notes;
import com.example.notesholder.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Notes> dataSource;
   // List<Notes> dataSource;
    private OnItemClickListener itemClickListener;
    private final Fragment fragment;
    private int menuPosition;

    public NotesAdapter(ArrayList<Notes> dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    public Notes getData(int i) {
        return dataSource.get(i);
    }

    public void setNewData(ArrayList<Notes> dataSet) {
        this.dataSource = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.bind(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            card = itemView.findViewById(R.id.note_card);
            registerContextMenu(itemView);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {
                    itemView.showContextMenu(50, 50);
                    menuPosition = getLayoutPosition();
                    return true;
                }
            });

        }

        public void bind(Notes cardData) {
            name.setText(cardData.name);
            date.setText(cardData.date);
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public int getMenuPosition() {
        return menuPosition;
    }
}
