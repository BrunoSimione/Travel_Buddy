package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder>{
    TripListEngine listEngine;

    public TripListAdapter(TripListEngine listEngine) {
        this.listEngine = listEngine;
    }

    @NonNull
    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //create a view, inflate it in a vh, return the vh
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_list_item, viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TripListAdapter.ViewHolder viewHolder, int i) {
        Trip trip = listEngine.getTrip(i);
        viewHolder.tvTripName.setText(trip.getTitle());
        viewHolder.tvTripDate.setText(trip.getCombinedDate());
        viewHolder.tvTripLocation.setText(trip.getLocation());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = viewHolder.getAdapterPosition();
                //Toast.makeText(viewHolder.itemView.getContext(), "Item Clicked: " + itemPosition, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(viewHolder.itemView.getContext() , TripDetailActivity.class);
                intent.putExtra("TRIP", listEngine.getTrip(itemPosition));
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listEngine.getTrip_list().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripName;
        TextView tvTripDate;
        TextView tvTripLocation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tv_trip_name);
            tvTripDate = itemView.findViewById(R.id.tv_trip_date);
            tvTripLocation = itemView.findViewById(R.id.tv_trip_location);
        }
    }
}
