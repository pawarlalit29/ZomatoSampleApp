package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;




import com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;

import java.util.List;


public interface PlaceSearchView {
    void showProgress();
    void placePredication(List<Prediction> predictionList);
    void locationDetail(Location location);
    void showError(String msg);
}
