package com.example.githubtrendingrepo.repository.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "github_repo")
public class GithubRepo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    private String forks;

    private String author;

    private String name;

    private String description;

    private String language;

    private String avatar;

    private String languageColor;

    private String stars;

    private String url;

    private String currentPeriodStars;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(String currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }
}
