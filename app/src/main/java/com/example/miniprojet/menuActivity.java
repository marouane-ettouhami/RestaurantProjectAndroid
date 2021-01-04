package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniprojet.Adapters.CategoryAdapter;
import com.example.miniprojet.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class menuActivity extends AppCompatActivity {

    @BindView(R.id.categoryList)
    ListView categoryList;

      //  categories.add(new Category("1", "Pizza"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        getCategory();

    }


    public void getCategory() {

        String url = "http://"+MainActivity.IP_ADRESS+":8080/android/webapi/categories";
        ArrayList<Category> categories = new ArrayList<Category>();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray responseArray) {

                        try {

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                // int idClient = response.getInt("id_client");
                                String id = response.getString("id");
                                String name = response.getString("name");
                                Log.e("category", id + " " + name);
                                categories.add(new Category(name));

                            }
                            CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), R.layout.liste_cellule, categories);
                            categoryList.setAdapter(adapter);
                            categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getApplicationContext(), RestaurantsActivity.class);
                                    startActivity(intent);
                                }
                            });


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

}
