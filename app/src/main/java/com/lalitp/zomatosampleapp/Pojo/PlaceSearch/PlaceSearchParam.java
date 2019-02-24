package com.lalitp.zomatosampleapp.Pojo.PlaceSearch;

/**
 * Created by atulsia on 28/11/18.
 */

public class PlaceSearchParam {
    String query;
    double latitude;
    double longitude;

    public PlaceSearchParam(String query, double latitude, double longitude) {
        this.query = query;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
