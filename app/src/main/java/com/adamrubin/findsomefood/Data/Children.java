package com.adamrubin.findsomefood.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Children implements Parcelable {
    @SerializedName("restaurant_name")
    @Expose
    private String restaurant_name;

    @SerializedName("restaurant_phone")
    @Expose
    private String restaurant_phone;

    @SerializedName("restaurant_website")
    @Expose
    private String restaurant_website;

    @SerializedName("hours")
    @Expose
    private String restaurant_hours;

    @SerializedName("price_range")
    @Expose
    private String price_range;

    @SerializedName("cuisines")
    @Expose
    private ArrayList<String> cuisines;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("menus")
    @Expose
    private ArrayList<Menu> menu;

    protected Children(Parcel in) {
        restaurant_name = in.readString();
        restaurant_phone = in.readString();
        restaurant_website = in.readString();
        restaurant_hours = in.readString();
        price_range = in.readString();
        cuisines = in.createStringArrayList();
        menu = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<Children> CREATOR = new Creator<Children>() {
        @Override
        public Children createFromParcel(Parcel in) {
            return new Children(in);
        }

        @Override
        public Children[] newArray(int size) {
            return new Children[size];
        }
    };

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public String getRestaurant_phone() {
        return restaurant_phone;
    }

    public String getRestaurant_website() {
        return restaurant_website;
    }

    public String getRestaurant_hours() {
        return restaurant_hours;
    }

    public String getPrice_range() {
        return price_range;
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Children{" +
                "restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_phone='" + restaurant_phone + '\'' +
                ", restaurant_website='" + restaurant_website + '\'' +
                ", restaurant_hours='" + restaurant_hours + '\'' +
                ", price_range='" + price_range + '\'' +
                ", cuisines=" + cuisines +
                ", address=" + address +
                ", menu=" + menu +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(restaurant_name);
        parcel.writeString(restaurant_phone);
        parcel.writeString(restaurant_website);
        parcel.writeString(restaurant_hours);
        parcel.writeString(price_range);
        parcel.writeStringList(cuisines);
        parcel.writeTypedList(menu);
    }
}
