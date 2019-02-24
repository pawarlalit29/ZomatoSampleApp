package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;


import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceLocation.PlaceLocationData;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchData;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Webapi.RestClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceSearchInteractorImpl implements PlaceSearchInteractor {

    @Override
    public void getPlacePredicationData(PlaceSearchParam placeSearchParam, final LocationDetailChangeListener changeListener) {
        Call<PlaceSearchData> routeDataCall = RestClient.getMapClient().getPlacePredicationCall(getPredicationParam(placeSearchParam));
        routeDataCall.enqueue(new Callback<PlaceSearchData>() {
            @Override
            public void onResponse(Call<PlaceSearchData> call, Response<PlaceSearchData> response) {
                if (response.isSuccessful()) {
                    PlaceSearchData placePredictions = response.body();
                    changeListener.getPlacePredication(placePredictions.getPredictions());

                } else {
                    changeListener.onError("");
                }
            }

            @Override
            public void onFailure(Call<PlaceSearchData> call, Throwable t) {
                changeListener.onError(RestClient.parseErrorThrow(t));
            }
        });
    }


    @Override
    public void getPlaceLocation(LatLng latLng, final LocationDetailChangeListener changeListener) {
        Call<PlaceLocationData> routeDataCall = RestClient.getClient().getLocationData(getLocation(latLng));
        routeDataCall.enqueue(new Callback<PlaceLocationData>() {
            @Override
            public void onResponse(Call<PlaceLocationData> call, Response<PlaceLocationData> response) {
                if (response.isSuccessful()) {
                    PlaceLocationData placePredictions = response.body();
                    changeListener.getLocation(placePredictions.getLocation());

                } else {
                    changeListener.onError("");
                }
            }

            @Override
            public void onFailure(Call<PlaceLocationData> call, Throwable t) {
                changeListener.onError(RestClient.parseErrorThrow(t));
            }
        });
    }

    // create parameters for
    private HashMap<String, String> getPredicationParam(PlaceSearchParam placeSearchParam) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("input", placeSearchParam.getQuery());
        hashMap.put("location", placeSearchParam.getLatitude() + "," + placeSearchParam.getLongitude());
        hashMap.put("radius", "500");
        hashMap.put("language", "en");
        hashMap.put("key", RestClient.GOOGLE_KEY);

        return hashMap;
    }

    private HashMap<String,Double> getLocation(LatLng latLng){
        HashMap<String,Double> stringHashMap = new HashMap<>();
        stringHashMap.put("lat",latLng.latitude);
        stringHashMap.put("lon",latLng.longitude);

        return stringHashMap;
    }
}
