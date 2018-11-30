package com.example.bruno.travel_buddy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder>{
    //TripListEngine listEngine;
    //int trip_id;
    Trip trip;

    public TravelListAdapter(Trip trip) {
        //this.listEngine = listEngine;
        this.trip = trip;
    }

    @NonNull
    @Override
    public TravelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //create a view, inflate it in a vh, return the vh
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.budget_list_item, viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TravelListAdapter.ViewHolder viewHolder, int i) {
        //Trip trip = listEngine.getTrip(trip_id);
        viewHolder.tvBudgetName.setText(trip.getCost_list().get(i).getTitle());
        viewHolder.tvBudgetDate.setText(trip.getCost_list().get(i).getDate());
        viewHolder.tvBudgetValue.setText(Double.toString(trip.getCost_list().get(i).getAmount()));

        /*
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = viewHolder.getAdapterPosition();
                //Toast.makeText(viewHolder.itemView.getContext(), "Item Clicked: " + itemPosition, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(viewHolder.itemView.getContext() , TripDetailActivity.class);
                intent.putExtra("TRIP_ID", itemPosition);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return trip.getCost_list().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBudgetName;
        TextView tvBudgetDate;
        TextView tvBudgetValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBudgetName = itemView.findViewById(R.id.tv_budget_name);
            tvBudgetDate = itemView.findViewById(R.id.tv_budget_date);
            tvBudgetValue= itemView.findViewById(R.id.tv_budget_value);
        }
    }
}
