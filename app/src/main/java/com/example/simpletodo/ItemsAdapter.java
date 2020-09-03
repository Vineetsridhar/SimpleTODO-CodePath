package com.example.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    List<ListItem> items;
    ListListener listListener;

    public interface ListListener {
        void onItemLongClicked(int position);
        void onItemClicked(int position);
    }


    public ItemsAdapter(List<ListItem> items, ListListener onLongClickListener){
        this.items = items;
        this.listListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
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
        TextView tvText;
        TextView tvPriority;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvText = itemView.findViewById(R.id.text);
            tvPriority = itemView.findViewById(R.id.priority);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(ListItem item){
            tvText.setText(item.getText());
            tvPriority.setText(item.getPrio());
            container.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     listListener.onItemLongClicked(getAdapterPosition());
                     return true;
                 }
             });

            container.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listListener.onItemClicked(getAdapterPosition());
                 }
             });
        }
    }
}
