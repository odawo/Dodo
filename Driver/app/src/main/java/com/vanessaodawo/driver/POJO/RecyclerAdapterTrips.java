package com.vanessaodawo.driver.POJO;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanessaodawo.driver.R;

import java.util.List;

/**
 * Created by Vanessa on 01/05/2018.
 */

public class RecyclerAdapterTrips extends RecyclerView.Adapter<RecyclerAdapterTrips.MyHolder>{

    List<DriverTrips> list;
    Context context;
    Context mContext;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trips, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        DriverTrips myList = list.get(position);
        holder.time.setText(myList.getDropOffTime().toString());
        holder.dropoff.setText(myList.dropOffLocation);
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
        TextView time, dropoff;
        public MyHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tvTime);
            dropoff = itemView.findViewById(R.id.tvDropOffPoint);
        }
    }
}
