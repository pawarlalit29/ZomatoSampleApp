package com.lalitp.zomatosampleapp.UserInterface.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Widget.ProgressLoader;

import java.util.List;

import butterknife.ButterKnife;


public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Restaurant> mData;
    private OnItemClickListener mItemClickListener;
    private int lastPosition = -1;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public RestaurantListAdapter(List<Restaurant> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      /*  View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.custom_restaurant_list, viewGroup, false);
        return new ViewHolder(view);*/

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_restaurant_list, viewGroup, false);
            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.custom_progress_item, viewGroup, false);
            vh = new ProgressLoader.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = ((ViewHolder) holder);

            Restaurant restaurant = mData.get(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
}

    public void setOnItemClickListener(
            final OnItemClickListener mitemClickListener) {
        this.mItemClickListener = mitemClickListener;
    }

    public void showProgress() {
        mData.add(null);
    }

    public void hideProgress() {
        if (mData != null && !mData.isEmpty()) {
            int lastPos = mData.size() - 1;
            if (mData.get(lastPos) == null) {
                mData.remove(lastPos);
                notifyItemRemoved(mData.size());
            }
        }
    }
}
