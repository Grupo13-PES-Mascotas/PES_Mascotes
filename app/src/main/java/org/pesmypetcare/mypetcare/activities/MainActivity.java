package org.pesmypetcare.mypetcare.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.MyPetsFragment;
import org.pesmypetcare.mypetcare.activities.communication.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.ImageZoom;
import org.pesmypetcare.mypetcare.activities.fragments.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.NotImplementedFragment;
import org.pesmypetcare.mypetcare.activities.fragments.RegisterPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.RegisterPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.SettingsMenuFragment;
import org.pesmypetcare.mypetcare.controllers.ControllersFactory;
import org.pesmypetcare.mypetcare.controllers.TrDeletePet;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.controllers.TrUpdatePetImage;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RegisterPetCommunication, NewPasswordInterface,
    InfoPetCommunication, MyPetsComunication {
    private static final int[] NAVIGATION_OPTIONS = {R.id.navigationMyPets, R.id.navigationPetsCommunity,
        R.id.navigationMyWalks, R.id.navigationNearEstablishments, R.id.navigationCalendar,
        R.id.navigationAchievements, R.id.navigationSettings
    };

    private static final Class[] APPLICATION_FRAGMENTS = {
        MyPetsFragment.class, NotImplementedFragment.class, NotImplementedFragment.class,
        NotImplementedFragment.class, NotImplementedFragment.class, NotImplementedFragment.class,
        SettingsMenuFragment.class
    };

    private Fragment actualFragment;

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private User user;
    private TrRegisterNewPet trRegisterNewPet;
    private TrUpdatePetImage trUpdatePetImage;
    private TrDeletePet trDeletePet;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeActivity();
        initializeControllers();

        user = new User("johnDoe", "johndoe@gmail.com", "1234");
    }

    /**
     * Initialize the controllers.
     */
    private void initializeControllers() {
        trRegisterNewPet = ControllersFactory.createTrRegisterNewPet();
        trUpdatePetImage = ControllersFactory.createTrUpdatePetImage();
    }

    /**
     * Initialize the views of this activity.
     */
    private void initializeActivity() {
        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;
        floatingActionButton = binding.flAddPet;

        initializeActionDrawerToggle();
        //initializeActionbar();
        setUpNavigationDrawer();
        setStartFragment();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    /**
     * Enters the fragment to create a pet.
     * @param view View from which the function was called
     */
    public void addPet(View view) {
        floatingActionButton.hide();
        changeFragment(getFragment(RegisterPetFragment.class));
        toolbar.setTitle(getString(R.string.register_new_pet));
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
            drawerLayout.closeDrawers();
            setUpNewFragment(item.getTitle(), item.getItemId());

            return true;
        });
    }

    /**
     * Sets up the new fragment.
     * @param title Title to display in the top bar
     * @param id Id of the navigation item
     */
    private void setUpNewFragment(CharSequence title, int id) {
        toolbar.setTitle(title);

        if (id == R.id.navigationMyPets) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    /**
     * Given a fragment class, if the class exists then an instance of it is returned. Otherwise, returns null.
     * @param fragmentClass Fragment class to create an instance of which
     * @return An instance of the fragmentClass if it exists or null otherwise
     */
    public Fragment getFragment(Class fragmentClass) {
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
    public void changeFragment(Fragment nextFragment) {
        actualFragment = nextFragment;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (actualFragment instanceof ImageZoom) {
                InfoPetFragment.setPetProfileDrawable(ImageZoom.getDrawable());
                changeFragment(new InfoPetFragment());
            }
            else {
                changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
                setUpNewFragment(getString(NAVIGATION_OPTIONS[0]), NAVIGATION_OPTIONS[0]);
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void addNewPet(Bundle petInfo) {
        Pet pet = new Pet(petInfo);

        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);

        try {
            trRegisterNewPet.execute();
        } catch (PetAlreadyExistingException e) {
            Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing), Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
        setUpNewFragment(getString(R.string.navigation_my_pets), NAVIGATION_OPTIONS[0]);
    }

    @Override
    public void changeFragmentPass(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityFrameLayout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public User getUser() {
        return user;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void makeZoomImage(Drawable drawable) {
        floatingActionButton.hide();
        changeFragment(new ImageZoom(drawable));
    }

    @Override
    public void updatePetImage(Pet pet, Bitmap newImage) {
        trUpdatePetImage.setUser(user);
        trUpdatePetImage.setPet(pet);
        trUpdatePetImage.setNewPetImage(newImage);

        try {
            trUpdatePetImage.execute();
        } catch (NotPetOwnerException e) {
            Toast toast = Toast.makeText(this, getString(R.string.error_user_not_owner), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void deletePet(Pet myPet) {
        trDeletePet.setUser(user);
        trDeletePet.setPet(myPet);
        try {
            trDeletePet.execute();
        } catch (UserIsNotOwnerException e) {
            Toast toast = Toast.makeText(this, getString(R.string.error_user_not_owner), Toast.LENGTH_LONG);
            toast.show();
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            galleryImageZoom(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (actualFragment != null) {
            changeFragment(actualFragment);
        }
    }

    /**
     * Access the gallery and selects an image to display in the zoom fragment.
     * @param data Data received from the gallery
     */
    private void galleryImageZoom(@Nullable Intent data) {
        String imagePath = getImagePath(data);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            ((ImageZoom) actualFragment).setDrawable(drawable);
        }
    }

    /**
     * Gets the path of the selected image in the gallery.
     * @param data Data received from the gallery
     * @return The path of the selected image
     */
    private String getImagePath(@Nullable Intent data) {
        Uri selectedImage = Objects.requireNonNull(data).getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(Objects.requireNonNull(selectedImage), filePathColumn,
            null, null, null);
        Objects.requireNonNull(cursor).moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }
}
