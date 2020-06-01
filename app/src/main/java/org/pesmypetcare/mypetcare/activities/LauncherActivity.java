package org.pesmypetcare.mypetcare.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.pesmypetcare.mypetcare.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {
    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
