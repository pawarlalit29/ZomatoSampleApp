
package com.lalitp.zomatosampleapp.Pojo.NearByRestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareData {

    @SerializedName("should_show")
    @Expose
    private String shouldShow;

    public String getShouldShow() {
        return shouldShow;
    }

    public void setShouldShow(String shouldShow) {
        this.shouldShow = shouldShow;
    }

}
