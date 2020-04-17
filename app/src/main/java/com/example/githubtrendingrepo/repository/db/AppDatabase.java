package com.example.githubtrendingrepo.repository.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.githubtrendingrepo.repository.db.dao.GithubRepoDao;
import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;

@Database(entities = {GithubRepo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "app_database.db";
    private static volatile AppDatabase INSTANCE;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext());
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context applicationContext) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME)
                .build();
    }


    public abstract GithubRepoDao githubRepoDao();

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public MutableLiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
