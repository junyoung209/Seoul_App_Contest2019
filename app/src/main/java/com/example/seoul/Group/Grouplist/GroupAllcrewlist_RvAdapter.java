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

public class GroupAllcrewlist_RvAdapter extends RecyclerView.Adapter<GroupAllcrewlist_RvAdapter.groupAllcrewlistViewHolder> {

    private Context context;
    private ArrayList<GroupData> items;

    public GroupAllcrewlist_RvAdapter(Context context, ArrayList<GroupData> items) {
        this.context = context;
        this.items = items;
    }



    @Override
    public groupAllcrewlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouplist_allcrewlist, null);
        groupAllcrewlistViewHolder viewHolder = new groupAllcrewlistViewHolder(view);
        return viewHolder;
    }

    /**
     * 정보 및 이벤트 처리는 이 메소드에서 구현
     **/

    @Override
    public void onBindViewHolder(groupAllcrewlistViewHolder holder, int position) {

        holder.crewName.setText(items.get(position).getCrewName());
        holder.crewRegion.setText(items.get(position).getCrewRegion());

    }


    public class groupAllcrewlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView crewName;
        TextView crewRegion;

        public groupAllcrewlistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            cv = (CardView) itemView.findViewById(R.id.allcrewlist_Cardview);
            crewName = (TextView) itemView.findViewById(R.id.allcrewlist_crewName);
            crewRegion = (TextView) itemView.findViewById(R.id.allcrewlist_crewRegion);
        }


        @Override
        public void onClick(View view) {

            Log.i("log", "crewHost"+items.get(getPosition()).getCrewHost());

//            myrecord_radio.setVisibility(view.VISIBLE);

            Intent intent = new Intent(view.getContext() , CrewmainActivity.class);

            view.getContext().startActivity(intent);


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}