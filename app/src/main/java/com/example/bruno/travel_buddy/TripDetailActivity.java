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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TripDetailActivity extends AppCompatActivity {

    static final int CREATE_BUDGET_REQUEST = 1;
    static final int CREATE_PLACE_REQUEST = 2;
    TripListEngine engine_list;
    TextView tv_budget_initial;
    TextView tv_budget_actual;
    TextView tv_budget_remaining;
    TextView tv_trip_date;
    TextView tv_list_locations;
    TextView tv_label_places;
    Button btn_cost_details;
    Trip trip;
    private static DecimalFormat df2 = new DecimalFormat("0.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_trip_detail);
        setContentView(R.layout.activity_trip_detail_coord);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            trip = extras.getParcelable("TRIP");
            getSupportActionBar().setTitle(trip.getTitle());
            Toast.makeText(this,"ID " + trip.getId(), Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }


        //engine_list = new TripListEngine();

        //engine_list.createDummyData(10);

        //tv_trip_title = (TextView) findViewById(R.id.tv_trip_detail_title);
        tv_trip_date = (TextView) findViewById(R.id.tv_trip_detail_date);

        tv_budget_initial = (TextView) findViewById(R.id.tv_trip_detail_value_budget_initial);
        tv_budget_actual = (TextView) findViewById(R.id.tv_trip_detail_value_budget_actual);
        tv_budget_remaining = (TextView) findViewById(R.id.tv_trip_detail_value_budget_remaining);

        btn_cost_details = findViewById(R.id.btn_details_budget);

        tv_list_locations = (TextView) findViewById(R.id.tv_list_location);
        tv_label_places = findViewById(R.id.tv_label_location);

        //tv_trip_title.setText(trip.getTitle());
        tv_trip_date.setText(trip.getCombinedDate());

        int places = trip.getPlace_list().size();
        tv_label_places.setText("Locations to visit (" + (places - trip.countPlacesVisited()) + " / " + places + ")");
        tv_list_locations.setText(trip.getPlacesInLine());

        double bi = trip.getBudget_initial();
        double ba = trip.getTotalCost();
        double br = trip.getRemainingBudget();
        tv_budget_initial.setText("$ " + df2.format(bi));
        tv_budget_actual.setText("$ " + df2.format(ba));
        tv_budget_remaining.setText("$ " + df2.format(br));

    }

    public void callBudgetDetailsActivity(View v){
        Intent intent = new Intent(v.getContext() , BudgetDetailsActivity.class);
        intent.putExtra("TRIP", trip);
        startActivityForResult(intent, CREATE_BUDGET_REQUEST);
    }

    public void callBudgetCreateActivity(View v){
        Intent intent = new Intent(v.getContext() , NewBudgetActivity.class);
        intent.putExtra("TRIP", trip);
        startActivityForResult(intent, CREATE_BUDGET_REQUEST);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CREATE_BUDGET_REQUEST || requestCode == CREATE_PLACE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                trip = data.getParcelableExtra("TRIP");
                refreshData();
            }
        }
    }

    public void refreshData(){
        //tv_trip_title.setText(trip.getTitle());
        getSupportActionBar().setTitle(trip.getTitle());
        tv_trip_date.setText(trip.getCombinedDate());
        tv_list_locations.setText(trip.getPlacesInLine());

        tv_budget_initial.setText(Double.toString(trip.getBudget_initial()));
        tv_budget_actual.setText(Double.toString(trip.getTotalCost()));
        tv_budget_remaining.setText(Double.toString(trip.getRemainingBudget()));
    }

    public void callPlaceCreateActivity(View view) {
        Intent intent = new Intent(view.getContext() , NewPlaceActivity.class);
        intent.putExtra("TRIP", trip);
        startActivityForResult(intent, CREATE_PLACE_REQUEST);
    }

    public void callPlaceDetailsActivity(View view) {
        Intent intent = new Intent(view.getContext() , PlaceDetailsActivity.class);
        intent.putExtra("TRIP", trip);
        startActivityForResult(intent, CREATE_PLACE_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_cost:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_new_place:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_details_menu, menu);
        return true;
    }
}
