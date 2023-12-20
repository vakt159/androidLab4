package com.example.spacexlab4.model.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LaunchApi {

    @GET("v3/launches/past")
    Call<List<LaunchNetworkEntity>> getLaunches();

}
