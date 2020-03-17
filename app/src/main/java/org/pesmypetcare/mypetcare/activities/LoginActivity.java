package org.pesmypetcare.mypetcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.LogInFragment;
import org.pesmypetcare.mypetcare.activities.fragments.SignUpFragment;
import org.pesmypetcare.mypetcare.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        initializeActivity();

    }

    /**
     * Initializes the fragment and the listeners of the activity.
     */
    private void initializeActivity() {
        replaceFragment(new SignUpFragment());
        binding.tabActivityLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentSelector(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Unused method for our current tab
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Unused method for our current tab
            }
        });
    }

    /**
     * Selects the new fragment and replaces the old one.
     * @param tab The container of the tab
     */
    private void fragmentSelector(TabLayout.Tab tab) {
        Fragment fragment;
        if (tab.getPosition() == 0) {
            fragment = new SignUpFragment();
        } else {
            fragment = new LogInFragment();
        }
        replaceFragment(fragment);
    }

    /**
     * Replaces the current fragment of the view.
     * @param fragment The new fragment to display in the activity
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameFragment, fragment);
        ft.commit();
    }
}
