package com.lalitp.zomatosampleapp.UserInterface.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Widget.RoundishImageView;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Restaurant> mData;
    private OnItemClickListener mItemClickListener;
    private Random randomGenerator;
    private List<String> defaultImageList;

    public RestaurantListAdapter(List<Restaurant> data) {
        mData = data;
        randomGenerator = new Random();
        defaultImageList = new ArrayList<>();

        defaultImageList.add("https://cdn.zeebiz.com/hindi/sites/default/files/styles/zeebiz_850x478/public/2019/02/23/8057-restaurants.jpg?itok=qBb-KSwM");
        defaultImageList.add("https://file.videopolis.com/D/9dc9f4ba-0b2d-4cbb-979f-fee7be8a4198/8485.11521.brussels.the-hotel-brussels.amenity.restaurant-AD3WAP2L-13000-853x480.jpeg");
        defaultImageList.add("https://www.omnihotels.com/-/media/images/hotels/bospar/restaurants/bospar-omni-parker-house-parkers-restaurant-1170.jpg");
        defaultImageList.add("https://clbrestaurants.com/portals/2/images/our-restaurants.jpg");
        defaultImageList.add("https://www.digitaltrends.com/wp-content/uploads/2012/08/restaurant.jpeg");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.custom_restaurant_list, viewGroup, false);
        return new ViewHolder(view);
}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = ((ViewHolder) holder);

            Restaurant restaurant = mData.get(position);
            String name = restaurant.getRestaurant().getName();
            String locality = restaurant.getRestaurant().getLocation().getLocality();
            String thumb = restaurant.getRestaurant().getThumb();

            String rating = restaurant.getRestaurant().getUserRating().getAggregateRating();
            String review = restaurant.getRestaurant().getUserRating().getVotes();
            String currency = restaurant.getRestaurant().getCurrency();
            String forTwo = restaurant.getRestaurant().getAverageCostForTwo();

            String cost_for_two = currency +" " + forTwo + " for two.";

            review = review + " Reviews";

            viewHolder.txtName.setText(name);
            viewHolder.txtDisp.setText(cost_for_two);
            viewHolder.ratingBar.setRating(Float.parseFloat(rating));
            viewHolder.txtReview.setText(review);

            if (!Common_Utils.isNotNullOrEmpty(thumb)) {
                int index = randomGenerator.nextInt(defaultImageList.size());
                thumb = defaultImageList.get(index);
            }

            Picasso.get()
                    .load(thumb)
                    .into(viewHolder.imgRestaurant);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_restaurant)
        RoundishImageView imgRestaurant;
        @BindView(R.id.txt_new)
        TextView txtDiscount;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_disp)
        TextView txtDisp;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.txt_review)
        TextView txtReview;

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


}
