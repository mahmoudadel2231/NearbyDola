package com.example.moham.nearby;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moham.nearby.Adapters.PlacesListViewAdapter;
import com.example.moham.nearby.DataModels.PlaceModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ListView listViewNearbyPlaces;
    String url;
    OkHttpClient client;
    PlaceModel[] placeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNearbyPlaces = (ListView) findViewById(R.id.listViewNearbyPlaces);
        client = new OkHttpClient();
        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q";
        getWebService();
    }

    public void getWebService()
    {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
          final   String  s=   response.body().string();
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        try {
            Log.d("zasm", "onResponse: "+s);
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            Log.d("sas", "onResponse: "+jsonArray.toString());
            placeModel = new Gson().fromJson(jsonArray.toString(), PlaceModel[].class);
            Log.d("zamel", "onResponse: "+placeModel.length);
            PlacesListViewAdapter placesListViewAdapter = new PlacesListViewAdapter(MainActivity.this, placeModel);
            listViewNearbyPlaces.setAdapter(placesListViewAdapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
});

            }

        });
    }
}

