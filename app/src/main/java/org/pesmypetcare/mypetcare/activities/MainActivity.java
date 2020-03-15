package org.pesmypetcare.mypetcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar toolbar;
    private NavigationView navigationView;
    private final int[] NAVIGATION_OPTIONS = new int[] {
        R.id.navigationMyPets, R.id.navigationPetsCommunity, R.id.navigationMyWalks, R.id.navigationNearEstablishments,
        R.id.navigationCalendar, R.id.navigationAchievements, R.id.navigationSettings
    };

    private final Class[] APPLICATION_FRAGMENTS = new Class[] {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeActivity();
    }

    private void initializeActivity() {
        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;

        initializeActionDrawerToggle();
        initializeActionbar();
        setUpNavigationDrawer();
    }


    private void setUpNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            //Fragment nextFragment = findNextFragment(menuItem.getItemId());
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.mainActivityFragment, nextFragment).commit();

            item.setChecked(true);
            toolbar.setTitle(item.getTitle());
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private Fragment findNextFragment(int itemId) {
        int index = 0;
        while (index < NAVIGATION_OPTIONS.length && NAVIGATION_OPTIONS[index] != itemId) {
            ++index;
        }

        Fragment nextFragment = null;
        try {
            nextFragment = (Fragment) APPLICATION_FRAGMENTS[index].newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return nextFragment;
    }

    private void initializeActionbar() {
        toolbar = getSupportActionBar();
        Objects.requireNonNull(toolbar).setTitle(R.string.navigation_my_pets);
        toolbar.setDisplayHomeAsUpEnabled(true);
    }

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
}
