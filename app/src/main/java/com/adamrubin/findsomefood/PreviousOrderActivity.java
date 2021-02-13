package com.adamrubin.findsomefood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.Room.PreviousOrderItemAdapter;

import java.util.List;

/**
 * This class is the detailed list of each item that constituted
 * a previous order
 */
public class PreviousOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PreviousOrderItemAdapter previousOrderItemAdapter;
    private List<Menu_Item> menu_items;

    private Button callButton;
    private Button driveButton;
    private TextView nameTextView;

    private String phone;
    private String restaurantName;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);

        recyclerView = findViewById(R.id.recycler_view_previous_orders_detail);
        nameTextView = findViewById(R.id.text_view_previous_order_details_name);

        //get intent from previousOrdersListActivity
        Intent intent = getIntent();
        menu_items = intent.getParcelableArrayListExtra("menuItems");
        restaurantName = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        //pass
        PassToRecyclerView(menu_items);

        nameTextView.setText(restaurantName);

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

    }

    private void PassToRecyclerView(List<Menu_Item> chosenItems) {
        previousOrderItemAdapter = new PreviousOrderItemAdapter(this, chosenItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(previousOrderItemAdapter);
    }
}
