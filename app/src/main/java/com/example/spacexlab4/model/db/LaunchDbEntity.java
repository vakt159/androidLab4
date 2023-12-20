package com.example.spacexlab4.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.spacexlab4.model.network.LaunchNetworkEntity;


@Entity(tableName = "launches")
public class LaunchDbEntity {

    @PrimaryKey
    @ColumnInfo(name = "flight_number")
    private long flightNumber;

    @ColumnInfo(name = "mission_name")
    private String mission_name;

    @ColumnInfo(name = "launch_year")
    private String launch_year;

    public LaunchDbEntity() {
    }


    public LaunchDbEntity(LaunchNetworkEntity launchNetworkEntity) {
        this.flightNumber = launchNetworkEntity.getFlight_number();
        this.mission_name = launchNetworkEntity.getMission_name();
        this.launch_year = launchNetworkEntity.getLaunch_year();
    }

    public long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }
}
