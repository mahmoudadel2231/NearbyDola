package com.example.moham.nearby.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.moham.nearby.DataModels.PlaceModel;
import com.example.moham.nearby.R;
import com.squareup.picasso.Picasso;

public class PlacesListViewAdapter extends ArrayAdapter<PlaceModel> {
    public PlacesListViewAdapter(@NonNull Context context, @NonNull PlaceModel[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_row, parent, false);
        }
        final PlaceModel placeModel = getItem(position);

        TextView textViewPlaceName = convertView.findViewById(R.id.textViewPlaceNameListView);
        textViewPlaceName.setText(placeModel.getName());
        TextView textViewPlaceOpenNow = convertView.findViewById(R.id.textViewPlaceOpenNowListView);
        if (placeModel.isOpen_now()) {
            textViewPlaceOpenNow.setText("Open now");
        } else {
            textViewPlaceOpenNow.setText("Closed");
        }
        RatingBar ratingBarPlaceRate = convertView.findViewById(R.id.ratingBar);
        ratingBarPlaceRate.setMax(5);
        ratingBarPlaceRate.setRating(placeModel.getRating());
        ratingBarPlaceRate.setIsIndicator(true);
        ImageView imageViewPlaceIcon = convertView.findViewById(R.id.imageViewPlaceIconListView);
        Picasso.with(getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=" + placeModel.getPhotoReference() + "&key=AIzaSyD8aYBQLmxk1qsY7wkojwiH_wZeCBI6QKA").into(imageViewPlaceIcon);
        return convertView;
    }

}
