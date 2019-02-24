package com.lalitp.zomatosampleapp.Webapi;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.lalitp.zomatosampleapp.BuildConfig;
import com.lalitp.zomatosampleapp.Utils.Common_Utils;
import com.lalitp.zomatosampleapp.ZomatoSampleApp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import dimitrovskif.smartcache.BasicCaching;
import dimitrovskif.smartcache.SmartCallFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;


public class RestClient {

    public static final String ErrorMessage = "Servers cannot be reached. Please try again";
    public static final String SERVER_APIURL = "https://developers.zomato.com/api/v2.1/";
    public static final String MAP_SERVER_APIURL = "https://maps.googleapis.com/maps/api/";
    public static String GOOGLE_KEY = "AIzaSyCtoYNL4izl9UjT1UAiJ6UgndBpY_2Jeo8";
    private static final String CACHE_CONTROL = "Cache-Control";

    private static RestInterface apiService = null;


    public static RestInterface getClient() {
        return getRetrofit().create(RestInterface.class);
    }

    public static RestInterface getMapClient() {
        return getMapRetrofit().create(RestInterface.class);
    }

    private static Retrofit getMapRetrofit() {
        GsonBuilder gBuilder = new GsonBuilder();
        gBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<>());
        Gson gson = gBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(MAP_SERVER_APIURL)
                .client(provideOkHttpMapClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit getRetrofit() {
        SmartCallFactory smartFactory = new SmartCallFactory(BasicCaching.fromCtx(ZomatoSampleApp.getAppContext()));
        GsonBuilder gBuilder = new GsonBuilder();

        Gson gson = gBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(SERVER_APIURL)
                .client(provideOkHttpClient())
                .addCallAdapterFactory(smartFactory)
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

    private static OkHttpClient provideOkHttpMapClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideMapHeaderSHAInterceptor())
                //.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                //.cache(provideCache())
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


    public static String parseErrorThrow(Throwable throwable) {

        String message = "";
        if (throwable == null)
            return message;

        if(Common_Utils.isNotNullOrEmpty(throwable.getMessage())){
            message = throwable.getMessage();
        }

        return message;
    }

    public static Interceptor provideMapHeaderSHAInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String packageName = ZomatoSampleApp.getAppContext().getPackageName();
                String signature = getSignature();

                Request request = chain.request().newBuilder().
                        addHeader("X-Android-Package", packageName).
                        addHeader("X-Android-Cert",signature)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * Gets the SHA1 signature, hex encoded for inclusion with Google Cloud Platform API requests
     *
     * @param packageName Identifies the APK whose signature should be extracted.
     * @return a lowercase, hex-encoded
     */
    public static String getSignature() {
        try {
            PackageManager packageManager = ZomatoSampleApp.getAppContext().getPackageManager();
            String packageName = ZomatoSampleApp.getAppContext().getPackageName();

            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (packageInfo == null
                    || packageInfo.signatures == null
                    || packageInfo.signatures.length == 0
                    || packageInfo.signatures[0] == null) {
                return null;
            }
            return signatureDigest(packageInfo.signatures[0]);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static String signatureDigest(Signature sig) {
        byte[] signature = sig.toByteArray();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(signature);
            return BaseEncoding.base16().lowerCase().encode(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                //System.out.println("null  updated to space");

                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
