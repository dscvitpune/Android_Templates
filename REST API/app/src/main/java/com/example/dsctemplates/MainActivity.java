package com.example.dsctemplates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final String BASE_URL = "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd0000015c4e13aaf9b441357c6510208452f298&format=json&offset=0&limit=1000";
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    RequestQueue requestQueue;
    int i=0;
    List<Record> recordList = new ArrayList<>();
    Button min, max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        min = findViewById(R.id.min_button);
        max = findViewById(R.id.max_button);

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MinActivity.class));
            }
        });

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MaxActivity.class));
            }
        });

        recyclerView = findViewById(R.id.view2);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd0000019468d5d310a24a5f5191ba02ce4216f2&format=json&offset=0&limit=1000", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonObject = response.getJSONArray("records");
                    List<Record> recordList = new ArrayList<>();
                    for (i=0; i < jsonObject.length(); i++){
                        try {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Record record = new Record();
                            record.setArrival_date(jsonObject1.getString("arrival_date"));
                            record.setCommodity(jsonObject1.getString("commodity"));
                            record.setDistrict(jsonObject1.getString("district"));
                            record.setMarket(jsonObject1.getString("market"));
                            record.setMax_price(jsonObject1.getString("max_price"));
                            record.setMin_price(jsonObject1.getString("min_price"));
                            record.setModal_price(jsonObject1.getString("modal_price"));
                            record.setTimestamp(jsonObject1.getString("timestamp"));
                            record.setState(jsonObject1.getString("state"));
                            record.setVariety(jsonObject1.getString("variety"));
                            recordList.add(record);
                        } catch (JSONException e) {
                            Log.d("myapp", "Error is" + e);
                        }
                    }
                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, recordList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Log.d("myapp", "Error is" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}