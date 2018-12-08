package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NewPlaceActivity extends AppCompatActivity {

    static final int CREATE_PLACE_REQUEST = 2;
    private Trip trip;
    Button btnCreate;
    EditText placeName, placeDate, placeLocation, placeDetails;
    Spinner ddlCategories;
    int TRIP_ID;
    String baseUrl = "http://brunosbeltrame.000webhostapp.com/api/place/create.php";
    String url;
    RequestQueue requestQueue; // This is our requests queue to process our HTTP requests.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        btnCreate = findViewById(R.id.btn_create_place);
        placeName = findViewById(R.id.et_place_title);
        placeDate = findViewById(R.id.et_place_date);
        placeLocation = findViewById(R.id.et_place_location);
        placeDetails = findViewById(R.id.et_place_details);
        ddlCategories = findViewById(R.id.ddl_place_category);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trip = extras.getParcelable("TRIP");
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.place_categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ddlCategories.setAdapter(adapter);


    }

    public void createNewPlace(View view) {
        String title = placeName.getText().toString();
        String date = placeDate.getText().toString();
        String location = placeLocation.getText().toString();
        String category = String.valueOf(ddlCategories.getSelectedItemPosition()+1);
        String details = placeDetails.getText().toString();
        Place p = new Place(0, title, date, category, details, location, 0);
        trip.getPlace_list().add(p);

        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests.
        TRIP_ID = trip.getId();
        getRepoList(TRIP_ID, p);

        Intent returnIntent = getIntent();
        returnIntent.putExtra("TRIP",trip);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private void getRepoList(int trip_id, Place p) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.url = this.baseUrl;

        Log.e("API",this.url);

        try {
            JSONObject jsonTrip = new JSONObject();
            jsonTrip.put("name", p.getTitle());
            jsonTrip.put("date", p.getDate());
            jsonTrip.put("category", Integer.valueOf(p.getCategory()));
            jsonTrip.put("location", p.getLocation());
            jsonTrip.put("details", p.getDetails());
            jsonTrip.put("trip_id", trip_id);

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
                            Log.e("VolleyE", error.toString());
                            error.printStackTrace();
                        }
                    }
            );

            requestQueue.add(arrReq);

        }catch(Exception e){
            Log.e("JSONError", e.getMessage());
            e.printStackTrace();
        }

    }
}
