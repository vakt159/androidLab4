package com.example.spacexlab4.model;

import android.util.Log;

import com.annimon.stream.Stream;
import com.example.spacexlab4.model.db.LaunchDao;
import com.example.spacexlab4.model.db.LaunchDbEntity;
import com.example.spacexlab4.model.network.LaunchApi;
import com.example.spacexlab4.model.network.LaunchNetworkEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Response;


public class LaunchService {
    private ExecutorService executorService;
    private LaunchApi launchApi;
    private LaunchDao launchDao;


    public static final String TAG = LaunchService.class.getSimpleName();

    public LaunchService(LaunchApi launchApi, LaunchDao launchDao, ExecutorService executorService) {
        this.launchApi = launchApi;
        this.launchDao = launchDao;
        this.executorService = executorService;
    }

    public Cancellable getLaunches( Callback<List<Launch>> callback) {
        Future<?> future = executorService.submit(() -> {
            try {
                List<LaunchDbEntity> entities = launchDao.getLaunchesLessThan5Years();
                List<Launch> launches = convertToLaunches(entities);
                callback.onResults(launches);
                Response<List<LaunchNetworkEntity>> response = launchApi.getLaunches().execute();
                if (response.isSuccessful()) {
                    List<LaunchDbEntity> newDbLaunch = networkToDbEntities( response.body());
                    launchDao.updateLaunchesForMission(newDbLaunch);
                    entities = launchDao.getLaunchesLessThan5Years();
                    launches = convertToLaunches(entities);
                    callback.onResults(launches);

                } else {
                    if (launches.isEmpty()) {
                        RuntimeException exception = new RuntimeException("there is no such user");
                        Log.e(TAG, "ErrorFromNetwork!", exception);
                        callback.onError(exception);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error!", e);
                callback.onError(e);
            }
        });

        return () -> future.cancel(true);
    }

    public Cancellable getLaunchByLaunchNumber(long launchNumber, Callback<Launch> callback) {
        Future<?> future = executorService.submit(() -> {
            try {
                LaunchDbEntity launchDbEntity = launchDao.getByLaunchNumber(launchNumber);
                Launch launch = new Launch(launchDbEntity);
                callback.onResults(launch);
            } catch (Exception e) {
                Log.e(TAG, "Error!", e);
                callback.onError(e);
            }
        });
        return () -> future.cancel(true);
    }

    private List<Launch> convertToLaunches(List<LaunchDbEntity> entities) {
        return Stream.of(entities)
                .map(Launch::new)
                .toList();
    }

    private List<LaunchDbEntity> networkToDbEntities( List<LaunchNetworkEntity> entities) {
        return Stream.of(entities)
                .map(networkEntity -> new LaunchDbEntity(networkEntity))
                .toList();
    }
}
