package com.example.spacexlab4.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LaunchDbEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LaunchDao getLaunchDao();
}
