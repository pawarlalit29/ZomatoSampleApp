package com.lalitp.zomatosampleapp.Webapi;


import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.NearByRestaurantData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


/**
 * Created by atulsia on 19/2/16.
 */
public interface RestInterface {


    @GET("search")
    Call<NearByRestaurantData> getRestaurantData(@HeaderMap Map<String, String> headers);


}
