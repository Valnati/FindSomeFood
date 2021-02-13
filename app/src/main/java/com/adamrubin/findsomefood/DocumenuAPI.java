package com.adamrubin.findsomefood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DocumenuAPI {
    @GET("restaurants/search/geo")
    Call<Documenu> getItems(
            @Query("key") String key,
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("distance") Integer distance,
            @Query("size") Integer size,
            @Query("fullmenu") Boolean fullmenu
    );

    @GET("restaurants/search/geo")
    Call<Documenu> getItemsWithCuisine(
            @Query("key") String key,
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("distance") Integer distance,
            @Query("size") Integer size,
            @Query("fullmenu") Boolean fullmenu,
            @Query("cuisine") String cuisine

    );
        //call getItems with variables, and if var is null retrofit will deal
}
