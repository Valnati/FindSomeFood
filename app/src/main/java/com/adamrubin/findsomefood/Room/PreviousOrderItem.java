package com.adamrubin.findsomefood.Room;

import android.os.Parcel;
import android.os.Parcelable;

import com.adamrubin.findsomefood.Data.Menu_Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviousOrderItem implements Parcelable {
    private String name;
    private String description;
    private Double price;

    protected PreviousOrderItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreviousOrderItem> CREATOR = new Creator<PreviousOrderItem>() {
        @Override
        public PreviousOrderItem createFromParcel(Parcel in) {
            return new PreviousOrderItem(in);
        }

        @Override
        public PreviousOrderItem[] newArray(int size) {
            return new PreviousOrderItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Menu_Items{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}