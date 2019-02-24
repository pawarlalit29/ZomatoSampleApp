package com.lalitp.zomatosampleapp.UserInterface.Fragment.Restaurant;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.NearByRestaurantData;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.Utils.AppConstant;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;
import com.lalitp.zomatosampleapp.Webapi.RestClient;

import java.util.HashMap;
import java.util.Map;

import dimitrovskif.smartcache.SmartCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantInteractorImpl implements RestaurantInteractor {


    @Override
    public void getRestaurantList(final RestaurantParam restaurantParam, final RestaurantChangeListener changeListener) {
        SmartCall<NearByRestaurantData> segmentDataCall = RestClient.getClient().getRestaurantData(getParam(restaurantParam));
        segmentDataCall.enqueue(new Callback<NearByRestaurantData>() {
            @Override
            public void onResponse(Call<NearByRestaurantData> call, Response<NearByRestaurantData> response) {
                if (response.isSuccessful()) {
                    NearByRestaurantData restaurantData = response.body();
                    if (restaurantData.getRestaurants() != null &&
                            !restaurantData.getRestaurants().isEmpty()) {
                        changeListener.getRestaurantData(restaurantData.getRestaurants(), restaurantParam);
                    } else if (restaurantParam.getCount() > 0) {
                        changeListener.onError(AppConstant.no_more_restaurant_found);
                    } else {
                        changeListener.onError(AppConstant.no_restaurant_found);
                    }

                } else {
                    changeListener.onError(AppConstant.no_restaurant_found);
                }
            }

            @Override
            public void onFailure(Call<NearByRestaurantData> call, Throwable t) {
                System.out.println(t.getMessage());
                changeListener.onError(AppConstant.no_restaurant_found);
            }
        });
    }

    private Map<String, String> getParam(RestaurantParam restaurantParam) {
        HashMap<String, String> stringHashMap = new HashMap<>();

        if(restaurantParam.getLatitude()==0.0 &&
        restaurantParam.getLongitude() == 0.0){
            stringHashMap.put("entity_id", "3");
            stringHashMap.put("entity_type", "city");
        }else {
            stringHashMap.put("lat", String.valueOf(restaurantParam.getLatitude()));
            stringHashMap.put("lon", String.valueOf(restaurantParam.getLongitude()));
            stringHashMap.put("radius", "5000");
        }



        if (Common_Utils.isNotNullOrEmpty(restaurantParam.getQuery()))
            stringHashMap.put("q", restaurantParam.getQuery());

        stringHashMap.put("start", String.valueOf(restaurantParam.getStart()));
        stringHashMap.put("count", String.valueOf(restaurantParam.getCount()));

        return stringHashMap;
    }


}
