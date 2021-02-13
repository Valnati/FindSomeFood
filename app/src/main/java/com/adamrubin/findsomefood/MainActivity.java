package com.adamrubin.findsomefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the homepage and allows for the first search term
 */
public class MainActivity extends AppCompatActivity {
    //data for search variables
    private Geocoder geocoder;
    private double latitude;
    private double longitude;
    private String searchType;
    private String cuisines;

    //ui variables
    private Button cheapButton;
    private Button fancyButton;
    private Button specificButton;
    private Button closestButton;
    private Button recordsButton;

    //address settings variables
    private AlertDialog.Builder addressDialogBuilder;
    private AlertDialog addressDialog;
    private EditText popup_address;
    private Button popup_yesButton;
    private Button popup_noButton;

    //cuisine settings variables
    private AlertDialog.Builder cuisineDialogBuilder;
    private AlertDialog cuisineDialog;
    private String[] cuisineItems;
    private boolean[] chosenItems;
    private ArrayList<Integer> mChosenItems = new ArrayList<>();

    //address preferences variables
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ADDRESS_TEXT = "text";
    private String addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cheapButton = findViewById(R.id.button_cheap);
        fancyButton = findViewById(R.id.button_fancy);
        specificButton = findViewById(R.id.button_specific);
        closestButton = findViewById(R.id.button_near);
        recordsButton = findViewById(R.id.button_records);

        cuisineItems = getResources().getStringArray(R.array.cuisines_list);
        chosenItems = new boolean[cuisineItems .length];

        cheapButton.setOnClickListener(view -> {
            searchType = "cheap";
            getAddress();
            Intent intent = new Intent(this, RestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString("searchType", searchType);
            bundle.putString("cuisines", cuisines);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        fancyButton.setOnClickListener(view -> {
            searchType = "fancy";
            cuisines = "";
            getAddress();
            Intent intent = new Intent(this, RestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString("searchType", searchType);
            bundle.putString("cuisines", cuisines);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //specific cuisines intent pauses until items are chosen
        //setCuisineChoices -> runSpecificIntent
        specificButton.setOnClickListener(view -> {
            setCuisineChoices();
            searchType = "specific";
            getAddress();
        });

        closestButton.setOnClickListener(view -> {
            searchType = "closest";
            cuisines = "";
            getAddress();
            Intent intent = new Intent(this, RestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString("searchType", searchType);
            bundle.putString("cuisines", cuisines);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        recordsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PreviousOrderListActivity.class);
            startActivity(intent);
        });

        loadData();
        //updateViews();
    }

    private void setCuisineChoices() {
        cuisineDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        cuisineDialogBuilder.setTitle(getString(R.string.specific_dialogue_title));
        cuisineDialogBuilder.setMultiChoiceItems(cuisineItems, chosenItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                        mChosenItems.add(position);
                    } else {
                        mChosenItems.remove(Integer.valueOf(position));
                    }
            }
        });

        cuisineDialogBuilder.setCancelable(false);
        cuisineDialogBuilder.setPositiveButton(R.string.specific_button_go, (dialogInterface, which) -> {
            String item = "";
            for (int i= 0; i < mChosenItems.size(); i++) {
                item = item + cuisineItems[mChosenItems.get(i)];
                if (i !=mChosenItems.size() - 1) item = item + ", ";
            }
            cuisines = item;
            useSpecificIntent();
        });

        cuisineDialogBuilder.setNegativeButton(R.string.specific_button_cancel, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        cuisineDialogBuilder.setNeutralButton(R.string.specific_button_clear, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < chosenItems.length; i++) {
                    chosenItems[i] = false;
                    mChosenItems.clear();
                }
            }
        });

        //is this necessary to show the options?
        cuisineDialog = cuisineDialogBuilder.create();
        cuisineDialog.show();
    }

    public void useSpecificIntent() {
        Intent intent = new Intent(this, RestaurantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("searchType", searchType);
        bundle.putString("cuisines", cuisines);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.item_address) {
            createNewItemDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {

        super.onResume();
    }

    private void getAddress() {
        /**
         *  GET LAT/LONG FROM A GIVEN ADDRESS
         */
        geocoder = new Geocoder(this);

        try {
            //will need to grab var from settings to use here
            List<Address> addresses = geocoder.getFromLocationName(addressText, 1);
            Address address = addresses.get(0);
            Log.d("TAG", "address lat/long: " + address.toString());
            latitude = address.getLatitude();
            longitude = address.getLongitude();
            Log.d("TAG", "lat: " + latitude);
            Log.d("TAG", "long: " + longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // END LAT/LON
    }

    //popup dialog address settings behavior
    public void createNewItemDialog() {
        addressDialogBuilder = new AlertDialog.Builder(this);
        final View confirmPopupView = getLayoutInflater().inflate(R.layout.change_address_popup, null);
        popup_yesButton = (Button) confirmPopupView.findViewById(R.id.button_item_confirm);
        popup_noButton = (Button) confirmPopupView.findViewById(R.id.button_item_cancel);
        popup_address = (EditText) confirmPopupView.findViewById(R.id.edit_text_address);

        popup_address.setText(addressText);

        addressDialogBuilder.setView(confirmPopupView);
        addressDialog = addressDialogBuilder.create();
        addressDialog.show();

        popup_yesButton.setOnClickListener(view -> {
            saveData();
            addressDialog.dismiss();
        });

        popup_noButton.setOnClickListener(view -> {
            addressDialog.dismiss();
        });
    }

    public void saveData() {
        if (popup_address.getText().toString().contentEquals("")) {
            Toast.makeText(this, "Please put an address", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ADDRESS_TEXT, popup_address.getText().toString());

        editor.apply();
        loadData();
        Toast.makeText(this, "Address saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        addressText = sharedPreferences.getString(ADDRESS_TEXT, "");
    }

    public void updateViews() {
        popup_address.setText(addressText);
    }
}