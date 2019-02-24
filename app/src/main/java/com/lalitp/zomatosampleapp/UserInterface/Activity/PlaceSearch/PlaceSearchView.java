package com.lalitp.zomatosampleapp.UserInterface.Activity.PlaceSearch;




import com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;

import java.util.List;

/**
 * Created by atulsia on 15/12/16.
 */

public interface PlaceSearchView {
    void showProgress();
    void placePredication(List<Prediction> predictionList);
    void locationDetail(Location location);
    void showError(String msg);
}
