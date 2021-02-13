package com.adamrubin.findsomefood.Room;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will organize all relevant information for the list of previous orders
 */
public class PreviousOrderListAdapter extends RecyclerView.Adapter<PreviousOrderListAdapter.PreviousOrderHolder> {
    //initialize the arraylist to avoid null state, never call later methods on null
    private Context context;
    private List<PreviousOrder> previousOrders = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(PreviousOrderListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public PreviousOrderListAdapter(Context context, List<PreviousOrder> previousOrders) {
        this.context = context;
        this.previousOrders = previousOrders;
    }

    @NonNull
    @Override
    //create
    public PreviousOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get context of viewgroup parent and inflate/attach
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_orders_list_item, parent,false);
        //return this properly tied note item
        return new PreviousOrderHolder(itemView, mListener);
    }

    @Override
    //place java objects into views of noteholder
    public void onBindViewHolder(@NonNull PreviousOrderHolder holder, int position) {
        //make sure we're on the current not using the position
        PreviousOrder currentPreviousOrder = previousOrders.get(position);
        //and slot each piece into its view using the getter methods
        holder.textViewName.setText(currentPreviousOrder.getRestaurantName());
        holder.textViewPrice.setText(currentPreviousOrder.getRestaurantPrice());
        holder.textViewAddress.setText(currentPreviousOrder.getRestaurantAddress());
        holder.textViewHours.setText(currentPreviousOrder.getRestaurantHours());
    }

    @Override
    public int getItemCount() {
        //create as many notes as there are cells in arraylist
        return previousOrders.size();
    }

    //method to place changes into
    public void setPreviousOrders(List<PreviousOrder> previousOrders) {
        this.previousOrders = previousOrders;
        notifyDataSetChanged();
        //above is a full method, but bad idea. there are individual options like
        //notifyItemChanged();
        //notifyItemInserted();
    }

    //hold views in each single recycler view item
    class PreviousOrderHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView textViewName;
        private TextView textViewPrice;
        private TextView textViewAddress;
        private TextView textViewHours;

        public PreviousOrderHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_previous_orders_list_name);
            textViewPrice = itemView.findViewById(R.id.text_view_previous_orders_list_price);
            textViewAddress = itemView.findViewById(R.id.text_view_previous_orders_list_address);
            textViewHours = itemView.findViewById(R.id.text_view_previous_orders_list_hours);

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
            contextMenu.add(this.getAdapterPosition(), 121, 0, "Remove!");
        }
    }

    public void removeItem(int position) {
        previousOrders.remove(position);
        notifyDataSetChanged();
    }
}
