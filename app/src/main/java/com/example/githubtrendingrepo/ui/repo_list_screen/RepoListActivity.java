package com.example.githubtrendingrepo.ui.repo_list_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.githubtrendingrepo.R;
import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.Objects;

public class RepoListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRepoRv;
    private RepoListAdapter mRepoListAdapter;
    private RepoListViewModel mRepoListViewModel;
    private ShimmerFrameLayout mShimmerLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mNoInternetLayout;
    private Button mRetryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mRepoListViewModel = new ViewModelProvider(this).get(RepoListViewModel.class);
        mRepoListAdapter = new RepoListAdapter(this);

        initView();
    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mShimmerLayout = findViewById(R.id.layout_shimmer);
        mNoInternetLayout = findViewById(R.id.layout_no_internet);
        mRetryBtn = findViewById(R.id.btn_retry);
        mRepoRv = findViewById(R.id.rv_repo_list);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        mRepoRv.setHasFixedSize(true);
        mRepoRv.setLayoutManager(new LinearLayoutManager(this));
        mRepoRv.addItemDecoration(itemDecoration);
        mRepoRv.setAdapter(mRepoListAdapter);

        mRetryBtn.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRepoListViewModel.fetchTrendingRepo();
            }
        });

        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerLayout.startShimmer();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShimmerLayout.stopShimmer();
    }

    private void updateUI() {
        mRepoListViewModel.getAllRepo().observe(this, new Observer<List<GithubRepo>>() {
            @Override
            public void onChanged(List<GithubRepo> githubRepos) {
                mSwipeRefreshLayout.setRefreshing(false);
                mShimmerLayout.stopShimmer();
                mShimmerLayout.setVisibility(View.GONE);
                if (githubRepos.size() > 0) {
                    mNoInternetLayout.setVisibility(View.GONE);
                    mRepoListAdapter.updateList(githubRepos);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    mNoInternetLayout.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                mRepoListViewModel.fetchTrendingRepo();
                break;
        }
    }
}
