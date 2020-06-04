package org.pesmypetcare.mypetcare.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.tabs.TabLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.login.LogInFragment;
import org.pesmypetcare.mypetcare.activities.fragments.login.SignUpFragment;
import org.pesmypetcare.mypetcare.databinding.ActivityLoginBinding;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class LoginActivity extends AppCompatActivity {
    private static final String GOOGLE_CALENDAR_SHARED_PREFERENCES = "GoogleCalendar";
    private static SharedPreferences sharedPreferences;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeActivity();
    }

    /**
     * Initializes the fragment and the listeners of the activity.
     */
    private void initializeActivity() {
        sharedPreferences = getSharedPreferences(GOOGLE_CALENDAR_SHARED_PREFERENCES, Context.MODE_PRIVATE);
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

    /**
     * Initializes the SharedPreferences.
     * @param acct The google account
     */
    public static void setGoogleAccount(GoogleSignInAccount acct) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GoogleEmail", acct.getEmail());

        StringBuilder scopes = new StringBuilder();
        for (Scope s : acct.getRequestedScopes())
            scopes.append(s.toString()).append(' ');
        scopes = new StringBuilder(scopes.substring(0, scopes.toString().lastIndexOf(' ')));

        editor.putString("GoogleScopes", scopes.toString());

        editor.apply();
    }
}
