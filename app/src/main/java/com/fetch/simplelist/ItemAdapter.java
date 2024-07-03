package com.fetch.simplelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.id.setText("ID: " + item.getId());
        holder.listId.setText("List ID: " + item.getListId());
        holder.name.setText("Name: " + item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView listId;
        TextView name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_id);
            listId = itemView.findViewById(R.id.item_list_id);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
