package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class PlaceDetailsActivity extends AppCompatActivity{
    static final int CREATE_PLACE_REQUEST = 2;
    private Trip trip;
    TextView title;
    TextView count;
    TripListEngine engine_list;
    RecyclerView rv_place_list;
    PlaceListAdapter adapter_list;
    Button btnAdd;
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            trip = extras.getParcelable("TRIP");
            //Toast.makeText(this, "ID " + trip_ID, Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }

        title = findViewById(R.id.tv_place_detail_title);
        count = findViewById(R.id.tv_place_detail_count);
        btnAdd = findViewById(R.id.add_place_button);

        title.setText(trip.getTitle() + " Places");
        count.setText("( " + trip.countPlacesVisited() + " / " + trip.getPlace_list().size() + ")");


        rv_place_list = findViewById(R.id.rv_place_list);
        rv_place_list.setLayoutManager(new LinearLayoutManager(this));
        adapter_list = new PlaceListAdapter(trip.getPlace_list(), this);
        rv_place_list.setAdapter(adapter_list);

        sv = findViewById(R.id.searchPlace);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter_list.getFilter().filter(query);
                return false;
            }
        });
    }


    public void openAddPlace(View view) {
        Intent intent = new Intent(view.getContext() , NewBudgetActivity.class);
        intent.putExtra("TRIP", trip);
        startActivityForResult(intent, CREATE_PLACE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CREATE_PLACE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                trip = data.getParcelableExtra("TRIP");
                refreshData();
            }
        }
    }

    public void refreshData(){
        title.setText(trip.getTitle() + " Places");
        count.setText("( " + trip.countPlacesVisited() + " / " + trip.getPlace_list().size() + ")");

        adapter_list = new PlaceListAdapter(trip.getPlace_list(), this);
        rv_place_list.setAdapter(adapter_list);

    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshData();

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("TRIP",trip);
        setResult(RESULT_OK,returnIntent);
        finish();
    }


}
