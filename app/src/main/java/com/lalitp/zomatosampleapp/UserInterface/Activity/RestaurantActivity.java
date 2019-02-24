package com.lalitp.zomatosampleapp.UserInterface.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Adaptor.RestaurantListAdapter;
import com.lalitp.zomatosampleapp.Utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements RestaurantView {

    private List<Restaurant> restaurantList;
    private RestaurantListAdapter restaurantListAdapter;
    private RestaurantPresenter restaurantPresenter;
    private double lat=0.0,lon = 0.0;
    private int offset = 0 , limit = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        init();
    }

    private void init(){

        restaurantList = new ArrayList<>();
        restaurantPresenter = new RestaurantPresenterImpl(this);

        restaurantListAdapter = new RestaurantListAdapter(restaurantList);
        /*recycleview.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(productListAdapter);*/

        restaurantFetchCall("", AppConstant.FROM_ONCREATE);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void getRestaurantData(List<Restaurant> restaurants) {

    /*    if(restaurantList!=null && !restaurantList.isEmpty()){
            restaurantList.clear();
        }

        restaurantList.addAll(restaurants);
        restaurantListAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showSuccess(String msg) {

    }

    @Override
    public void showInternetError() {

    }

    private void restaurantFetchCall(String query,String from){
        RestaurantParam restaurantParam = new RestaurantParam();
        restaurantParam.setFrom(from);
        restaurantParam.setQuery(query);
        restaurantParam.setStart(offset);
        restaurantParam.setCount(limit);

        restaurantPresenter.getRestaurantList(restaurantParam);
    }
}
