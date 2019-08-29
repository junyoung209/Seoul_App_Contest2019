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

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CustomViewHolder> {

    private Context context;
    private List<Integer> items;
    private int i;

    public RvAdapter(Context context, List<Integer> items, int i) {
        this.context = context;
        this.items = items;
        this.i = i;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplist_crew_list,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Integer item = items.get(position);
        holder.title.setText(item + "");
        holder.content.setText(item + "content");
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView content;

        public CustomViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_cardview);
            title = (TextView)itemView.findViewById(R.id.item_tv_title);
            content = (TextView)itemView.findViewById(R.id.item_tv_content);
        }
    }
}