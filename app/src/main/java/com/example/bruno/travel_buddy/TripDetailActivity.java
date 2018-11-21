package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailActivity extends AppCompatActivity {
    TripListEngine engine_list;
    TextView tv_budget_initial;
    TextView tv_budget_actual;
    TextView tv_budget_remaining;
    TextView tv_trip_date;
    TextView tv_trip_title;
    TextView tv_list_locations;
    Button btn_cost_details;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            trip = extras.getParcelable("TRIP");
            Toast.makeText(this,"ID " + trip.getId(), Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }


        //engine_list = new TripListEngine();

        //engine_list.createDummyData(10);

        tv_trip_title = (TextView) findViewById(R.id.tv_trip_detail_title);
        tv_trip_date = (TextView) findViewById(R.id.tv_trip_detail_date);

        tv_budget_initial = (TextView) findViewById(R.id.tv_trip_detail_value_budget_initial);
        tv_budget_actual = (TextView) findViewById(R.id.tv_trip_detail_value_budget_actual);
        tv_budget_remaining = (TextView) findViewById(R.id.tv_trip_detail_value_budget_remaining);

        btn_cost_details = findViewById(R.id.btn_details_budget);

        tv_list_locations = (TextView) findViewById(R.id.tv_list_location);

        tv_trip_title.setText(trip.getTitle());
        tv_trip_date.setText(trip.getCombinedDate());
        tv_list_locations.setText(trip.getPlacesInLine());

        tv_budget_initial.setText(Double.toString(trip.getBudget_initial()));
        tv_budget_actual.setText(Double.toString(trip.getTotalCost()));
        tv_budget_remaining.setText(Double.toString(trip.getRemainingBudget()));




    }

    public void callBudgetDetailsActivty(View v){
        //NEED TO WORK ON THIS
        Intent intent = new Intent(v.getContext() , BudgetDetailsActivity.class);
        intent.putExtra("TRIP", trip);
        startActivity(intent);
    }
}
