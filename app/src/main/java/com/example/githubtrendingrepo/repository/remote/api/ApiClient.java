package com.example.githubtrendingrepo.repository.remote.api;

import android.app.Application;
import android.util.Log;

import com.example.githubtrendingrepo.GithubRepoApp;
import com.example.githubtrendingrepo.util.NetworkUtil;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiClient {

    private static ApiClient mInstance;
    private final String HEADER_CACHE_CONTROL = "Cache-Control";
    private final String HEADER_PRAGMA = "Pragma";
    private Retrofit mRetrofit;

    private ApiClient() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl(GithubRepoService.GIT_TRENDING_API)
                .client(getOkHttpClient(getCache(GithubRepoApp.getInstance())))
                .build();
    }

    public static ApiClient getInstance() {
        if (mInstance == null) {
            synchronized (ApiClient.class) {
                if (mInstance == null)
                    mInstance = new ApiClient();
            }
        }
        return mInstance;
    }

    private Cache getCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    private OkHttpClient getOkHttpClient(Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor());

        return httpClient.build();
    }

    /**
     * This interceptor will be called both if the network is available and if the network is not available
     *
     * @return
     */
    private Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "offline interceptor: called.");
                Request request = chain.request();

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (NetworkUtil.isNetworkConnected(GithubRepoApp.getInstance())) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    /**
     * This interceptor will be called ONLY if the network is available
     *
     * @return
     */
    private Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "network interceptor: called.");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.HOURS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public GithubRepoService getGithubRepoService() {
        return mRetrofit.create(GithubRepoService.class);
    }
}