package com.example.bruno.travel_buddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
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

public class LoginActivty extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    ImageView iv_icon;

    RequestQueue requestQueue; // This is our requests queue to process our HTTP requests.
    String baseUrl = "https://brunosbeltrame.000webhostapp.com/api/user/read.php";
    String url;
    int USER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        iv_icon = findViewById(R.id.login_icon);

    }

    public void loginClicked(View view) {

        if (!et_email.getText().toString().isEmpty() && !et_email.getText().toString().isEmpty()){

            RotateAnimation rotateAnimation = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(5000);
            iv_icon.startAnimation(rotateAnimation);

            //intent.putExtra("TRIP_ID", trip_ID);

            requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests.
            getRepoList();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(USER_ID == 0){
                        Toast.makeText(getBaseContext(), "Couldn't retrieve information. Please, try again later!", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(getBaseContext() , TripListActivity.class);
                        intent.putExtra("USER_ID", USER_ID);
                        startActivity(intent);
                        finish();
                    }

                }
            }, 5000);

        }else{
            Toast.makeText(getBaseContext(), "Please enter an email/password", Toast.LENGTH_SHORT).show();
        }
    }

    public void newUserClicked(View view) {
        Intent intent = new Intent(view.getContext() , NewUserActivity.class);
        startActivity(intent);
    }

    private void getRepoList() {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        this.url = this.baseUrl + "?email=" + email + "&password=" + password;
        Log.e("API",this.url);

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            if(response.has("message")){
                                Toast.makeText(getBaseContext(), "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                            }else{
                                // The user does have repos, so let's loop through them all.
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        // For each repo, add a new line to our repo list.

                                        JSONArray jsonArray = response.getJSONArray("user");
                                        JSONObject jsonUser = jsonArray.getJSONObject(0);

                                        USER_ID = jsonUser.getInt("id");

                                    } catch (JSONException e) {
                                        // If there is an error then output this to the logs.
                                        Log.e("Volley", "Invalid JSON Object. ");
                                        Log.e("Volley", e.getMessage());
                                    }

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
}
