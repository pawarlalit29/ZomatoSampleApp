package com.lalitp.zomatosampleapp.UserInterface.Activity;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;

import java.util.List;


public interface RestaurantInteractor {
    interface RestaurantChangeListener {
        void getRestaurantData(List<Restaurant> restaurants);
        void onError(String msg);
        void onSuccess(String msg);
    }

    void getRestaurantList(RestaurantParam settingsParam, RestaurantChangeListener changeListener);
}
