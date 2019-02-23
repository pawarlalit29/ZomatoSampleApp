package com.lalitp.zomatosampleapp.UserInterface.Activity;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;

import java.util.List;

/**
 * Created by atulsia on 13/1/17.
 */

public interface RestaurantView {
    void showProgress();
    void getRestaurantData(List<Restaurant> restaurants);
    void showError(String msg);
    void showSuccess(String msg);
    void showInternetError();
}
