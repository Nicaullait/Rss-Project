package com.project.rss.rssproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.rss.rssproject.R;
import com.project.rss.rssproject.model.Deal;

import java.util.ArrayList;

/**
 * Created by nico_ on 12/03/2018.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    public interface ItemClickListener {
        public void onClick(Deal deal);

    }

    ArrayList<Deal> deals;
    ItemClickListener listener;

    public MainRecyclerViewAdapter(ArrayList<Deal> deals, ItemClickListener listener) {
        this.deals = deals;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Deal deal = deals.get(position);
        holder.display(deal, listener);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private ItemClickListener listener;


        private Deal currentDeal;

        public MyViewHolder(final View itemView) {
            super(itemView);

            title = ((TextView) itemView.findViewById(R.id.title));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   listener.onClick(currentDeal);
                }
            });
        }

        public void display(Deal deal, ItemClickListener listener) {
            currentDeal = deal;
            title.setText(deal.getTitle());
            this.listener = listener;
        }
    }


}