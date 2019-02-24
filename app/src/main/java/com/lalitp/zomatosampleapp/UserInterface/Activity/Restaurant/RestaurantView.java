package com.lalitp.zomatosampleapp.UserInterface.Activity.Restaurant;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;

import java.util.List;



public interface RestaurantView {
    void showProgress();
    void getRestaurantData(List<Restaurant> restaurants, RestaurantParam restaurantParam);
    void showError(String msg);
    void showInternetError();
}
