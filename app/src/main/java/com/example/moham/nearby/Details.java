package com.example.moham.nearby;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moham.nearby.DataModels.PlaceModel;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    TextView resturantname;
    TextView category;
    TextView openhours;
    RatingBar ratingBar;
    ImageView image;
    TextView rate;
    TextView editcategory;
    TextView edithours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        PlaceModel placeModel = (PlaceModel) getIntent().getSerializableExtra("PlaceModel");
        // initi
        editcategory = (TextView) findViewById(R.id.category);
        edithours = (TextView) findViewById(R.id.openhours);
        resturantname = (TextView) findViewById(R.id.name);
        category = (TextView) findViewById(R.id.category1);
        rate = (TextView) findViewById(R.id.rate);
        openhours = (TextView) findViewById(R.id.openinghours);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        image = (ImageView) findViewById(R.id.imageView4);

        Typeface mycustom = Typeface.createFromAsset(getAssets(), "fonts/b.otf");


        //  SET Font //
        resturantname.setTypeface(mycustom);
        category.setTypeface(mycustom);
        openhours.setTypeface(mycustom);
        rate.setTypeface(mycustom);

        //  edited text
        resturantname.setText(placeModel.getName());
        // category.setText(placeModel.getRelease_date());
        //  openhours.setText(placeModel.ge());
        ratingBar.setMax(5);
        ratingBar.setRating(placeModel.getRating());
        ratingBar.setIsIndicator(true);

        Picasso.with(this).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=700&photoreference=" +
                placeModel.photos[0].getPhoto_reference() + "&key=AIzaSyD8aYBQLmxk1qsY7wkojwiH_wZeCBI6QKA").fit().into(image);
    }
}
