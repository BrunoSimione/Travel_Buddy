package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewPlaceActivity extends AppCompatActivity {

    static final int CREATE_PLACE_REQUEST = 2;
    private Trip trip;
    Button btnCreate;
    EditText placeName, placeDate, placeLocation, placeDetails;
    Spinner ddlCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        btnCreate = findViewById(R.id.btn_create_place);
        placeName = findViewById(R.id.et_place_title);
        placeDate = findViewById(R.id.et_place_date);
        placeLocation = findViewById(R.id.et_place_location);
        placeDetails = findViewById(R.id.et_place_details);
        ddlCategories = findViewById(R.id.ddl_place_category);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trip = extras.getParcelable("TRIP");
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.place_categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ddlCategories.setAdapter(adapter);
    }

    public void createNewPlace(View view) {
        String title = placeName.getText().toString();
        String date = placeDate.getText().toString();
        String location = placeLocation.getText().toString();
        String category = ddlCategories.getSelectedItem().toString();
        String details = placeDetails.getText().toString();
        Place p = new Place(title, date, category, details, location, 0);
        trip.getPlace_list().add(p);

        Intent returnIntent = getIntent();
        returnIntent.putExtra("TRIP",trip);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
