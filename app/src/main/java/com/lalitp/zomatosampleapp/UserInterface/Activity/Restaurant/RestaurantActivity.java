package com.lalitp.zomatosampleapp.UserInterface.Activity.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lalitp.zomatosampleapp.Pojo.NearByRestaurant.Restaurant;
import com.lalitp.zomatosampleapp.Pojo.RestaurantParam;
import com.lalitp.zomatosampleapp.R;
import com.lalitp.zomatosampleapp.UserInterface.Activity.PlaceSearch.PlaceSearchActivity;
import com.lalitp.zomatosampleapp.UserInterface.Adaptor.RestaurantListAdapter;
import com.lalitp.zomatosampleapp.UserInterface.Widget.EmptyView.ProgressLinearLayout;
import com.lalitp.zomatosampleapp.UserInterface.Widget.EndlessRecyclerViewScrollListener;
import com.lalitp.zomatosampleapp.Utils.AppConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantActivity extends AppCompatActivity implements RestaurantView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.simpleGrid)
    RecyclerView simpleGrid;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_empty)
    ProgressLinearLayout progressEmpty;
    @BindString(R.string.retry)
    String strRetry;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.btn_cancel_searchBar)
    ImageView btnCancelSearchBar;

    private List<Restaurant> restaurantList;
    private RestaurantListAdapter restaurantListAdapter;
    private RestaurantPresenter restaurantPresenter;
    private int offset = 0, limit = 15;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private final long DELAY = 500; // milliseconds
    private Timer timer = new Timer();
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        restaurantList = new ArrayList<>();
        restaurantPresenter = new RestaurantPresenterImpl(this);

        restaurantListAdapter = new RestaurantListAdapter(restaurantList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        simpleGrid.setLayoutManager(staggeredGridLayoutManager);
        simpleGrid.setAdapter(restaurantListAdapter);
        simpleGrid.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                restaurantFetchCall(AppConstant.FROM_ONLODEMORE);
            }
        });

        restaurantFetchCall(AppConstant.FROM_ONCREATE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().toString().equalsIgnoreCase("")) {
                    btnCancelSearchBar.setVisibility(View.GONE);
                } else {
                    btnCancelSearchBar.setVisibility(View.VISIBLE);
                }

                waitForActionDone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.txt_change)
    public void onClickChangeLocation(){
        Intent intent = new Intent(this, PlaceSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cancel_searchBar)
    public void onCancelBtnSearchbarClick() {
        edtSearch.setText(null);
        searchQuery = "";
        restaurantFetchCall(AppConstant.FROM_ONCREATE);
    }

    @Override
    public void onRefresh() {
        restaurantFetchCall(AppConstant.FROM_ONREFRESH);
    }

    @Override
    public void showProgress() {
        progressEmpty.showLoading();
    }

    @Override
    public void getRestaurantData(List<Restaurant> restaurants, RestaurantParam restaurantParam) {
        progressEmpty.showContent();
        swipeRefreshLayout.setRefreshing(false);


        if (restaurantList != null &&
                !restaurantList.isEmpty() &&
                !restaurantParam.getFrom().equalsIgnoreCase(AppConstant.FROM_ONLODEMORE)) {
            restaurantList.clear();
        }

        offset = restaurantParam.getStart() + 15;

        restaurantList.addAll(restaurants);
        restaurantListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        swipeRefreshLayout.setRefreshing(false);

        if (msg.equalsIgnoreCase(AppConstant.no_restaurant_found))
            progressEmpty.showError(R.drawable.zomato_default, "", msg, strRetry, retryClickListener, getVisibleView());
    }

    @Override
    public void showInternetError() {
        progressEmpty.showError(R.drawable.zomato_default, "", AppConstant.no_internet_connection, strRetry, retryClickListener, getVisibleView());
    }

    private void restaurantFetchCall(String from) {
        if (!from.equalsIgnoreCase(AppConstant.FROM_ONLODEMORE))
            offset = 0;

        RestaurantParam restaurantParam = new RestaurantParam();
        restaurantParam.setFrom(from);
        restaurantParam.setQuery(searchQuery);
        restaurantParam.setStart(offset);
        restaurantParam.setCount(limit);

        restaurantPresenter.getRestaurantList(restaurantParam);
    }

    private View.OnClickListener retryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            restaurantFetchCall(AppConstant.FROM_ONCREATE);
        }
    };

    public List<Integer> getVisibleView() {
        List<Integer> skipIds = new ArrayList<>();
        skipIds.add(R.id.coordinator_layout);
        skipIds.add(R.id.appbar);

        return skipIds;
    }

    private void waitForActionDone(final String newQuery) {
        //System.out.println(("waitForAction waiting");
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchQuery = newQuery;
                                restaurantFetchCall(AppConstant.FROM_ONCREATE);
                            }
                        });
                    }
                },
                DELAY
        );
    }

}
