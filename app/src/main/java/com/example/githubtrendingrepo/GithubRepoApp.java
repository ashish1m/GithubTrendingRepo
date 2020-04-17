package com.example.githubtrendingrepo;

import android.app.Application;

import com.example.githubtrendingrepo.executor.AppExecutor;
import com.example.githubtrendingrepo.repository.Repository;
import com.example.githubtrendingrepo.repository.db.AppDatabase;

public class GithubRepoApp extends Application {

    private static GithubRepoApp sInstance;
    private AppExecutor mAppExecutor;

    public static GithubRepoApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mAppExecutor = new AppExecutor();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getDatabase(this);
    }

    public Repository getRepository() {
        return Repository.getInstance(getDatabase());
    }

    public AppExecutor getAppExecutor() {
        return mAppExecutor;
    }
}
