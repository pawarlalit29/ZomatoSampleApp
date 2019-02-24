package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;


import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;

import java.util.List;


public class PlaceSearchPresenterImpl implements PlaceSearchPresenter, PlaceSearchInteractor.LocationDetailChangeListener {

    private PlaceSearchView placeSearchView;
    private PlaceSearchInteractor placeSearchInteractor;

    public PlaceSearchPresenterImpl(PlaceSearchView locationDetailView) {
        this.placeSearchView = locationDetailView;
        this.placeSearchInteractor = new PlaceSearchInteractorImpl();
    }

    @Override
    public void getPlacePredication(PlaceSearchParam placeSearchParam) {
        if (placeSearchView != null)
            placeSearchView.showProgress();

        placeSearchInteractor.getPlacePredicationData(placeSearchParam, this);
    }

    @Override
    public void getLocation(LatLng latLng) {
        if (placeSearchView != null)
            placeSearchView.showProgress();

        placeSearchInteractor.getPlaceLocation(latLng, this);
    }

    @Override
    public void getPlacePredication(List<Prediction> predictionList) {
        if (placeSearchView != null)
            placeSearchView.placePredication(predictionList);
    }

    @Override
    public void getLocation(com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location location) {
        if (placeSearchView != null)
            placeSearchView.locationDetail(location);
    }

    @Override
    public void onError(String strMsg) {
        if (placeSearchView != null)
            placeSearchView.showError(strMsg);
    }
}
