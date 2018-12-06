package com.example.bruno.travel_buddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUserActivity extends AppCompatActivity {

    EditText et_name, et_email, et_psw, et_psw2;
    TextView tv_errors;
    Button createUser;

    private RequestQueue requestQueue;
    private static NewUserActivity mInstance;
    String url = "http://brunosbeltrame.000webhostapp.com/api/user/create.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        et_name = findViewById(R.id.et_user_name);
        et_email = findViewById(R.id.et_user_email);
        et_psw = findViewById(R.id.et_user_password);
        et_psw2 = findViewById(R.id.et_user_password2);
        createUser = findViewById(R.id.btn_create_user);
        tv_errors = findViewById(R.id.tv_new_user_error);
        mInstance = this;

    }

    public static synchronized NewUserActivity getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }
    /*
    Cancel all the requests matching with the given tag
    */
    public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    public void createNewUser(View view) {

        String errors = "";
        if(et_psw.getText().toString().equals(et_psw2.getText().toString())){

            try{
                JSONObject userJSON = new JSONObject();
                userJSON.put("name", et_name.getText().toString());
                userJSON.put("email", et_email.getText().toString());
                userJSON.put("password", et_psw.getText().toString());

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, userJSON,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getBaseContext(), "Account created!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                Log.e("Insert Volley ",error.toString());
                            }
                        });
                // Adding the request to the queue along with a unique string tag
                NewUserActivity.getInstance().addToRequestQueue(jsonObjReq, "postRequest");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            errors += "The passwords must match \n";
        }

        tv_errors.setText(errors);

    }
}
