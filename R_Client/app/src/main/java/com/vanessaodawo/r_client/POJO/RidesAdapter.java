package com.vanessaodawo.r_client.POJO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanessaodawo.r_client.R;

import java.util.List;

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.MyHolder>  {

    List<Rides_Trips> list;
    Context context;
    String img;

    public RidesAdapter(List<Rides_Trips> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RidesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rides, parent, false);
        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesAdapter.MyHolder holder, int position) {
        Rides_Trips rtList = list.get(position);
        holder.from.setText(rtList.getFrom());
        holder.time.setText(rtList.getTime());
        holder.cost.setText(rtList.getCost());
    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try {
            if (list.size() == 0) {
                arr = 0;
            }else {
                arr = list.size();
            }
        }catch (Exception ignored) { }

        return arr;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView from, time, cost;

        public MyHolder(View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.from);
            time = itemView.findViewById(R.id.time);
            cost = itemView.findViewById(R.id.cost);
        }
    }
}
