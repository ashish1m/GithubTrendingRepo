package com.example.githubtrendingrepo.repository.remote.api;

import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubRepoService {

    String GIT_TRENDING_API = "https://github-trending-api.now.sh";
    String REPOSITORIES = "/repositories";

    @GET(REPOSITORIES)
    Call<List<GithubRepo>> fetchTrendingRepository();
}
