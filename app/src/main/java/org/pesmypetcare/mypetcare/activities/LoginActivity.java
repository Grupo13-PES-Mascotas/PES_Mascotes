package org.pesmypetcare.mypetcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.pesmypetcare.mypetcare.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
