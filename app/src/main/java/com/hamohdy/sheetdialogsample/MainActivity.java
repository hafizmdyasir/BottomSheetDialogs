package com.hamohdy.sheetdialogsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hamohdy.bottomsheetdialogs.MessageSheet;
import com.hamohdy.bottomsheetdialogs.ProgressSheet;
import com.hamohdy.sheetdialogsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = (nightMode == Configuration.UI_MODE_NIGHT_YES);
        if (!isNightMode) binding.getRoot().setSystemUiVisibility(
                binding.getRoot().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding.showMessage.setOnClickListener(v -> {
            MessageSheet mMessageSheet = MessageSheet.getInstance(
                    "Title", "Include a message of your choice here. You can also pass string resources via the overloaded factory method",
                    "Negative", "Positive"
            );
            mMessageSheet.setClickListener(view -> Toast.makeText(this, "Button with id " + view.getId() + " clicked", Toast.LENGTH_SHORT).show());
            mMessageSheet.show(getSupportFragmentManager(), "TAG");
        });

        binding.showIndeterminateProgress.setOnClickListener(v -> {
            ProgressSheet progressSheet = ProgressSheet.getInstance(
                    "Please Wait", "Include a message of your choice here. You can also pass string resources via the overloaded factory method",
                    true, true
            );
            progressSheet.show(getSupportFragmentManager(), "TAG");
        });

        binding.showProgress.setOnClickListener(v -> {
            ProgressSheet progressSheet = ProgressSheet.getInstance(
                    "Loading", "Include a message of your choice here. You can also pass string resources via the overloaded factory method",
                    true, false
            );
            progressSheet.show(getSupportFragmentManager(), "TAG");
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    progressSheet.publishProgress(progressSheet.getProgress() + 10);
                    if (progressSheet.getProgress() != 100) v.postDelayed(this, 1000);
                }
            };
            progressSheet.setClickListener(view -> v.removeCallbacks(runnable));
            v.postDelayed(runnable, 1000);
        });
    }
}