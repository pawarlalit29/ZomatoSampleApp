package com.lalitp.zomatosampleapp.UserInterface.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commitAllowingStateLoss();
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

        setFrameLayout(RestaurantFragment.getInstance());

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
        FragmentManager frag_manager = getSupportFragmentManager();
        if (frag_manager.getBackStackEntryCount() > 0) {
            frag_manager.popBackStack();
        } else {
            backToMainScreen();
        }
    }

    private void appExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Common_Utils.showToast(AppConstant.app_exit_message);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void backToMainScreen() {
        if (getValidFragScreen(RestaurantFragment.getInstance())) {
            setFrameLayout(RestaurantFragment.getInstance());
        } else {
            appExit();
        }

    }

    public boolean getValidFragScreen(Fragment fragment) {

        FragmentManager frag_manager = getSupportFragmentManager();
        if (frag_manager.getBackStackEntryCount() != 0) {
            frag_manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        Fragment fragment1 = frag_manager.findFragmentById(R.id.contentContainer);

        if (fragment1 == null)
            return false;

        String strFragmentName = fragment.getClass().getSimpleName();

        if (!strFragmentName
                .equalsIgnoreCase(fragment1.getClass().getSimpleName())) {
            return true;
        } else {
            return false;
        }
    }
}
