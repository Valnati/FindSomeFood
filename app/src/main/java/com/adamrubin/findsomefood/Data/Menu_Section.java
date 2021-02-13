package com.adamrubin.findsomefood.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Menu_Section implements Parcelable {
    @SerializedName("section_name")
    @Expose
    private String section_name;

    @SerializedName("menu_items")
    @Expose
    private ArrayList<Menu_Item> menu_items;

    protected Menu_Section(Parcel in) {
        section_name = in.readString();
        menu_items = in.createTypedArrayList(Menu_Item.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(section_name);
        dest.writeTypedList(menu_items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu_Section> CREATOR = new Creator<Menu_Section>() {
        @Override
        public Menu_Section createFromParcel(Parcel in) {
            return new Menu_Section(in);
        }

        @Override
        public Menu_Section[] newArray(int size) {
            return new Menu_Section[size];
        }
    };

    public String getSection_name() {
        return section_name;
    }

    public ArrayList<Menu_Item> getMenu_items() {
        return menu_items;
    }

    @Override
    public String toString() {
        return "Menu_Sections{" +
                "section_name='" + section_name + '\'' +
                ", menu_items=" + menu_items +
                '}';
    }
}
