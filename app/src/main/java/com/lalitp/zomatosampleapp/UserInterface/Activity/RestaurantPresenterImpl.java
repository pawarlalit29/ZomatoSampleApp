package com.lalitp.zomatosampleapp.UserInterface.Activity;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.Utils.AppConstant;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;

import java.util.List;

/**
 * Created by atulsia on 13/1/17.
 */

public class RestaurantPresenterImpl implements RestaurantPresenter, RestaurantInteractor.RestaurantChangeListener {

    private RestaurantView settingView;
    private RestaurantInteractor settingInteractor;

    public RestaurantPresenterImpl(RestaurantView settingView) {
        this.settingView = settingView;
        this.settingInteractor = new RestaurantInteractorImpl();
    }

    @Override
    public void getRestaurantData(List<Restaurant> restaurants) {

    }

    @Override
    public void getRestaurantList(RestaurantParam restaurantParam) {
        if (Common_Utils.isNetworkAvailable()) {

            if(settingView!=null && !restaurantParam.getFrom().equalsIgnoreCase(AppConstant.FROM_ONREFRESH))
                settingView.showProgress();

            settingInteractor.getRestaurantList(restaurantParam,this);
        } else
            settingView.showInternetError();
    }

    @Override
    public void onSuccess(String msg) {
        if(settingView!=null)
            settingView.showSuccess(msg);
    }

    @Override
    public void onError(String msg) {
        if(settingView!=null)
            settingView.showError(msg);
    }
}
