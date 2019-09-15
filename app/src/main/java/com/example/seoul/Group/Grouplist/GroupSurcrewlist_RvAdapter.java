package com.example.seoul.Group.Grouplist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seoul.Group.GroupData;
import com.example.seoul.Group.Mycrewlist.CrewmainActivity;
import com.example.seoul.R;

import java.util.ArrayList;

public class GroupSurcrewlist_RvAdapter extends RecyclerView.Adapter<GroupSurcrewlist_RvAdapter.groupSurcrewlistViewHolder> {

    private Context context;
    private ArrayList<GroupData> items;


    public GroupSurcrewlist_RvAdapter(Context context, ArrayList<GroupData> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public groupSurcrewlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouplist_surcrewlist,null);
        groupSurcrewlistViewHolder viewHolder = new groupSurcrewlistViewHolder(view);
        return viewHolder;
    }

    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/

    @Override
    public void onBindViewHolder(groupSurcrewlistViewHolder holder, int position) {
        holder.crewName.setText(items.get(position).getCrewName());
        holder.crewRegion.setText(items.get(position).getCrewRegion());
    }

    public int getPosition(){
        return getPosition();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class groupSurcrewlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView crewName;
        TextView crewRegion;


        public groupSurcrewlistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.surcrewlist_Cardview);
            crewName = (TextView)itemView.findViewById(R.id.surcrewlist_crewName);
            crewRegion = (TextView)itemView.findViewById(R.id.surcrewlist_crewRegion);

        }

//
//        public void setRadio(View itemView)
//        {
//            myrecord_radio.setVisibility(itemView.);
//        }


        @Override
        public void onClick(View view) {

            Log.i("log", "crewHost"+items.get(getPosition()).getCrewHost());

//            myrecord_radio.setVisibility(view.VISIBLE);

            Intent intent = new Intent(view.getContext() , CrewmainActivity.class);

            view.getContext().startActivity(intent);


        }
    }
}