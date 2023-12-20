package com.example.spacexlab4.model;

import com.example.spacexlab4.model.db.LaunchDbEntity;

public class Launch {

    private final long flightNumber;
    private final String missionName;
    private final String launch_year;

    public Launch(long flightNumber, String missionName, String launch_year) {
        this.flightNumber = flightNumber;
        this.missionName = missionName;
        this.launch_year = launch_year;
    }

    public Launch(LaunchDbEntity entity) {
        this(
                entity.getFlightNumber(),
                entity.getMission_name(),
                entity.getLaunch_year()
        );
    }

    public long getFlightNumber() {
        return flightNumber;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getLaunch_year() {
        return launch_year;
    }
}
