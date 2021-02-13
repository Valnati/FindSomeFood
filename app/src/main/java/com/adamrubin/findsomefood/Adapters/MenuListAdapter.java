package com.adamrubin.findsomefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.R;

import java.util.ArrayList;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuListHolder> {
    private Context context;
    private ArrayList<Menu_Item> menuList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MenuListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MenuListAdapter(Context context, ArrayList<Menu_Item> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new MenuListHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListHolder holder, int position) {
        //this functionality is now in MenuActivity under SendMenuToAdapter
//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(context, ItemDetailsActivity.class);
//            //what should go to item detail??
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("menuList", (ArrayList<? extends Parcelable>) menuList);
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//        });

        //menu data is hiding below 3 levels of objects...
        holder.menuItemName.setText(menuList.get(position).getName());
        holder.menuItemPrice.setText(menuList.get(position).getPrice().toString());
        holder.menuItemDescription.setText(menuList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuListHolder extends RecyclerView.ViewHolder {
        TextView menuItemName;
        TextView menuItemDescription;
        TextView menuItemPrice;

        public MenuListHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            menuItemName = itemView.findViewById(R.id.menu_item_name);
            menuItemPrice = itemView.findViewById(R.id.menu_item_price);
            menuItemDescription = itemView.findViewById(R.id.menu_item_description);

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
    }
}
