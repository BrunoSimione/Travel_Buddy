package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewTripActivity extends AppCompatActivity {

    static final int CREATE_TRIP_REQUEST = 3;
    RequestQueue requestQueue; // This is our requests queue to process our HTTP requests.
    private int USER_ID;
    private int TRIP_COUNT;
    Button btnCreate;
    EditText tripName, tripBudget, tripDate, tripEndDate, tripLocation, tripDetails;
    String baseUrl = "http://brunosbeltrame.000webhostapp.com/api/trip/create.php";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        btnCreate = findViewById(R.id.btn_create_trip);
        tripName = findViewById(R.id.et_trip_title);
        tripDate = findViewById(R.id.et_trip_date);
        tripEndDate = findViewById(R.id.et_trip_end_date);
        tripBudget = findViewById(R.id.et_trip_budget);
        tripLocation = findViewById(R.id.et_trip_location);
        tripDetails = findViewById(R.id.et_trip_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            USER_ID = extras.getInt("USER_ID");
            TRIP_COUNT = extras.getInt("TRIP_COUNT");
        }
    }

    public void createNewTrip(View view) {
        String title = tripName.getText().toString();
        String date = tripDate.getText().toString();
        String endDate = tripEndDate.getText().toString();
        double budget = Double.valueOf(tripBudget.getText().toString());
        String location = tripLocation.getText().toString();
        String details = tripDetails.getText().toString();

        Trip t = new Trip(TRIP_COUNT, title, location, details, date, endDate, budget);
        //trip_engine.getTrip_list().add(t);

        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests.
        getRepoList(USER_ID, t);

        Intent returnIntent = getIntent();
        returnIntent.putExtra("NEW_TRIP", t);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private void getRepoList(int userid, Trip t) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.url = this.baseUrl;

        Log.e("API",this.url);

        try {
            JSONObject jsonTrip = new JSONObject();
            jsonTrip.put("title", t.getTitle());
            jsonTrip.put("start_date", t.getDateDB(t.getDate_start()));
            jsonTrip.put("end_date", t.getDateDB(t.getDate_end()));
            jsonTrip.put("details", t.getDetails());
            jsonTrip.put("location", t.getLocation());
            jsonTrip.put("user_id", userid);

            Log.e("TripJSON", jsonTrip.toString());

            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, jsonTrip,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Check the length of our response (to see if the user has any repos)
                            if (response.length() > 0) {
                                // The user does have repos, so let's loop through them all.
                                Log.e("VolleyInsert" , response.toString());
                            } else {
                                // The user didn't have any repos
                                Log.e("VolleyInsert", "No repos found");
                            }
                            Log.e("VolleyInsert2" , response.toString());

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // If there a HTTP error then add a note to our repo list.
                            Log.e("Volley", error.toString());
                        }
                    }
            );
            // Add the request we just defined to our request queue.
            // The request queue will automatically handle the request as soon as it can.
            requestQueue.add(arrReq);

        }catch(Exception e){
            Log.e("JSONError", e.getMessage());
            e.printStackTrace();
        }
        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html

    }
}
