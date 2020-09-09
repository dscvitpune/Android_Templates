package com.example.dsctemplates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MinActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    RequestQueue requestQueue;
    int i=0;
    List<Record> recordList = new ArrayList<>();
    ArrayList minList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min);

        recyclerView = findViewById(R.id.view1);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MinActivity.this));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd0000019468d5d310a24a5f5191ba02ce4216f2&format=json&offset=0&limit=1000", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonObject = response.getJSONArray("records");
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
                    Collections.sort(recordList);
                    for (i=0; i<10; i++){
                        minList.add(recordList.get(i));
                    }
                    recyclerViewAdapter = new RecyclerViewAdapter(MinActivity.this, minList);
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
