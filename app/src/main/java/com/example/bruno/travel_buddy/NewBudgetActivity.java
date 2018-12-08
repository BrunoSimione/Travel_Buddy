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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NewBudgetActivity extends AppCompatActivity {

    static final int CREATE_BUDGET_REQUEST = 1;
    RequestQueue requestQueue; // This is our requests queue to process our HTTP requests.
    private Trip trip;
    Button btnCreate;
    EditText budgetName, budgetDate, budgetAmount, budgetDetails;
    Spinner ddlCategories;
    int USER_ID, TRIP_ID;
    String baseUrl = "http://brunosbeltrame.000webhostapp.com/api/cost/create.php";
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);
        btnCreate = findViewById(R.id.btn_create_budget);
        budgetName = findViewById(R.id.et_budget_title);
        budgetDate = findViewById(R.id.et_budget_date);
        budgetAmount = findViewById(R.id.et_budget_amount);
        budgetDetails = findViewById(R.id.et_budget_details);
        ddlCategories = findViewById(R.id.ddl_budget_category);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            trip = extras.getParcelable("TRIP");
            USER_ID = extras.getInt("USER_ID");
            //Toast.makeText(this,"ID " + trip.getId(), Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ddlCategories.setAdapter(adapter);
    }

    public void createNewBudget(View view) {
        String title = budgetName.getText().toString();
        String date = budgetDate.getText().toString();
        double amount = Double.parseDouble(budgetAmount.getText().toString());

        String category = String.valueOf(ddlCategories.getSelectedItemPosition()+1);
        int stars = 3;
        String details = budgetDetails.getText().toString();
        Cost c = new Cost(title, date, amount, category, stars, details);
        trip.getCost_list().add(0, c);

        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests.
        TRIP_ID = trip.getId();
        getRepoList(TRIP_ID, c);

        Intent returnIntent = getIntent();
        returnIntent.putExtra("TRIP",trip);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private void getRepoList(int trip_id, Cost c) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.url = this.baseUrl;

        Log.e("API",this.url);

        try {
            JSONObject jsonTrip = new JSONObject();
            jsonTrip.put("name", c.getTitle());
            jsonTrip.put("date", c.getDate());
            jsonTrip.put("amount", c.getAmount());
            jsonTrip.put("category", Integer.valueOf(c.getCategory()));
            jsonTrip.put("rate", c.getStars());
            jsonTrip.put("details", c.getDetails());
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
