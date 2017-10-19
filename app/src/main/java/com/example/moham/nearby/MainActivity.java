package com.example.moham.nearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

import static com.example.moham.nearby.R.menu.men;

public class MainActivity extends AppCompatActivity {

    ListView listViewNearbyPlaces;
    String url;
    OkHttpClient client;
    PlaceModel[] placeModel;
    String input;
    EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imgSearch);
        listViewNearbyPlaces = (ListView) findViewById(R.id.listViewNearbyPlaces);
        client = new OkHttpClient();
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                registerForContextMenu(editText);
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString().trim();
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type= resturant&keyword=" + input + "&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q";
                getWebService();
            }
        });
    }

    public void getWebService() {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            placeModel = new Gson().fromJson(jsonArray.toString(), PlaceModel[].class);
                            PlacesListViewAdapter placesListViewAdapter = new PlacesListViewAdapter(MainActivity.this, placeModel);
                            listViewNearbyPlaces.setAdapter(placesListViewAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(men, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.res:
                editText.setText("resturant");
                return true;
            case R.id.hot:
                editText.setText("hotel");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}

