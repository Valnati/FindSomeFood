package com.adamrubin.findsomefood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adamrubin.findsomefood.Data.Menu_Item;

public class ItemDetailsActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView description;
    Button confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_details);

        Intent intent = getIntent();
        Menu_Item menuItem = intent.getParcelableExtra("menuItem");
        Log.d("TAG", "Item name: " + menuItem.getName());
        Log.d("TAG", "Item price: " + menuItem.getPrice());
        Log.d("TAG", "Item description: " + menuItem.getDescription());

        name.setText(menuItem.getName());
        price.setText(menuItem.getPrice().toString());
        description.setText(menuItem.getDescription());

        confirm.setOnClickListener(view -> {
            Intent confirmIntent = new Intent(ItemDetailsActivity.this, MenuActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("orderedItem", menuItem);
            confirmIntent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
