package com.adamrubin.findsomefood.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("formatted")
    @Expose
    private String formatted_address;

    public String getStreet() {
        return street;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", formatted_address='" + formatted_address + '\'' +
                '}';
    }
}
