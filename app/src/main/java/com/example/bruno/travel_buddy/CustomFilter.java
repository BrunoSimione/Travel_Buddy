package com.example.bruno.travel_buddy;


import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    PlaceListAdapter adapter;
    List<Place> filterList;

    public CustomFilter(List<Place> filterList,PlaceListAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Place> filteredPlaces=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint))
                {
                    //ADD PLACE TO FILTERED PLACES
                    filteredPlaces.add(filterList.get(i));
                }
            }

            results.count=filteredPlaces.size();
            results.values=filteredPlaces;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.places = (ArrayList<Place>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
