package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailRestaurantActivity extends AppCompatActivity {

    private double latitude = 0;
    private double longitude = 0;
    private String phone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant_acitiv);
        ButterKnife.bind(this);
        String name = getIntent().getStringExtra("nomRestau");

        showMap(name);

    }

   @OnClick(R.id.call)
    public void CallRestaraunt() {
        String phone = getIntent().getStringExtra("tel");
        Uri telNumber = Uri.parse("tel:"+phone);
        Intent call = new Intent(Intent.ACTION_DIAL, telNumber);
        startActivity(call);
    }

    @OnClick(R.id.qr_code)
    public void ScanQR() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @OnClick(R.id.log_out)
    public void logOut() {
        SharedPreferences sharedPrefs =getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

        //show login form
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showMap(String name) {
        String url = "http://"+MainActivity.IP_ADRESS+":8080/android/webapi/restaurants/restaurant/" + name;


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray responseArray) {

                        try {

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                // int idClient = response.getInt("id_client");
                                latitude = response.getDouble("latitude");
                                longitude = response.getDouble("longitude");
                                // phone = response.getString("telephone");

                                Fragment fragment = new MapFragment();
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_layout, fragment)
                                        .commit();

                                Bundle bundle = new Bundle();
                                bundle.putDouble("latitude", latitude);
                                bundle.putDouble("longitude", longitude);
                                fragment.setArguments(bundle);
                                //bundle.putString("test","Greetings from activity");

                                //
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                        Log.e("onResponse", latitude + " " + longitude);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }


                });
        requestQueue.add(jsArrayRequest);
    }



}