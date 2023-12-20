package com.example.spacexlab4.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import com.example.spacexlab4.BaseViewModel;
import com.example.spacexlab4.model.Callback;
import com.example.spacexlab4.model.Cancellable;
import com.example.spacexlab4.model.Launch;
import com.example.spacexlab4.model.LaunchService;
import com.example.spacexlab4.model.Result;

public class MainViewModel extends BaseViewModel {

    private Result<List<Launch>> launchResult = Result.empty();
    private MutableLiveData<ViewState> stateLiveData = new MutableLiveData<>();

    {
        updateViewState(Result.empty());
    }

    private Cancellable cancellable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public MainViewModel(LaunchService launchService) {
        super(launchService);
    }

    public LiveData<ViewState> getViewState() {
        return stateLiveData;
    }

    public void getLaunches() {
        updateViewState(Result.loading());
        cancellable = getLaunchService().getLaunches(new Callback<List<Launch>>() {
            @Override
            public void onError(Throwable error) {
                if (launchResult.getStatus() != Result.Status.SUCCESS) {
                    updateViewState(Result.error(error));
                }
            }

            @Override
            public void onResults(List<Launch> data) {
                updateViewState(Result.success(data));
            }
        });
    }

    private void updateViewState(Result<List<Launch>> launchResult) {
        this.launchResult = launchResult;
        ViewState state = new ViewState();
        state.enableSearchButton = launchResult.getStatus() != Result.Status.LOADING;
        state.showList = launchResult.getStatus() == Result.Status.SUCCESS;
        state.showError = launchResult.getStatus() == Result.Status.ERROR;
        state.showProgress = launchResult.getStatus() == Result.Status.LOADING;
        state.launches = launchResult.getData();
        stateLiveData.postValue(state);
    }

    static class ViewState {
        boolean enableSearchButton;
        boolean showList;
        boolean showEmptyHint;
        boolean showError;
        boolean showProgress;
        private List<Launch> launches;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public void setEnableSearchButton(boolean enableSearchButton) {
            this.enableSearchButton = enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public void setShowList(boolean showList) {
            this.showList = showList;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public void setShowEmptyHint(boolean showEmptyHint) {
            this.showEmptyHint = showEmptyHint;
        }

        public boolean isShowError() {
            return showError;
        }

        public void setShowError(boolean showError) {
            this.showError = showError;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public void setShowProgress(boolean showProgress) {
            this.showProgress = showProgress;
        }

        public List<Launch> getLaunches() {
            return launches;
        }

        public void setRepositories(List<Launch> launches) {
            this.launches = launches;
        }
    }
}
