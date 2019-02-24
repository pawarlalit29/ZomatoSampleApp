package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;


import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;


public interface PlaceSearchPresenter {

    void getPlacePredication(PlaceSearchParam placeSearchParam);
    void getLocation(LatLng latLng);
}
