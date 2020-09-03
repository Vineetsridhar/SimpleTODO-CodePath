package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    List<ListItem> items;

    public interface ListListener {
        void onItemLongClicked(int position);
        void onItemClicked(int position);
    }

    ListListener listListener;

    public ItemsAdapter(List<ListItem> items, ListListener onLongClickListener){
        this.items = items;
        this.listListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(ListItem item){
             tvItem.setText(item.getText());
             tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     listListener.onItemLongClicked(getAdapterPosition());
                     return true;
                 }
             });

             tvItem.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listListener.onItemClicked(getAdapterPosition());
                 }
             });
        }
    }
}
