package com.example.githubtrendingrepo.repository.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;

import java.util.List;

@Dao
public interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GithubRepo> repo);

    @Query("DELETE FROM github_repo")
    void deleteAll();

    @Query("SELECT * from github_repo")
    LiveData<List<GithubRepo>> getAllRepo();
}