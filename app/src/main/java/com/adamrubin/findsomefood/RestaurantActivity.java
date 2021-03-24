package com.adamrubin.findsomefood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamrubin.findsomefood.Adapters.RestaurantAdapter;
import com.adamrubin.findsomefood.Data.Children;
import com.adamrubin.findsomefood.Data.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is the list of restaurants that can be chosen from
 * to make an order
 */
public class RestaurantActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.documenu.com/v2/";
    private final String API_KEY = getString(R.string.API_KEY);

    private double latitude;
    private double longitude;
    private String searchType;
    private String cuisines;

    RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;

    APIRepository apiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        recyclerView = findViewById(R.id.restaurant_recycler_view);

        apiRepository = new APIRepository();

        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        searchType = bundle.getString("searchType");
        cuisines = bundle.getString("cuisines");

//        childrenList = apiRepository.callAPI(latitude, longitude, searchType, cuisines);
//        PassToRecyclerView(childrenList);


        callAPI(latitude, longitude, cuisines);
    }

    private void callAPI(double latitude, double longitude, String cuisines) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DocumenuAPI documenuAPI = retrofit.create(DocumenuAPI.class);
        Call<Documenu> call;
        if (searchType.contentEquals("specific")) {
            call = documenuAPI.getItemsWithCuisine(
                    API_KEY,
                    latitude,
                    longitude,
                    15,
                    25,
                    true,
                    cuisines);
        } else {
            call = documenuAPI.getItems(
                    API_KEY,
                    latitude,
                    longitude,
                    15,
                    25,
                    true);
        }

        call.enqueue(new Callback<Documenu>() {
            @Override
            public void onResponse(Call<Documenu> call, Response<Documenu> response) {
                Log.d("TAG", "onResponse: Server Response: " + response.toString());
                Log.d("TAG", "onResponse: received information: " + response.body().toString());

                //get all children from json data, including kind String, and data {} object
                Documenu documenu = response.body();
                ArrayList<Children> childrenList = documenu.getData();
                PassToRecyclerView(childrenList);
            }

            @Override
            public void onFailure(Call<Documenu> call, Throwable t) {
                Log.e("Tag", "onFailure: Something went wrong: " + t.getLocalizedMessage());
            }
        });
    }

    private void PassToRecyclerView(List<Children> restaurantList) {
        restaurantAdapter = new RestaurantAdapter(this, restaurantList, searchType);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(restaurantAdapter);

        restaurantAdapter.setOnItemClickListener((RestaurantAdapter.OnItemClickListener) position -> {
            Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
            Bundle bundle = new Bundle();

            ArrayList<Menu> menu = restaurantList.get(position).getMenu();

            bundle.putParcelableArrayList("menu", (ArrayList<? extends Parcelable>) menu);
            bundle.putString("name", restaurantList.get(position).getRestaurant_name());
            bundle.putString("phone", restaurantList.get(position).getRestaurant_phone());
            bundle.putString("address", restaurantList.get(position).getAddress().getFormatted_address());
            bundle.putString("website", restaurantList.get(position).getRestaurant_website());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
