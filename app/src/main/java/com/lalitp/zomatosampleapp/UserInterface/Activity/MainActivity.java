package com.lalitp.zomatosampleapp.UserInterface.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch.PlaceSearchFragment;
import com.lalitp.zomatosampleapp.UserInterface.Fragment.Restaurant.RestaurantFragment;
import com.lalitp.zomatosampleapp.Utils.AppConstant;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;
import com.lalitp.zomatosampleapp.Utils.SecurePreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contentContainer)
    FrameLayout frameLayout;

    private SecurePreferences securePreferences;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setFrameLayout(RestaurantFragment.getInstance());
    }

    public void setFrameLayout(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.contentContainer, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    public Location getStoreLocation() {
        Location location = new Location();
        securePreferences = SecurePreferences.getSecurePreferences();
        String strLocation = securePreferences.getString(AppConstant.store_location);

        if (Common_Utils.isNotNullOrEmpty(strLocation)) {
            Gson gson = new Gson();
            location = gson.fromJson(strLocation, Location.class);
        }

        return location;
    }

    public void setStoreLocation(Location location) {
        securePreferences = SecurePreferences.getSecurePreferences();
        if (location != null) {
            Gson gson = new Gson();
            String strLocation = gson.toJson(location);
            securePreferences.put(AppConstant.store_location, strLocation);
        }

        popBackStack();

    }

    public void popBackStack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            getSupportFragmentManager().popBackStack(PlaceSearchFragment.class.getName(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1)
            appExit();
    }

    private void appExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Common_Utils.showToast("Please click BACK again to exit");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
