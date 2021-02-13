package com.adamrubin.findsomefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Data.Children;
import com.adamrubin.findsomefood.R;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>{
    private Context context;
    private List<Children> restaurantList;
    private String searchType;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RestaurantAdapter(Context context, List<Children> restaurantList, String searchType) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.searchType = searchType;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);

        return new RestaurantHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        String price = restaurantList.get(position).getPrice_range();
        //filter out restaurants that don't match the search
        if (searchType.contentEquals("cheap")) {
            if (price.contentEquals("$$$") || price.contentEquals("$$$$")) return;
        }

        if (searchType.contentEquals("fancy")) {
            if (price.contentEquals("$") || price.contentEquals("$$")) return;
        }

        //place relevant data into each card of the recyclerview
        holder.restaurantName.setText(restaurantList.get(position).getRestaurant_name());
        holder.restaurantPrice.setText(price);
        String cuisines = restaurantList.get(position).getCuisines().toString();
        String almostFormattedCuisines = cuisines.replace("]", "");
        String formattedCuisines = almostFormattedCuisines.replace("[", "");
        if (formattedCuisines.contentEquals("[]")) formattedCuisines = "";
        holder.restaurantCuisine.setText(formattedCuisines);
        holder.restaurantAddress.setText(restaurantList.get(position).getAddress().getFormatted_address());
        holder.restaurantHours.setText(restaurantList.get(position).getRestaurant_hours());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class RestaurantHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        TextView restaurantPrice;
        TextView restaurantCuisine;
        TextView restaurantAddress;
        TextView restaurantHours;

        public RestaurantHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.restaurant_item_name);
            restaurantPrice = itemView.findViewById(R.id.restaurant_item_price_range);
            restaurantCuisine = itemView.findViewById(R.id.restaurant_item_cuisine);
            restaurantAddress = itemView.findViewById(R.id.restaurant_item_address);
            restaurantHours = itemView.findViewById(R.id.restaurant_item_hours);

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
