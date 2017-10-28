package com.example.banha.nearby.Adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.banha.nearby.DataModels.PlaceModel;
import com.example.banha.nearby.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Random;

public class PlacesListViewAdapter extends ArrayAdapter<PlaceModel> {
    private final Context mContext;


    public PlacesListViewAdapter(@NonNull Context context, @NonNull PlaceModel[] objects) {
        super(context, 0, objects);
        mContext = context;

    }
/*
String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
context.startActivity(intent);*/

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_row, parent, false);
        }
        ImageView gmap = convertView.findViewById(R.id.imageView9);
        gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });
        Typeface mycustom = Typeface.createFromAsset(mContext.getAssets(), "fonts/b.otf");

        PlaceModel placeModel = getItem(position);
        ImageView view = convertView.findViewById(R.id.imageView2);
        TextView textViewPlaceName = convertView.findViewById(R.id.name);
        textViewPlaceName.setText(placeModel.getName());
        textViewPlaceName.setTypeface(mycustom);
        TextView textViewPlaceOpenNow = convertView.findViewById(R.id.open);
        if (placeModel.isOpen_now()) {
            textViewPlaceOpenNow.setText("Open now");
        } else {
            textViewPlaceOpenNow.setText("Closed");
        }
        textViewPlaceOpenNow.setTypeface(mycustom);
        RatingBar ratingBarPlaceRate = convertView.findViewById(R.id.ratingBar);
        ratingBarPlaceRate.setMax(5);
        ratingBarPlaceRate.setRating(placeModel.getRating());
        ratingBarPlaceRate.setIsIndicator(true);
        ImageView imageView = convertView.findViewById(R.id.image);
        try {
            Picasso.with(mContext).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=700&photoreference=" +
                    placeModel.photos[0].getPhoto_reference() + "&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q").transform(new CircleTransform()).into(imageView);
        } catch (java.lang.NullPointerException exception) {
            exception.getMessage();
        }
        int[] androidColors = getContext().getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        view.setBackgroundColor(randomAndroidColor);

        return convertView;
    }

}
