package com.example.githubtrendingrepo.ui.repo_list_screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubtrendingrepo.GithubRepoApp;
import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;

import java.util.List;

public class RepoListViewModel extends ViewModel {

    private LiveData<List<GithubRepo>> mRepoList;

    public RepoListViewModel() {
        GithubRepoApp.getInstance().getRepository().fetchGithubTrendingRepo();
        mRepoList = GithubRepoApp.getInstance().getRepository().getAllRepo();
    }

    public LiveData<List<GithubRepo>> getAllRepo() {
        return mRepoList;
    }
}
