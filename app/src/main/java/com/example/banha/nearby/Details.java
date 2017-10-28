package com.example.banha.nearby;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.banha.nearby.DataModels.PlaceModel;
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
    ImageView imageViewback;
    ImageView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        final Double lang = getIntent().getDoubleExtra("langitude", 0.0);
        final Double lat = getIntent().getDoubleExtra("latitude", 0.0);

//Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout2);
        imageViewback = (ImageView) findViewById(R.id.imageViewback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Details.this, Search.class);
                finish();
                startActivity(intent);

            }
        });


        PlaceModel placeModel = (PlaceModel) getIntent().getSerializableExtra("PlaceModel");

        // initi
        editcategory = (TextView) findViewById(R.id.category);
        editcategory.setText(placeModel.getVicinity());
        edithours = (TextView) findViewById(R.id.openhours);
        resturantname = (TextView) findViewById(R.id.name);
        category = (TextView) findViewById(R.id.category1);
        rate = (TextView) findViewById(R.id.rate);
        openhours = (TextView) findViewById(R.id.openinghours);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        image = (ImageView) findViewById(R.id.imageView4);
        address = (ImageView) findViewById(R.id.address);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Double lang0 = getIntent().getDoubleExtra("langitude1", 0.000);
        final Double lat0 = getIntent().getDoubleExtra("latitude1", 0.0000);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    Toast.makeText(Details.this, lang0 + "" + lat0, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Details.this, MapsActivity.class);
                intent.putExtra("lang", lang);
                intent.putExtra("lat", lat);
                intent.putExtra("lang0", lang0);
                intent.putExtra("lat0", lat0);
                startActivity(intent);
            }
        });
        Typeface mycustom = Typeface.createFromAsset(getAssets(), "fonts/b.otf");
        //  SET Font //
        resturantname.setTypeface(mycustom);
        category.setTypeface(mycustom);
        openhours.setTypeface(mycustom);
        rate.setTypeface(mycustom);

        //  edited text
        resturantname.setText(placeModel.getName());

        if (placeModel.isOpen_now()) {
            edithours.setText("Open now");
        } else {
            edithours.setText("Closed");
        }
        ratingBar.setMax(5);
        ratingBar.setRating(placeModel.getRating());
        ratingBar.setIsIndicator(true);

        try {
            Picasso.with(getApplicationContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=850&photoreference=" +
                    placeModel.photos[0].getPhoto_reference() + "&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q").fit().into(image);
        } catch (java.lang.NullPointerException exception) {
            exception.getMessage();
        }
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lang + "&zoom=12&size=700x700&key=AIzaSyBMW6CSh2_FNWKN07wa9StzldJv6PkuCjk";
        try {
            Picasso.with(getApplicationContext()).load(url).fit().into(address);
        } catch (java.lang.NullPointerException exception) {
            exception.getMessage();
        }
    }


}
