package com.lalitp.zomatosampleapp.UserInterface.Fragment.Restaurant;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;

import java.util.List;


public interface RestaurantInteractor {
    interface RestaurantChangeListener {
        void getRestaurantData(List<Restaurant> restaurants,RestaurantParam restaurantParam);
        void onError(String msg);
    }

    void getRestaurantList(RestaurantParam settingsParam, RestaurantChangeListener changeListener);
}
