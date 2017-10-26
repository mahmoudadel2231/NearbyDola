package com.example.moham.nearby.DataModels;


import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaceModel implements Serializable {
    public photos[] photos;
    String name;
    boolean open_now;
    float rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public class photos implements Serializable {

        public String photo_reference;

        public String getPhoto_reference() {
            return photo_reference;
        }

    }
}
