package com.example.seoul.Single.Myrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seoul.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecordAdapter extends RecyclerView.Adapter<MyRecordAdapter.recordViewHolder> {

    private Context context;
    private ArrayList<Myrecord> items;
    private int i;

    public MyRecordAdapter(Context context, ArrayList<Myrecord> items) {
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
        holder.date.setText(items.get(position).getDate());
    }



    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/



    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class recordViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView index;
        TextView time;
        TextView date;

        public recordViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.myrecord_Cardview);
            index = (TextView)itemView.findViewById(R.id.myrecord_index);
            time = (TextView)itemView.findViewById(R.id.myrecord_time);
            date = (TextView)itemView.findViewById(R.id.myrecord_Date);
        }


    }
}