package com.example.bruno.travel_buddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetDetailsActivity extends AppCompatActivity{
    private Trip trip;
    TextView title;
    TextView initial_cost;
    TextView actual_cost;
    TextView remaining_cost;
    TripListEngine engine_list;
    RecyclerView rv_budget_list;
    RecyclerView.Adapter adapter_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("TRIP_ID");
            trip = extras.getParcelable("TRIP");
            //Toast.makeText(this, "ID " + trip_ID, Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }

        title = findViewById(R.id.tv_budget_detail_title);
        initial_cost = findViewById(R.id.tv_budget_detail_value_budget_initial);
        actual_cost = findViewById(R.id.tv_budget_detail_value_budget_actual);
        remaining_cost = findViewById(R.id.tv_budget_detail_value_budget_remaining);

        //engine_list = new TripListEngine();
        //engine_list.createDummyData(10);

        title.setText(trip.getTitle() + " Budget");
        initial_cost.setText(Double.toString(trip.getBudget_initial()));
        actual_cost.setText(Double.toString(trip.getTotalCost()));
        remaining_cost.setText(Double.toString(trip.getRemainingBudget()));

        rv_budget_list = findViewById(R.id.rv_budget_list);
        rv_budget_list.setLayoutManager(new LinearLayoutManager(this));
        //engine_list = new TripListEngine();
        //engine_list.createDummyData(10);
        //engine_list.createDummyData2(5);
        adapter_list = new BudgetListAdapter(trip);
        rv_budget_list.setAdapter(adapter_list);
    }


}
