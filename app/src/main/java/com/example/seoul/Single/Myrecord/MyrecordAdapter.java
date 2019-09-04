package com.example.seoul.Single.Myrecord;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seoul.R;
import com.example.seoul.Single.Run.Runrecord;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MyrecordAdapter extends RecyclerView.Adapter<MyrecordAdapter.recordViewHolder> {

    private Context context;
    private ArrayList<Myrecord> items;


    public MyrecordAdapter(Context context, ArrayList<Myrecord> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public recordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myrecord_list,null);
        recordViewHolder viewHolder = new recordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recordViewHolder holder, int position) {

        holder.index.setText(Integer.toString(items.get(position).getIdx()));
        holder.time.setText(items.get(position).getRunTime());
        holder.date.setText(items.get(position).getRunDate());

    }

    public int getPosition(){
        return getPosition();
    }




    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/



    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class recordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView index;
        TextView time;
        TextView date;

        public recordViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.myrecord_Cardview);
            index = (TextView)itemView.findViewById(R.id.myrecord_index);
            time = (TextView)itemView.findViewById(R.id.myrecord_time);
            date = (TextView)itemView.findViewById(R.id.myrecord_Date);
        }

//
//        public void setRadio(View itemView)
//        {
//            myrecord_radio.setVisibility(itemView.);
//        }


        @Override
        public void onClick(View view) {
            Log.i("logposition",Integer.toString(getPosition()));
            Log.i("logpos", items.get(getPosition()).getRunTime());

//            myrecord_radio.setVisibility(view.VISIBLE);

            Intent intent = new Intent(view.getContext() , MyrecordResult.class);


            Runrecord runRecord=new Runrecord(items.get(getPosition()).getUserID(), items.get(getPosition()).getRunTime(),items.get(getPosition()).getRunDistance(),items.get(getPosition()).getRunTime(),items.get(getPosition()).getRunDate());

            ArrayList<LatLng> runCordlist=items.get(getPosition()).getCord();
            intent.putExtra("runCord",runCordlist);
            intent.putExtra("runRecord",runRecord);
            intent.putExtra("idx",items.get(getPosition()).getIdx());




            view.getContext().startActivity(intent);


        }
    }
}