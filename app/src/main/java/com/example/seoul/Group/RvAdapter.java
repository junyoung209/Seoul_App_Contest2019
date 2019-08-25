package com.example.seoul.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seoul.R;

import java.util.ArrayList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplist_mycrew_list,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

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
            cv = itemView.findViewById(R.id.item_cardview);
            title = itemView.findViewById(R.id.item_tv_title);
            content = itemView.findViewById(R.id.item_tv_content);
        }
    }
}