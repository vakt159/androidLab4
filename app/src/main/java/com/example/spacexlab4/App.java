package com.example.spacexlab4;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import com.example.spacexlab4.model.LaunchService;
import com.example.spacexlab4.model.db.AppDatabase;
import com.example.spacexlab4.model.db.LaunchDao;
import com.example.spacexlab4.model.network.LaunchApi;

public class App extends Application {

    private ViewModelProvider.Factory viewModelFactory;

    private static final String BASE_URL = "https://api.spacexdata.com";


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        LaunchApi launchApi = retrofit.create(LaunchApi.class);

        ExecutorService executorService = Executors.newCachedThreadPool();

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database.db")
                .build();
        LaunchDao repositoryDao = appDatabase.getLaunchDao();

        LaunchService launchService = new LaunchService(launchApi, repositoryDao, executorService);
        viewModelFactory = new ViewModelFactory(launchService);

    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return viewModelFactory;
    }
}
