package com.lalitp.zomatosampleapp.UserInterface.Activity;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.NearByRestaurantData;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;
import com.lalitp.zomatosampleapp.Webapi.RestClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantInteractorImpl implements RestaurantInteractor {


    @Override
    public void getRestaurantList(RestaurantParam restaurantParam, final RestaurantChangeListener changeListener) {
        Call<NearByRestaurantData> segmentDataCall = RestClient.getClient().getRestaurantData(getParam(restaurantParam));
        segmentDataCall.enqueue(new Callback<NearByRestaurantData>() {
            @Override
            public void onResponse(Call<NearByRestaurantData> call, Response<NearByRestaurantData> response) {
                if (response.isSuccessful()) {
                    NearByRestaurantData restaurantData = response.body();
                    changeListener.getRestaurantData(restaurantData.getRestaurants());
                } else {
                    changeListener.onError("");
                }
            }

            @Override
            public void onFailure(Call<NearByRestaurantData> call, Throwable t) {
                changeListener.onError("");
            }
        });
    }

    private Map<String,String> getParam(RestaurantParam restaurantParam){
        HashMap<String,String> stringHashMap = new HashMap<>();

        return stringHashMap;
    }


}
