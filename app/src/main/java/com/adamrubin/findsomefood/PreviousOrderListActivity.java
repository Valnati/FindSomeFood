package com.adamrubin.findsomefood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Adapters.RestaurantAdapter;
import com.adamrubin.findsomefood.Data.Menu;
import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.Room.PreviousOrder;
import com.adamrubin.findsomefood.Room.PreviousOrderListAdapter;
import com.adamrubin.findsomefood.Room.PreviousOrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the list of orders previously made
 */
public class PreviousOrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PreviousOrderListAdapter previousOrderListAdapter;

    private PreviousOrderViewModel orderDatabase;

    private LiveData<List<PreviousOrder>> roomData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_orders_list_item);

//        orderDatabase = ViewModelProvider.AndroidViewModelFactory(PreviousOrderViewModel, ViewModelProvider.Factory);
//
//
//        recyclerView = findViewById(R.id.recycler_view_previous_orders_list);
//
//        //TODO: get roomData of PreviousOrders
//        //previousOrders = getRoomDataSomehow();
//
//        roomData = orderDatabase.getAllOrders();
//
//        PassToRecyclerView(roomData);

        //in intent send menuItems across
    }

//    private void PassToRecyclerView(LiveData<List<PreviousOrder>> previousOrder) {
//        //TODO: add listener to arguments
//        previousOrderListAdapter = new PreviousOrderListAdapter(this, previousOrder);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        registerForContextMenu(recyclerView);
//        recyclerView.setAdapter(previousOrderListAdapter);
//
//        previousOrderListAdapter.setOnItemClickListener(new PreviousOrderListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(PreviousOrderListActivity.this, PreviousOrderActivity.class);
//                Bundle bundle = new Bundle();
//
//                ArrayList<Menu_Item> chosenItems = previousOrder.get(position).getOrders();
//
//                bundle.putParcelableArrayList("chosenItems", (ArrayList<? extends Parcelable>) chosenItems);
//                bundle.putString("name", previousOrder.get(position).getRestaurantName());
//                bundle.putString("phone", previousOrder.get(position).getRestaurantPhone());
//                bundle.putString("address", previousOrder.get(position).getRestaurantAddress());
//                bundle.putString("website", previousOrder.get(position).getRestaurantWebsite());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem menuItem) {
//
//        switch(menuItem.getItemId())
//        {
//            case 121:
//                previousOrderListAdapter.removeItem(menuItem.getGroupId());
//                menuItem.
//                displayMessage(0);
//                return true;
//            default:
//                return super.onContextItemSelected(menuItem);
//        }
//    }
//
//    private void displayMessage(int type) {
//        switch (type) {
//            case 0: Toast.makeText(this, "Order removed", Toast.LENGTH_SHORT).show();
//        }
//    }
}
