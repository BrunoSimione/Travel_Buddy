package com.example.bruno.travel_buddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> implements Filterable{
    //TripListEngine listEngine;
    //int trip_id;
    List<Place> places;
    List<Place> filterList;
    CustomFilter filter;
    private Context mCtx;
    String baseUrl = "http://brunosbeltrame.000webhostapp.com/api/place/update.php";
    String url;

    String baseUrlDelete = "http://brunosbeltrame.000webhostapp.com/api/place/delete.php";
    String urlDelete;
    RequestQueue requestQueue;

    public PlaceListAdapter(List<Place> places, Context c) {
        //this.listEngine = listEngine;
        this.places = places;
        this.filterList = places;
        mCtx = c;
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
    public void onBindViewHolder(@NonNull final PlaceListAdapter.ViewHolder viewHolder, final int i) {
        //Trip trip = listEngine.getTrip(trip_id);
        viewHolder.tvPlaceName.setText(places.get(i).getTitle());
        viewHolder.tvPlaceDate.setText(places.get(i).getDate());
        viewHolder.cbPlaceVisited.setChecked(places.get(i).isVisited());
        viewHolder.tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //creating a popup menu
                final PopupMenu popup = new PopupMenu(mCtx, viewHolder.tvMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.places_item_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //Change Visited
                                boolean status = !viewHolder.cbPlaceVisited.isChecked();
                                viewHolder.cbPlaceVisited.setChecked(status);
                                filterList.get(i).setVisited(status);
                                requestQueue = Volley.newRequestQueue(mCtx); // This setups up a new request queue which we will need to make HTTP requests.
                                getRepoList(filterList.get(i));
                                break;
                            case R.id.menu2:
                                //Delete
                                Place c = filterList.get(i);
                                filterList.remove(i);
                                requestQueue = Volley.newRequestQueue(mCtx); // This setups up a new request queue which we will need to make HTTP requests.
                                getRepoList2(c);
                                break;
                            case R.id.menu3:
                                //Maps
                                String param =  filterList.get(i).getLocation();
                                Log.e("Click", param);
                                String nParam = param.replace(" ", "+");
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + nParam + "&mode=w");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                mCtx.startActivity(mapIntent);

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

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

    private void getRepoList(Place p) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.url = this.baseUrl;

        Log.e("API",this.url);

        try {
            JSONObject jsonTrip = new JSONObject();
            jsonTrip.put("id", p.getId());
            jsonTrip.put("visited", p.isVisitedInt());

            Log.e("TripJSON", jsonTrip.toString());

            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, jsonTrip,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Check the length of our response (to see if the user has any repos)
                            if (response.length() > 0) {
                                // The user does have repos, so let's loop through them all.
                                Log.e("VolleyInsert" , response.toString());
                            } else {
                                // The user didn't have any repos
                                Log.e("VolleyInsert", "No repos found");
                            }
                            Log.e("VolleyInsert2" , response.toString());
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // If there a HTTP error then add a note to our repo list.
                            Log.e("VolleyE", error.toString());
                            error.printStackTrace();
                        }
                    }
            );

            requestQueue.add(arrReq);

        }catch(Exception e){
            Log.e("JSONError", e.getMessage());
            e.printStackTrace();
        }

    }

    private void getRepoList2(Place p) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).

        this.urlDelete = this.baseUrlDelete;

        Log.e("API",this.urlDelete);

        try {
            JSONObject jsonTrip = new JSONObject();
            jsonTrip.put("id", p.getId());

            Log.e("TripJSON", jsonTrip.toString());

            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urlDelete, jsonTrip,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Check the length of our response (to see if the user has any repos)
                            if (response.length() > 0) {
                                // The user does have repos, so let's loop through them all.
                                Log.e("VolleyInsert" , response.toString());
                            } else {
                                // The user didn't have any repos
                                Log.e("VolleyInsert", "No repos found");
                            }
                            Log.e("VolleyInsert2" , response.toString());
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // If there a HTTP error then add a note to our repo list.
                            Log.e("VolleyE", error.toString());
                            error.printStackTrace();
                        }
                    }
            );

            requestQueue.add(arrReq);

        }catch(Exception e){
            Log.e("JSONError", e.getMessage());
            e.printStackTrace();
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceName;
        TextView tvPlaceDate;
        CheckBox cbPlaceVisited;
        TextView tvMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tv_place_name);
            tvPlaceDate = itemView.findViewById(R.id.tv_place_date);
            cbPlaceVisited= itemView.findViewById(R.id.cb_place_visited);
            tvMenu = itemView.findViewById(R.id.tv_place_options);
        }
    }
}
