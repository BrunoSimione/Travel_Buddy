package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewBudgetActivity extends AppCompatActivity {

    static final int CREATE_BUDGET_REQUEST = 1;
    private Trip trip;
    Button btnCreate;
    EditText budgetName, budgetDate, budgetAmount, budgetDetails;
    Spinner ddlCategories;


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
        String category = ddlCategories.getSelectedItem().toString();
        int stars = 3;
        String details = budgetDetails.getText().toString();
        Cost c = new Cost(title, date, amount, category, stars, details);
        trip.getCost_list().add(0, c);

        Intent returnIntent = getIntent();
        returnIntent.putExtra("TRIP",trip);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
