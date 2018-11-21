package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivty extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        iv_icon = findViewById(R.id.login_icon);

    }

    public void loginClicked(View view) {

        if (et_email.getText().toString().equals("a") && et_email.getText().toString().equals("a")){

            RotateAnimation rotateAnimation = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(5000);
            iv_icon.startAnimation(rotateAnimation);

            //intent.putExtra("TRIP_ID", trip_ID);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getBaseContext() , TripListActivity.class);
                    startActivity(intent);
                }
            }, 5000);

        }
    }
}
