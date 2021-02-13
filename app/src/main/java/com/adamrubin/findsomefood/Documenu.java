package com.adamrubin.findsomefood;

import com.adamrubin.findsomefood.Data.Children;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Documenu {
    @SerializedName("data")
    @Expose
    private ArrayList<Children> data;

    public ArrayList<Children> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Documenu{" +
                "data=" + data +
                '}';
    }
}
