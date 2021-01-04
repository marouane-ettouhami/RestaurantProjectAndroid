package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    //Todo: Change IP_ADRESS with your ipv4 adress
    public static String IP_ADRESS = "192.168.127.1";
    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getUser();
    }

    @BindView(R.id.editTextLog)
    EditText ndc;
    @BindView(R.id.editTextPwd)
    EditText mdp;






    @OnClick(R.id.buttonSub)
    public void ButtonClicked() {

        String ndc_, mdp_;
        ndc_ = ndc.getText().toString();
        mdp_ = mdp.getText().toString();

        String url = "http://"+IP_ADRESS+":8080/android/webapi/login";



            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                        @Override
                        public void onResponse(JSONArray responseArray) {

                            try {

                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject response = responseArray.getJSONObject(i);
                                   // int idClient = response.getInt("id_client");
                                    String login = response.getString("login");
                                    String password = response.getString("password");
                                    Log.e("test", login + password);

                                    if (login.equals(ndc_) && password.equals(mdp_)) {

                                        CheckBox ch=(CheckBox)findViewById(R.id.checkBox);
                                        if(ch.isChecked())
                                            rememberMe(login,password);

                                        Intent intent = new Intent(getApplicationContext(), menuActivity.class);
                                        startActivity(intent);

                                        finish();
                                    }

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

    public void getUser(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            //directly show logout form
            Intent intent = new Intent(getApplicationContext(), menuActivity.class);
            startActivity(intent);
        }
    }

    public void rememberMe(String user, String password){
        //save username and password in SharedPreferences
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME,user)
                .putString(PREF_PASSWORD,password)
                .commit();
    }

    }
