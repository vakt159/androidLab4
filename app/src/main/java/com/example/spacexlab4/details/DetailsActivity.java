package com.example.spacexlab4.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.spacexlab4.App;
import com.example.spacexlab4.R;
import com.example.spacexlab4.model.Launch;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LAUNCH_NUMBER = "LAUNCH_NUMBER";

    private TextView launchNumberTextView;
    private TextView missionNameTextView;
    private TextView launchYearTextView;
    private DetailsViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        launchNumberTextView = findViewById(R.id.launchNumber);
        missionNameTextView = findViewById(R.id.missionName);
        launchYearTextView = findViewById(R.id.launchYear);
        progressBar = findViewById(R.id.progress);

        long launchNumber = getIntent().getLongExtra(EXTRA_LAUNCH_NUMBER,-1);
        if (launchNumber == -1) {
            throw new RuntimeException("There is no launch number");
        }

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(DetailsViewModel.class);

        viewModel.loadLaunchByLaunchNumber(launchNumber);
        viewModel.getResults().observe(this, result -> {
            switch (result.getStatus()) {
                case SUCCESS:
                    Launch launch = result.getData();
                    launchYearTextView.setText(String.valueOf(launch.getLaunch_year()));
                    launchNumberTextView.setText(String.valueOf(launch.getFlightNumber()));
                    missionNameTextView.setText(launch.getMissionName());
                    progressBar.setVisibility(View.GONE);
                    break;
                case EMPTY:
                    launchYearTextView.setText("");
                    launchNumberTextView.setText("");
                    missionNameTextView.setText("");
                    progressBar.setVisibility(View.GONE);
                    break;
                case LOADING:
                    launchYearTextView.setText("");
                    launchNumberTextView.setText("");
                    missionNameTextView.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }
}
