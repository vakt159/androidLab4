package com.example.spacexlab4;

import androidx.lifecycle.ViewModel;

import com.example.spacexlab4.model.LaunchService;

public class BaseViewModel extends ViewModel {
    private LaunchService launchService;

    public BaseViewModel(LaunchService launchService){
        this.launchService = launchService;
    }

    protected final LaunchService getLaunchService(){
        return launchService;
    }
}
