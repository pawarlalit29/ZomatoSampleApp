package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;



import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;

import java.util.List;


public interface PlaceSearchInteractor {
    interface LocationDetailChangeListener{
        void getPlacePredication(List<Prediction> predictionList);
        void getLocation(com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location location);
        void onError(String strMsg);
    }

    void getPlacePredicationData(PlaceSearchParam placeSearchParam, LocationDetailChangeListener changeListener);
    void getPlaceLocation(LatLng latLng, LocationDetailChangeListener changeListener);
}
