package org.pesmypetcare.mypetcare.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import org.pesmypetcare.mypetcare.BuildConfig;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.login.LogInFragment;
import org.pesmypetcare.mypetcare.activities.fragments.login.SignUpFragment;
import org.pesmypetcare.mypetcare.databinding.ActivityLoginBinding;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d("LoginActivity", "Create LoginActivity");
        }
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
