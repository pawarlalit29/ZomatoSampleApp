package com.lalitp.zomatosampleapp.UserInterface.Activity.PlaceSearch;



import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;

import java.util.List;

/**
 * Created by atulsia on 15/12/16.
 */

public interface PlaceSearchInteractor {
    interface LocationDetailChangeListener{
        void getPlacePredication(List<Prediction> predictionList);
        void onError(String strMsg);
    }

    void getPlacePredicationData(PlaceSearchParam placeSearchParam, LocationDetailChangeListener changeListener);
}
