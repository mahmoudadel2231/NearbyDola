package com.example.banha.nearby;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banha.nearby.Adapters.PlacesListViewAdapter;
import com.example.banha.nearby.DataModels.PlaceModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.banha.nearby.R.menu.men;

public class Search extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;

    ListView listViewNearbyPlaces;
    String url;
    OkHttpClient client;
    PlaceModel[] placeModel;
    String input;
    EditText editText;
    ImageView imageView;
    TextView textView;
    LocationRequest mLocationRequest;
    Double Latitude, longitude;
    ImageView Shutdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        Shutdown = (ImageView) findViewById(R.id.ImageSuhtdown);
        Shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        int location_permession_statua = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);

        }
        Log.d("zamel", "onCreate: " + location_permession_statua);
//        Typeface mycustom = Typeface.createFromAsset(getAssets(), "fonts/b.otf");
//        textView.setTypeface(mycustom);

        textView = (TextView) findViewById(R.id.textView);
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
                //Toast.makeText(Search.this, Latitude + " m" + longitude, Toast.LENGTH_LONG).show();
                //Sydney
               url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q";
                //Madent NAsr
              //  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=30.056220,31.330211&radius=500&type=restaurant&keyword=cruise&key=AIzaSyC-Aw8ExKIvYXAHtHRc3yRsDtgFDvr2j3Q";
                //current
                //  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + Latitude + "," + longitude + "&radius=500&type=" + input + "&key=AIzaSyAUALAN8KpdviIt6N0eGBzMNk9gSxmDV3g";
                getWebService();
            }
        });
    }

    public void getWebService() {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Search.this, "Error", Toast.LENGTH_LONG).show();
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
                            final Double lng = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                            final Double lat = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");

                            final PlacesListViewAdapter placesListViewAdapter = new PlacesListViewAdapter(Search.this, placeModel);
                            listViewNearbyPlaces.setAdapter(placesListViewAdapter);
                            listViewNearbyPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    long viewId = view.getId();
                                    if (viewId == R.id.imageView9) {
                                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        startActivity(intent) ;
                                    }
                                    Intent intent = new Intent(Search.this, Details.class);
                                    intent.putExtra("PlaceModel", placeModel[i]);
                                    // Toast.makeText(Search.this,lat + "  "  +lng , Toast.LENGTH_SHORT).show();
                                    intent.putExtra("langitude", lng);
                                    intent.putExtra("latitude", lat);
                                    //  Intent intent1 = new Intent(Search.this, Details.class);
                                    // Toast.makeText(Search.this, longitude + "" + Latitude, Toast.LENGTH_SHORT).show();
                                    intent.putExtra("langitude1", longitude);
                                    intent.putExtra("latitude1", Latitude);
                                    startActivity(intent);

                                }
                            });


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
            case R.id.bank:
                editText.setText("bank");
                return true;
            case R.id.school:
                editText.setText("school");
                return true;
            case R.id.stadium:
                editText.setText("stadium");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("k", "onRequestPermissionsResult: " + "yes");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            99);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            createLocationRequest();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            if (mLastLocation != null) {
                Latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                //   Log.d("zzz", "onConnected: " + mLastLocation.getLatitude() + " m " + mLastLocation.getLongitude());
                //  Toast.makeText(this, mLastLocation.getLatitude() + " m " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        //   Toast.makeText(Search.this, "the change" + location.getLatitude() + " m " + location.getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}

