package com.adamrubin.findsomefood.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.R;

import java.util.ArrayList;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.CurrentOrderHolder> {
    private Context context;
    private ArrayList<Menu_Item> menuList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CurrentOrderAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public CurrentOrderAdapter(Context context, ArrayList<Menu_Item> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public CurrentOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_orders_item, parent, false);
        return new CurrentOrderHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentOrderHolder holder, int position) {
        holder.menuItemName.setText(menuList.get(position).getName());
        holder.menuItemPrice.setText(menuList.get(position).getPrice().toString());
        holder.menuItemDescription.setText(menuList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class CurrentOrderHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView menuItemName;
        TextView menuItemDescription;
        TextView menuItemPrice;
        CardView cardView;

        public CurrentOrderHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            menuItemName = itemView.findViewById(R.id.text_view_orders_name);
            menuItemDescription = itemView.findViewById(R.id.text_view_orders_description);
            menuItemPrice = itemView.findViewById(R.id.text_view_orders_price);
            cardView = itemView.findViewById(R.id.card_view_current_orders);
            cardView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 121, 0, "Another!");
            contextMenu.add(this.getAdapterPosition(), 122, 1, "I don't want this...");
        }
    }

    public void removeItem(int position) {
        menuList.remove(position);
        notifyDataSetChanged();
    }

    public void copyItem(int position) {
        Menu_Item itemCopy = menuList.get(position);
        menuList.add(itemCopy);
        notifyDataSetChanged();
    }

    public ArrayList<Menu_Item> getMenuList() {
        return menuList;
    }
}
