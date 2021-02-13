package com.adamrubin.findsomefood;

import android.util.Log;

import com.adamrubin.findsomefood.Data.Children;
import com.adamrubin.findsomefood.Data.Menu;
import com.adamrubin.findsomefood.Data.Menu_Item;
import com.adamrubin.findsomefood.Data.Menu_Section;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepository {

    private static final String BASE_URL = "https://api.documenu.com/v2/";
    private static final String API_KEY = "b411218966833bf33f43dc1c2eb77e51";

    ArrayList<Children> childrenList;

    public APIRepository() {

    }

    public ArrayList<Children> callAPI(double latitude, double longitude, String searchType, String cuisines) {
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
                //currently getting NO data to list, even though it is there...
                Documenu documenu = response.body();
                childrenList = documenu.getData();
            }

            @Override
            public void onFailure(Call<Documenu> call, Throwable t) {
                Log.e("Tag", "onFailure: Something went wrong: " + t.getLocalizedMessage());
            }
        });
        return childrenList;
    }
}
