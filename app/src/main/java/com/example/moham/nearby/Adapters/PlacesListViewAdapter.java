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

public class PlacesListViewAdapter extends ArrayAdapter<PlaceModel>
{
    public PlacesListViewAdapter(@NonNull Context context, @NonNull PlaceModel[] objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_row, parent, false);
        }
        PlaceModel placeModel = getItem(position);
        TextView textViewPlaceName = (TextView) convertView.findViewById(R.id.textViewPlaceNameListView);
        textViewPlaceName.setText(placeModel.getName());
        TextView textViewPlaceOpenNow = (TextView) convertView.findViewById(R.id.textViewPlaceOpenNowListView);
        if (placeModel.isOpen_now())
        {
            textViewPlaceOpenNow.setText("Open now");
        }
        else
        {
            textViewPlaceOpenNow.setText("Closed");
        }
        RatingBar ratingBarPlaceRate = (RatingBar) convertView.findViewById(R.id.ratingBarPlaceRate);
        ratingBarPlaceRate.setRating(placeModel.getRating());
        ImageView imageViewPlaceIcon = (ImageView) convertView.findViewById(R.id.imageViewPlaceIconListView);
        Picasso.with(getContext()).load(placeModel.getIcon()).into(imageViewPlaceIcon);
        return convertView;
    }
}
