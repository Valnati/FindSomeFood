package com.adamrubin.findsomefood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Adapters.MenuListAdapter;
import com.adamrubin.findsomefood.Data.Menu;
import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.Data.Menu_Section;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * This class shows the individual restaurant's items
 */
public class MenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 400;
    private static final int RESULT_OK = 200;
    RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton confirmButton;
    private String name;
    private String phone;
    private String address;
    private String website;
    private TextView websiteView;

    ArrayList<Menu> menuList;
    ArrayList<Menu_Item> formattedMenuItems;
    ArrayList<Menu_Item> chosenItems = new ArrayList<>();

    private MenuListAdapter menuListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //find all relevant views
        setContentView(R.layout.activity_menu);
        coordinatorLayout = findViewById(R.id.menu_coordinator_layout);
        recyclerView = findViewById(R.id.menu_recycler_view);
        websiteView = findViewById(R.id.menu_website);
        confirmButton = findViewById(R.id.button_move_to_final_order);

        //get menu data from bundle
        Intent intent = getIntent();

        menuList = intent.getParcelableArrayListExtra("menu");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        website = intent.getStringExtra("website");

        //attach website
        websiteView.setOnClickListener(view -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(website));
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            } else {
                Toast.makeText(this, "No browser app available", Toast.LENGTH_SHORT).show();
            }
            });

        //format that menu data to get individual items
        formattedMenuItems = getMenuData(menuList);
        //and send it to adapter
        SendMenuToAdapter(formattedMenuItems);

        //behavior upon confirmation of items, to next activity
        confirmButton.setOnClickListener(view -> {
            Intent confirmIntent = new Intent(MenuActivity.this, CurrentOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("chosenItems", chosenItems);
            bundle.putString("name", name);
            bundle.putString("address", address);
            bundle.putString("website", website);
            bundle.putString("phone", phone);
            confirmIntent.putExtras(bundle);
            startActivityForResult(confirmIntent, REQUEST_CODE);
        });
    }

    //if coming from the currentOrderActivity, this will bring along any changes made there
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if ( data.hasExtra("workingOrder")) {
                chosenItems = data.getParcelableArrayListExtra("workingOrder");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SendMenuToAdapter(ArrayList<Menu_Item> menu_items) {
        menuListAdapter = new MenuListAdapter(this, menu_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(menuListAdapter);

        menuListAdapter.setOnItemClickListener(position -> {
            Menu_Item newItem = menu_items.get(position);
            chosenItems.add(newItem);
            Snackbar.make(coordinatorLayout, R.string.item_added, Snackbar.LENGTH_SHORT).show();
        });
    }

    //assume we have the menu data in this activity,
    //from the bundle that RestaurantActivity sent
    private ArrayList<Menu_Item> getMenuData(ArrayList<Menu> menuList) {
        ArrayList<Menu_Item> menuItems = new ArrayList<>();
        for (Menu currentMenu: menuList) {
            ArrayList<Menu_Section> menuSections = currentMenu.getMenu_sections();
            for (Menu_Section currentSection: menuSections) {
                menuItems.addAll(currentSection.getMenu_items());
            }
        }
        return menuItems;
    }
}
