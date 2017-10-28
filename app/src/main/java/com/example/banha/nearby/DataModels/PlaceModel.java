package com.example.banha.nearby.DataModels;


import java.io.Serializable;

public class PlaceModel implements Serializable {
    public photos[] photos;
    String name;
    boolean open_now;
    float rating;
    String vicinity;

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

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
