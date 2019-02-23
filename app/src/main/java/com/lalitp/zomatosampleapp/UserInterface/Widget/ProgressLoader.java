package com.lalitp.zomatosampleapp.UserInterface.Widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.lalitp.zomatosampleapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressLoader {
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }

}
