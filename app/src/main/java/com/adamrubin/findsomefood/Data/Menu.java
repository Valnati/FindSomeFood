package com.adamrubin.findsomefood.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Menu implements Parcelable {
    @SerializedName("menu_name")
    @Expose
    private String menu_name;

    @SerializedName("menu_sections")
    @Expose
    private ArrayList<Menu_Section> menu_sections;

    public Menu(String menu_name, ArrayList<Menu_Section> menu_sections) {
        this.menu_name = menu_name;
        this.menu_sections = menu_sections;
    }

    protected Menu(Parcel in) {
//        this();
//        menu_sections = new ArrayList<Menu_Section>();
        menu_name = in.readString();
        menu_sections = in.createTypedArrayList(Menu_Section.CREATOR);
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getMenu_name() {
        return menu_name;
    }

    public ArrayList<Menu_Section> getMenu_sections() {
        return menu_sections;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu_name='" + menu_name + '\'' +
                ", menu_sections=" + menu_sections +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(menu_name);
        parcel.writeTypedList(menu_sections);
    }
}
