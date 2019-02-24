package com.lalitp.zomatosampleapp.UserInterface.Activity.PlaceSearch;


import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchData;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Webapi.RestClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by atulsia on 15/12/16.
 */

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

}
