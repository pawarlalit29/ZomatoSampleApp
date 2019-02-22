package com.lalitp.zomatosampleapp.Webapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalitp.zomatosampleapp.BuildConfig;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by atulsia on 19/2/16.
 */
public class RestClient {

    public static final String ErrorMessage = "Servers cannot be reached. Please try again";
    public static final String SERVER_APIURL = "https://developers.zomato.com/api/v2.1/";


    private static final String CACHE_CONTROL = "Cache-Control";

    private static RestInterface apiService = null;

    public static RestInterface getClient() {
        return getRetrofit().create(RestInterface.class);
    }



    public static Retrofit getRetrofit() {
        GsonBuilder gBuilder = new GsonBuilder();

        Gson gson = gBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(SERVER_APIURL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .build();

        return okHttpClient;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        //LogUtils.LOGD("provideHttpLoggingInterceptor", message);
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return logging;
    }


    public static void parseErrorThrow(Throwable throwable) {
        if (throwable == null)
            return;

        if(Common_Utils.isNotNullOrEmpty(throwable.getMessage())){
            Common_Utils.showToast(throwable.getMessage());
        }
    }


}
