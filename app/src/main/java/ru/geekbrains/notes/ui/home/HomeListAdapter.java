package ru.geekbrains.notes.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.LinkedList;

import ru.geekbrains.notes.Notes;
import ru.geekbrains.notes.R;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private final LinkedList<LinkedList<String>> dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;

    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public HomeListAdapter(Notes notes, Fragment fragment) {
        this.dataSource = notes.getAllNotes();
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.get(i).get(1));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            registerContextMenu(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            textView.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                return false;
            });

        }
        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(String title) {
            textView.setText(title);
        }
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
