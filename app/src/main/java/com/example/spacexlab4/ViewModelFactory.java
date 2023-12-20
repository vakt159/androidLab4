package com.example.spacexlab4;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.spacexlab4.model.LaunchService;

import java.lang.reflect.Constructor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private LaunchService launchService;

    public static final String TAG = ViewModelFactory.class.getSimpleName();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(LaunchService.class);
            return constructor.newInstance(launchService);
        } catch (ReflectiveOperationException e) {
            Log.e(TAG,"Error", e);
            RuntimeException wrapper = new RuntimeException();
            wrapper.initCause(e);
            throw wrapper;
        }
    }

    public ViewModelFactory(LaunchService launchService) {
        this.launchService = launchService;
    }
}
