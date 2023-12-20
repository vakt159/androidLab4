package com.example.spacexlab4.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface LaunchDao {

    @Query("SELECT * FROM launches WHERE launch_year > strftime('%Y', 'now', '-5 years')")
    List<LaunchDbEntity> getLaunchesLessThan5Years();

    @Query("SELECT * FROM launches WHERE flight_number = :launchNumber")
    LaunchDbEntity getByLaunchNumber(long launchNumber);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLaunches(List<LaunchDbEntity> launches);

    @Query("DELETE FROM launches")
    void deleteLaunches();

    default void updateLaunchesForMission( List<LaunchDbEntity> launches) {
        deleteLaunches();
        insertLaunches(launches);
    }
}
