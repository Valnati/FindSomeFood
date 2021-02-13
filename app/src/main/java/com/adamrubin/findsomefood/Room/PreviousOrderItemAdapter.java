package com.adamrubin.findsomefood.Room;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Adapters.CurrentOrderAdapter;
import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.R;

import java.util.List;

/**
 * This class will take all relevant information for
 * the items that constitute a previous individual order
 */
public class PreviousOrderItemAdapter extends RecyclerView.Adapter<PreviousOrderItemAdapter.PreviousOrderItemHolder> {
    private Context context;
    private List<Menu_Item> previousOrderItems;

    public PreviousOrderItemAdapter(Context context, List<Menu_Item> previousOrderItems) {
        this.context = context;
        this.previousOrderItems = previousOrderItems;
    }

    @NonNull
    @Override
    public PreviousOrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_orders_item, parent, false);
        return new PreviousOrderItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousOrderItemAdapter.PreviousOrderItemHolder holder, int position) {
        holder.itemName.setText(previousOrderItems.get(position).getName());
        holder.itemPrice.setText(previousOrderItems.get(position).getPrice().toString());
        holder.itemDescription.setText(previousOrderItems.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return previousOrderItems.size();
    }

    public class PreviousOrderItemHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemPrice;
        TextView itemDescription;

        public PreviousOrderItemHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.text_view_previous_order_details_name);
            itemPrice = itemView.findViewById(R.id.text_view_previous_order_details_price);
            itemDescription = itemView.findViewById(R.id.text_view_previous_order_details_description);
        }
    }
}
