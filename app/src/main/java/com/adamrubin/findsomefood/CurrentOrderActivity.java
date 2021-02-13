package com.adamrubin.findsomefood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Adapters.CurrentOrderAdapter;
import com.adamrubin.findsomefood.Data.Menu_Item;

import java.util.ArrayList;

/**
 * This class will show the final order and give options to call and get directions
 */
public class CurrentOrderActivity extends AppCompatActivity {


    private Button callButton;
    private Button driveButton;
    private TextView restaurantTextView;
    private String restaurantName;
    private String address;
    private String phone;

    ArrayList<Menu_Item> chosenItems;
    CurrentOrderAdapter currentOrderAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_current_order);
        recyclerView = findViewById(R.id.recycler_view_previous_orders_detail);

        Intent intent = getIntent();
        chosenItems = intent.getParcelableArrayListExtra("chosenItems");
        restaurantName = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");

        restaurantTextView = findViewById(R.id.text_view_previous_order_restaurant_name);
        restaurantTextView.setText(restaurantName);

        callButton = findViewById(R.id.button_previous_order_call);
        //button to transfer to calling app
        callButton.setOnClickListener( view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            //phone must not have hyphens
            String formattedPhone = phone.replaceAll("[( )-]", "");
            callIntent.setData(Uri.parse("tel:" + formattedPhone));
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            } else {
                Toast.makeText(this, "No phone app available", Toast.LENGTH_SHORT).show();
            }
        });
        //button to transfer to map app
        driveButton = findViewById(R.id.button_previous_order_drive);
        driveButton.setOnClickListener( view -> {
            Intent driveIntent = new Intent(Intent.ACTION_VIEW);
            driveIntent.setData(Uri.parse("geo:0,0?q=" + address));
            if (driveIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(driveIntent);
            } else {
                Toast.makeText(this, "No map app available", Toast.LENGTH_SHORT).show();
            }
        });

        sendItemsToAdapter(chosenItems);
    }

    private void sendItemsToAdapter(ArrayList<Menu_Item> chosenItems) {
        currentOrderAdapter = new CurrentOrderAdapter(this, chosenItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(recyclerView);
        recyclerView.setAdapter(currentOrderAdapter);
    }

    @Override
    public void finish() {
        Intent workingOrderIntent = new Intent();
        ArrayList<Menu_Item> workingOrder = currentOrderAdapter.getMenuList();
        workingOrderIntent.putParcelableArrayListExtra("workingOrder", workingOrder);
        setResult(200, workingOrderIntent);
        super.finish();
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId())
        {
            case 121:
                currentOrderAdapter.copyItem(menuItem.getGroupId());
                displayMessage(0);
                return true;
            case 122:
                currentOrderAdapter.removeItem(menuItem.getGroupId());
                displayMessage(1);
                return true;
            default:
                return super.onContextItemSelected(menuItem);
        }
    }

    private void displayMessage(int type) {
        switch (type) {
            case 0: Toast.makeText(this, "Another for you!", Toast.LENGTH_SHORT).show();
            case 1: Toast.makeText(this, "Order modified", Toast.LENGTH_SHORT).show();
        }
    }
}
