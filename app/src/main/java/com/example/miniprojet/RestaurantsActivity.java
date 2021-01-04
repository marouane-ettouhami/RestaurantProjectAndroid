package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniprojet.Adapters.CategoryAdapter;
import com.example.miniprojet.Adapters.RestaurantAdapter;
import com.example.miniprojet.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsActivity extends AppCompatActivity {


    private LocationManager locationManager;

    private static String longitudePos ;
    private static String latitudePos ;

    @BindView(R.id.list_restaurants)
    ListView listRestau;



    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);


        // Get position

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(RestaurantsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(RestaurantsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RestaurantsActivity.this , new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitudePos = String.valueOf(location.getLongitude());
                latitudePos = String.valueOf(location.getLatitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });





        //getLocation();
        //longitudePos = longitude.getText().toString();
        //latitudePos = latitude.getText().toString();
        //Log.e("onCreate", longitudePos + " " + latitudePos);

        getRestaurant();






       // latitude1.setText(latitude);
        //longitude1.setText(longitude);


    }

    public void getRestaurant() {

        String url = "http://"+MainActivity.IP_ADRESS+":8080/android/webapi/restaurants";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray responseArray) {

                        try {

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                // int idClient = response.getInt("id_client");
                                int id = response.getInt("idRestaurant");
                                String nameRestau = response.getString("name");
                                String stateRestau = response.getString("state");
                                Double lat = response.getDouble("latitude");
                                Double longi = response.getDouble("longitude");
                                String telephone = response.getString("telephone");
                              //  Log.e("restaurant", nameRestau + " " + stateRestau);
                               // Log.e("getRestaurant", longitudePos + " " + latitudePos);
                                double distance = distanceCalcul(Double.parseDouble(latitudePos), Double.parseDouble(longitudePos), lat, longi, "K");
                               // Log.e("distance", Double.toString(distance));

                                restaurants.add(new Restaurant(nameRestau, stateRestau, distance, id, telephone));


                                RestaurantAdapter adapter = new RestaurantAdapter(getApplicationContext(), R.layout.liste_restau, restaurants);
                                listRestau.setAdapter(adapter);
                                listRestau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                       Restaurant item = (Restaurant) parent.getAdapter().getItem(position);
                                       Log.e("item", item.getId() + ": " + item.getName());
                                        Intent intent = new Intent(getApplicationContext(), DetailRestaurantActivity.class);
                                        intent.putExtra("nomRestau", item.getName());
                                        intent.putExtra("tel", item.getPhone());
                                        startActivity(intent);

                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }
                });
        requestQueue.add(jsArrayRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Restaurant> results = new ArrayList<>();
                for(Restaurant x: restaurants) {
                    if(x.getName().contains(newText))
                        results.add(x);
                }

                ((RestaurantAdapter) listRestau.getAdapter()).update(results);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


 private static double distanceCalcul(double lat1, double lon1, double lat2, double lon2, String unit) {
     if ((lat1 == lat2) && (lon1 == lon2)) {
         return 0;
     }
     else {
         double theta = lon1 - lon2;
         double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
         dist = Math.acos(dist);
         dist = Math.toDegrees(dist);
         dist = dist * 60 * 1.1515;
         if (unit.equals("K")) {
             dist = dist * 1.609344;
         } else if (unit.equals("N")) {
             dist = dist * 0.8684;
         }
         return (dist);
     }
 }
}

