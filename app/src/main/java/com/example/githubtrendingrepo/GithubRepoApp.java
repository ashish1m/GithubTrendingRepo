package com.example.githubtrendingrepo;

import android.app.Application;

public class GithubRepoApp extends Application {

    private static GithubRepoApp sInstance;

    public static GithubRepoApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }
}
