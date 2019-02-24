package com.lalitp.zomatosampleapp.UserInterface.Activity.Restaurant;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.Utils.AppConstant;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;

import java.util.List;


public class RestaurantPresenterImpl implements RestaurantPresenter, RestaurantInteractor.RestaurantChangeListener {

    private RestaurantView restaurantView;
    private RestaurantInteractor settingInteractor;

    public RestaurantPresenterImpl(RestaurantView restaurantView) {
        this.restaurantView = restaurantView;
        this.settingInteractor = new RestaurantInteractorImpl();
    }

    @Override
    public void getRestaurantData(List<Restaurant> restaurants,RestaurantParam restaurantParam) {
        if (restaurantView != null)
            restaurantView.getRestaurantData(restaurants,restaurantParam);
    }

    @Override
    public void getRestaurantList(RestaurantParam restaurantParam) {
        if (Common_Utils.isNetworkAvailable()) {

            if (restaurantView != null && restaurantParam.getFrom().equalsIgnoreCase(AppConstant.FROM_ONCREATE))
                restaurantView.showProgress();

            settingInteractor.getRestaurantList(restaurantParam, this);
        } else
            restaurantView.showInternetError();
    }

    @Override
    public void onError(String msg) {
        if (restaurantView != null)
            restaurantView.showError(msg);
    }
}
