package com.lalitp.zomatosampleapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.lalitp.zomatosampleapp.ZomatoSampleApp;

public class Common_Utils {

    public static Toast toast;
    public static boolean isNotNullOrEmpty(String str) {

        if (str != null
                && !str.equalsIgnoreCase("null")
                && !str.isEmpty()
                //&& !str.contains("null")
                && !str.equalsIgnoreCase("")
                && !str.equalsIgnoreCase(" ")) {

            return true;
        } else {
            return false;
        }
    }

    public static void showToast(String data) {

        try {
            if (!Common_Utils.isNotNullOrEmpty(data))
                return;

            if (toast == null) {
                toast = Toast.makeText(ZomatoSampleApp.getAppContext(), data, Toast.LENGTH_SHORT);
            }

            if (!toast.getView().isShown()) {
                toast.setText(data);
                toast.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) ZomatoSampleApp.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String getTimeStamp(long timestamp) {
        try {

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    timestamp,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            return timeAgo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
