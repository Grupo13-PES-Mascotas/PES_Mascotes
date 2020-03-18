package org.pesmypetcare.mypetcare.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.NotImplementedFragment;
import org.pesmypetcare.mypetcare.activities.fragments.SettingsMenuFragment;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NewPasswordInterface{
    private static final int[] NAVIGATION_OPTIONS = {R.id.navigationMyPets, R.id.navigationPetsCommunity,
        R.id.navigationMyWalks, R.id.navigationNearEstablishments, R.id.navigationCalendar,
        R.id.navigationAchievements, R.id.navigationSettings
    };

    private static final Class[] APPLICATION_FRAGMENTS = {
        NotImplementedFragment.class, NotImplementedFragment.class, NotImplementedFragment.class,
        NotImplementedFragment.class, NotImplementedFragment.class, NotImplementedFragment.class,
        SettingsMenuFragment.class
    };

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeActivity();
    }

    /**
     * Initialize the views of this activity.
     */
    private void initializeActivity() {
        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;

        initializeActionDrawerToggle();
        initializeActionbar();
        setUpNavigationDrawer();
        setStartFragment();
    }

    /**
     * Sets the first fragment to appear when loading the application for the first time.
     */
    private void setStartFragment() {
        Fragment startFragment = getFragment(APPLICATION_FRAGMENTS[0]);
        changeFragment(startFragment);
    }

    /**
     * Sets up the navigation drawer of the application.
     */
    private void setUpNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment nextFragment = findNextFragment(item.getItemId());
            changeFragment(nextFragment);

            item.setChecked(true);
            toolbar.setTitle(item.getTitle());
            drawerLayout.closeDrawers();
            return true;
        });
    }

    /**
     * Given a fragment class, if the class exists then an instance of it is returned. Otherwise, returns null.
     * @param fragmentClass Fragment class to create an instance of which
     * @return An instance of the fragmentClass if it exists or null otherwise
     */
    private Fragment getFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return fragment;
    }

    /**
     * Change the current fragment to the one specified.
     * @param nextFragment Fragment to replace the current one
     */
    private void changeFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityFrameLayout, nextFragment);
        fragmentTransaction.commit();

    }

    /**
     * Given an id of a menu item, it returns the fragment to which the id is associated with.
     * @param menuItemId The selected menu id
     * @return The fragment associated with menuItemId
     */
    private Fragment findNextFragment(int menuItemId) {
        int index = 0;
        while (index < NAVIGATION_OPTIONS.length && NAVIGATION_OPTIONS[index] != menuItemId) {
            ++index;
        }

        return getFragment(APPLICATION_FRAGMENTS[index]);
    }

    /**
     * Initializes the action bar of the application.
     */
    private void initializeActionbar() {
        toolbar = getSupportActionBar();
        Objects.requireNonNull(toolbar).show();
        Objects.requireNonNull(toolbar).setTitle(R.string.navigation_my_pets);
        toolbar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initializes the action drawer toggle of the navigation drawer.
     */
    private void initializeActionDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
            R.string.navigation_view_open, R.string.navigation_view_closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void changeFragmentPass(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}
