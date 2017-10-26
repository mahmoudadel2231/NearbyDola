package com.example.moham.nearby.Adapters;

import android.content.Context;

import android.graphics.Typeface;
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
import com.github.lzyzsd.randomcolor.RandomColor;
import com.squareup.picasso.Picasso;

public class PlacesListViewAdapter extends ArrayAdapter<PlaceModel> {
    private final Context mContext;


    public PlacesListViewAdapter(@NonNull Context context, @NonNull PlaceModel[] objects) {
        super(context, 0, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_row, parent, false);
        }
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
//        int[] androidColors = getContext().getResources().getIntArray(R.array.androidcolors);
//       int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        view.setBackgroundColor(randomAndroidColor);
        RandomColor randomColor = new RandomColor();
        int color = randomColor.randomColor();
        view.setBackgroundColor(color);
        return convertView;
    }

}
