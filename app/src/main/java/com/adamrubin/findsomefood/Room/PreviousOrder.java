package com.adamrubin.findsomefood.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.adamrubin.findsomefood.Data.Menu_Item;

import java.util.ArrayList;

@Entity(tableName = "PreviousOrder")
public class PreviousOrder {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String restaurantName;
    private String restaurantPrice;
    private String restaurantAddress;
    private String restaurantHours;
    private String restaurantWebsite;
    private String restaurantPhone;
    //prob easiest way to store item name/price?
    private ArrayList<Menu_Item> orders;
    //@ColumnInfo(name = newNameForColumn)

    //construct each object, except primarykey to regenerate


    public PreviousOrder(int id, String restaurantName, String restaurantPrice,
                         String restaurantAddress,
                         String restaurantHours, String restaurantWebsite,
                         String restaurantPhone, ArrayList<Menu_Item> orders) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantPrice = restaurantPrice;
        this.restaurantAddress = restaurantAddress;
        this.restaurantHours = restaurantHours;
        this.restaurantWebsite = restaurantWebsite;
        this.restaurantPhone = restaurantPhone;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPrice() {
        return restaurantPrice;
    }

    public void setRestaurantPrice(String restaurantPrice) {
        this.restaurantPrice = restaurantPrice;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantHours() {
        return restaurantHours;
    }

    public void setRestaurantHours(String restaurantHours) {
        this.restaurantHours = restaurantHours;
    }

    public String getRestaurantWebsite() {
        return restaurantWebsite;
    }

    public void setRestaurantWebsite(String restaurantWebsite) {
        this.restaurantWebsite = restaurantWebsite;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public ArrayList<Menu_Item> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Menu_Item> orders) {
        this.orders = orders;
    }
}