package com.lalitp.zomatosampleapp.UserInterface.Fragment.PlaceSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.PlaceSearchParam;
import com.lalitp.zomatosampleapp.Pojo.PlaceSearch.Prediction;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Activity.MainActivity;
import com.lalitp.zomatosampleapp.UserInterface.Adaptor.PlaceAutoCompleteAdapter;
import com.lalitp.zomatosampleapp.Utils.AppConstant;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permission.auron.com.marshmallowpermissionhelper.FragmentManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class PlaceSearchFragment extends FragmentManagePermission implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PlaceSearchView, PlaceAutoCompleteAdapter.ItemClickListner {


    @BindView(R.id.et_search_place)
    AppCompatEditText etSearchPlace;
    @BindView(R.id.btn_cancel_searchBar)
    ImageView btnCancelSearchBar;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Context context;
    private static String TAG = PlaceSearchFragment.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;


    public static int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 20;
    private static final int MY_PERMISSIONS_REQUEST_LOC = 30;
    double latitude;
    double longitude;
    private PlaceSearchPresenter placeSearchPresenter;
    private List<Prediction> placeAutoCompletes;
    private Timer timer = new Timer();
    private final long DELAY = 500; // milliseconds
    private PlaceAutoCompleteAdapter placeAutoCompleteAdapter;
    private static final int REQUEST_CHECK_SETTINGS = 1000;


    public static PlaceSearchFragment getInstance(){
        PlaceSearchFragment placeSearchFragment = new PlaceSearchFragment();
        return placeSearchFragment;
    }

    public PlaceSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
            }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.frag_place_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    //call default method to initialized variables
    private void init() {

        placeAutoCompletes = new ArrayList<>();
        placeSearchPresenter = new PlaceSearchPresenterImpl(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvResult.setLayoutManager(layoutManager);
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvResult.setItemAnimator(new DefaultItemAnimator());

        placeAutoCompleteAdapter = new PlaceAutoCompleteAdapter(getActivity(), placeAutoCompletes);
        rvResult.setAdapter(placeAutoCompleteAdapter);
        placeAutoCompleteAdapter.setOnItemClickListener(this);

        fetchLocation();

        etSearchPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearchPlace.getText().toString().equalsIgnoreCase("")) {
                    btnCancelSearchBar.setVisibility(View.GONE);
                } else {
                    btnCancelSearchBar.setVisibility(View.VISIBLE);
                }

                if (s.length() > 2) {
                    waitForActionDone(s.toString());
                } else
                    clearResult();
                //countryCodeAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.img_location)
    public void onClickCurrentLocation(){
        getLocationPermissionInfo();
    }

    @Override
    public void onItemclicked(int pos) {
        if (mGoogleApiClient == null)
            return;

        Prediction placeAutoComplete = placeAutoCompletes.get(pos);
        String placeId = placeAutoComplete.getPlaceId();

        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            Log.v("Latitude is", "" + queriedLocation.latitude);
                            Log.v("Longitude is", "" + queriedLocation.longitude);

                            com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location location = new com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location();

                            location.setLatitude(String.valueOf(myPlace.getLatLng().latitude));
                            location.setLongitude(String.valueOf(myPlace.getLatLng().longitude));
                            location.setTitle(myPlace.getAddress().toString());

                            ((MainActivity)getActivity()).setStoreLocation(location);

                        }
                        places.release();

                    }
                });
    }


    @OnClick(R.id.btn_cancel_searchBar)
    public void onCancelBtnSearchbarClick() {
        etSearchPlace.setText(null);
        clearResult();
    }

    public void fetchLocation() {
        //Build google API client to use fused location
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    // initialized google api clint
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void placePredication(List<Prediction> predictionList) {
        progressBar.setVisibility(View.INVISIBLE);
        if (placeAutoCompletes != null && !placeAutoCompletes.isEmpty())
            placeAutoCompletes.clear();

        placeAutoCompletes.addAll(predictionList);
        placeAutoCompleteAdapter.notifyDataSetChanged();

    }

    @Override
    public void locationDetail(com.lalitp.zomatosampleapp.Pojo.PlaceLocation.Location location) {

        if(location!=null){
            ((MainActivity)getActivity()).setStoreLocation(location);
        }

    }

    @Override
    public void showError(String msg) {
        progressBar.setVisibility(View.INVISIBLE);
    }


    // wait call till continues  entering character
    private void waitForActionDone(final String query) {
        System.out.println("waitForAction waiting");
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                placeAutoCompleteAdapter.setSearchQuery(query);
                                placeSearchPresenter.getPlacePredication(new PlaceSearchParam(query, latitude, longitude));
                            }
                        });
                    }
                },
                DELAY
        );
    }

    private void clearResult() {
        if (placeAutoCompletes != null && !placeAutoCompletes.isEmpty())
            placeAutoCompletes.clear();

        placeAutoCompleteAdapter.notifyDataSetChanged();
    }

    public void getLocationPermissionInfo() {
        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(AppConstant.no_internet_connection);
            return;
        }

        askCompactPermissions(new String[]{PermissionUtils.Manifest_ACCESS_COARSE_LOCATION
                , PermissionUtils.Manifest_ACCESS_FINE_LOCATION}, new PermissionResult() {

            @Override
            public void permissionGranted() {
                settingLocationRequest();
            }

            @Override
            public void permissionDenied() {

            }

            @Override
            public void permissionForeverDenied() {

            }

        });

    }

    /*Method to get the enable location settings dialog*/
    @SuppressLint("RestrictedApi")
    public void settingLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            //status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                            startIntentSenderForResult(status.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //settingLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Common_Utils.showToast("Connection Suspended!");
    }

    /*Ending the updates for the location service*/
    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();
                return;
            }

            for (Location location : locationResult.getLocations()) {
                if (location != null) {

                    mLastLocation = location;
                    Log.i(TAG, "Inside getLocation function.");
                    Double currentLongitude = mLastLocation.getLongitude();
                    Double currentLatitude = mLastLocation.getLatitude();

                    LatLng latLng = new LatLng(currentLatitude, currentLongitude);

                    placeSearchPresenter.getLocation(latLng);
                    mFusedLocationClient.removeLocationUpdates(locationCallback);

                } else {
                    Log.i(TAG, "Inside getLocation function. Error while getting location");
                    //System.out.println((TAG + task.getException());
                    if (!mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect();
                }
            }
        }
    };


}
