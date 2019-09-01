package com.example.seoul.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seoul.R;

import java.util.List;

public class Mycrewschedule_RvAdapter extends RecyclerView.Adapter<Mycrewschedule_RvAdapter.Cus_ViewHolder> {

    private Context context;
    private List<Integer> items;
    private int i;

    public Mycrewschedule_RvAdapter(Context context, List<Integer> items, int i) {
        this.context = context;
        this.items = items;
        this.i = i;
    }
    @Override
    public Cus_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouplist_myschedule,null);
        Cus_ViewHolder viewHolder = new Cus_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Cus_ViewHolder holder, int position) {
        final Integer item = items.get(position);
        holder.title.setText("Sadang coco Crew");
        holder.place.setText("Seoul City, SadangDong");
        holder.time.setText("오후"+item+":00");
    }

    public class Cus_ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView place;
        TextView time;

        public Cus_ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_cardview);
            title = (TextView)itemView.findViewById(R.id.Schedule_title);
            place = (TextView)itemView.findViewById(R.id.Schedule_place);
            time = (TextView)itemView.findViewById(R.id.Schedule_time);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}