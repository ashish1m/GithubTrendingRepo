package com.example.githubtrendingrepo.repository;

public class Repository {

    private static Repository sInstance;

    private Repository() {
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository();
                }
            }
        }
        return sInstance;
    }
}
