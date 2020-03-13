package org.pesmypetcare.mypetcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.pesmypetcare.mypetcare.databinding.ActivityLoginBinding;

public class LoginActivity extends FragmentActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
