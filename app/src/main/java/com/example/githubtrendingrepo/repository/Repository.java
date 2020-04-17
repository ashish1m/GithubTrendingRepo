package com.example.githubtrendingrepo.repository;

import androidx.lifecycle.LiveData;

import com.example.githubtrendingrepo.GithubRepoApp;
import com.example.githubtrendingrepo.repository.db.AppDatabase;
import com.example.githubtrendingrepo.repository.db.dao.GithubRepoDao;
import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;
import com.example.githubtrendingrepo.repository.remote.api.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository sInstance;
    private final GithubRepoDao mGithubRepoDao;
    private final LiveData<List<GithubRepo>> mGithubRepo;

    private Repository(AppDatabase db) {
        mGithubRepoDao = db.githubRepoDao();
        mGithubRepo = mGithubRepoDao.getAllRepo();
    }

    public static Repository getInstance(AppDatabase db) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository(db);
                }
            }
        }
        return sInstance;
    }

    public void fetchGithubTrendingRepo() {
        ApiClient.getInstance().getGithubRepoService().fetchTrendingRepository().enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, final Response<List<GithubRepo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GithubRepoApp.getInstance().getAppExecutor().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mGithubRepoDao.deleteAll();
                            mGithubRepoDao.insertAll(response.body());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<GithubRepo>> getGithubRepo() {
        return mGithubRepo;
    }
}
