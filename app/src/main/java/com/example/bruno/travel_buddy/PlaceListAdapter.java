package com.example.bruno.travel_buddy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> implements Filterable{
    //TripListEngine listEngine;
    //int trip_id;
    List<Place> places;
    List<Place> filterList;
    CustomFilter filter;

    public PlaceListAdapter(List<Place> places) {
        //this.listEngine = listEngine;
        this.places = places;
        this.filterList = places;
    }

    @NonNull
    @Override
    public PlaceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //create a view, inflate it in a vh, return the vh
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_list_item, viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceListAdapter.ViewHolder viewHolder, int i) {
        //Trip trip = listEngine.getTrip(trip_id);
        viewHolder.tvPlaceName.setText(places.get(i).getTitle());
        viewHolder.tvPlaceDate.setText(places.get(i).getDate());
        viewHolder.cbPlaceVisited.setChecked(places.get(i).isVisited());

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
        return places.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceName;
        TextView tvPlaceDate;
        CheckBox cbPlaceVisited;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tv_place_name);
            tvPlaceDate = itemView.findViewById(R.id.tv_place_date);
            cbPlaceVisited= itemView.findViewById(R.id.cb_place_visited);
        }
    }
}
