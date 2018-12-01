package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.ViewHolder>{
    //TripListEngine listEngine;
    //int trip_id;
    List<Cost> costList;
    private static DecimalFormat df2 = new DecimalFormat("0.##");

    public BudgetListAdapter(List<Cost> costs) {
        //this.listEngine = listEngine;
        this.costList = costs;
    }

    @NonNull
    @Override
    public BudgetListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //create a view, inflate it in a vh, return the vh
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.budget_list_item, viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BudgetListAdapter.ViewHolder viewHolder, int i) {
        //Trip trip = listEngine.getTrip(trip_id);

        double amount = costList.get(i).getAmount();

        viewHolder.tvBudgetName.setText(costList.get(i).getTitle());
        viewHolder.tvBudgetDate.setText(costList.get(i).getDate());
        viewHolder.tvBudgetValue.setText("$ " + df2.format(amount));

        String cat = costList.get(i).getCategory();
        switch (cat){
            case "1":
                viewHolder.imgIcon.setImageResource(R.mipmap.ic_restaurant_icon);
                break;
            case "2":
                viewHolder.imgIcon.setImageResource(R.mipmap.ic_shopping_icon);
                break;
            case "3":
                viewHolder.imgIcon.setImageResource(R.mipmap.ic_leisure_icon);
                break;
            default:
                viewHolder.imgIcon.setImageResource(R.mipmap.ic_restaurant_icon);
                break;
        }


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
        return costList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBudgetName;
        TextView tvBudgetDate;
        TextView tvBudgetValue;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBudgetName = itemView.findViewById(R.id.tv_budget_name);
            tvBudgetDate = itemView.findViewById(R.id.tv_budget_date);
            tvBudgetValue = itemView.findViewById(R.id.tv_budget_value);
            imgIcon = itemView.findViewById(R.id.img_budget_icon);
        }
    }
}
