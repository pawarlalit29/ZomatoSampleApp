package com.lalitp.zomatosampleapp.Webapi;


import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.NearByRestaurantData;
import com.lalitp.zomatosampleapp.Pojo.PlaceLocation.PlaceLocationData;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchData;


import java.util.HashMap;
import java.util.Map;

import dimitrovskif.smartcache.SmartCall;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface RestInterface {

    @Headers("user-key:4feaa2167c4dc6beadf629319423bd4b")
    @GET("search")
    SmartCall<NearByRestaurantData> getRestaurantData(@QueryMap Map<String, String> queryMap);

    @Headers("user-key:4feaa2167c4dc6beadf629319423bd4b")
    @GET("geocode")
    Call<PlaceLocationData> getLocationData(@QueryMap Map<String, Double> queryMap);


    @GET("place/autocomplete/json")
    Call<PlaceSearchData> getPlacePredicationCall(@QueryMap HashMap<String, String> queryMap);
}
