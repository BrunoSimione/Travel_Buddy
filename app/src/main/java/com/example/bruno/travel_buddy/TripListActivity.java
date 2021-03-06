package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TripListActivity extends AppCompatActivity {

    RecyclerView rv_list;
    RecyclerView.Adapter adapter_list;
    TripListEngine engine_list;
    RequestQueue requestQueue; // This is our requests queue to process our HTTP requests.
    static final int CREATE_TRIP_REQUEST = 3;

    int USER_ID;
    String baseUrl = "https://brunosbeltrame.000webhostapp.com/api/trip/readAll.php";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            USER_ID = extras.getInt("USER_ID");
            //Toast.makeText(this,"ID " + trip.getId(), Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }

        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests.
        rv_list = findViewById(R.id.rv_trip_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        engine_list = new TripListEngine();
        engine_list.setUser_id(USER_ID);
        //engine_list.createDummyData(10);
        getRepoList(USER_ID);
        //engine_list.createDummyData2(5);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.e("EngineSize", String.valueOf(engine_list.getTrip_list().size()));
                adapter_list = new TripListAdapter(engine_list);
                rv_list.setAdapter(adapter_list);
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getBaseContext() , LoginActivty.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getRepoList(int userid) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.url = this.baseUrl + "?id=" + userid;

        Log.e("API",this.url);

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.

                                    JSONArray jsonArray = response.getJSONArray("trips");

                                    for(int a = 0; a < jsonArray.length(); a++){
                                        JSONObject  jsonObj = jsonArray.getJSONObject(a);
                                        Log.e("Volley", "ADD Called " + jsonObj);
                                        engine_list.addTripJson(jsonObj);

                                    }
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object. ");
                                    Log.e("Volley", e.getMessage());
                                }

                            }
                        } else {
                            // The user didn't have any repos
                            Log.e("Volley", "No repos found");
                        }

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CREATE_TRIP_REQUEST ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Trip t = data.getParcelableExtra("NEW_TRIP");
                engine_list.getTrip_list().add(t);
                adapter_list = new TripListAdapter(engine_list);
                rv_list.setAdapter(adapter_list);
            }
        }
    }

}
