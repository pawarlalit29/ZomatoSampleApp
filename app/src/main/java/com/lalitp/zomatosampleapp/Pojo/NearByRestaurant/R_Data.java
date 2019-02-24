
package com.lalitp.zomatosampleapp.Pojo.NearByRestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class R_Data {

    @SerializedName("res_id")
    @Expose
    private String resId;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

}
