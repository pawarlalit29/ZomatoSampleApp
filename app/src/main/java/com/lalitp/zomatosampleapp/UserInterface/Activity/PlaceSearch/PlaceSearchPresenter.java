package com.lalitp.zomatosampleapp.UserInterface.Activity.PlaceSearch;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;

/**
 * Created by atulsia on 15/12/16.
 */

public interface PlaceSearchPresenter {

    void getPlacePredication(PlaceSearchParam placeSearchParam);
    void getLocation(LatLng latLng);
}
