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
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsComunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.NewPasswordInterface;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarFragment;
import org.pesmypetcare.mypetcare.activities.fragments.imagezoom.ImageZoomFragment;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.NotImplementedFragment;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsMenuFragment;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.controllers.ControllersFactory;
import org.pesmypetcare.mypetcare.controllers.TrChangeMail;
import org.pesmypetcare.mypetcare.controllers.TrChangePassword;
import org.pesmypetcare.mypetcare.controllers.TrDeletePet;
import org.pesmypetcare.mypetcare.controllers.TrDeleteUser;
import org.pesmypetcare.mypetcare.controllers.TrObtainUser;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.controllers.TrUpdatePet;
import org.pesmypetcare.mypetcare.controllers.TrUpdatePetImage;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RegisterPetCommunication, NewPasswordInterface,
    InfoPetCommunication, MyPetsComunication, SettingsCommunication, CalendarCommunication {
    private static final int[] NAVIGATION_OPTIONS = {R.id.navigationMyPets, R.id.navigationPetsCommunity,
        R.id.navigationMyWalks, R.id.navigationNearEstablishments, R.id.navigationCalendar,
        R.id.navigationAchievements, R.id.navigationSettings
    };

    private static final Class[] APPLICATION_FRAGMENTS = {
        MyPetsFragment.class, NotImplementedFragment.class, NotImplementedFragment.class,
        NotImplementedFragment.class, CalendarFragment.class, NotImplementedFragment.class,
        SettingsMenuFragment.class
    };

    private static boolean enableLoginActivity = true;
    private static FloatingActionButton floatingActionButton;
    private static FirebaseAuth mAuth;
    private static Fragment actualFragment;
    private static User user;

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private TrRegisterNewPet trRegisterNewPet;
    private TrUpdatePetImage trUpdatePetImage;
    private TrChangePassword trChangePassword;
    private TrDeletePet trDeletePet;
    private TrDeleteUser trDeleteUser;
    private TrObtainUser trObtainUser;
    private TrUpdatePet trUpdatePet;
    private TrChangeMail trChangeMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (enableLoginActivity && mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else if (!enableLoginActivity) {
            user = new User("johnDoe", "johnDoe@gmail.com", "1234");
        }

        initializeControllers();

        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;
        floatingActionButton = binding.flAddPet;

        if (mAuth.getCurrentUser() != null) {
            try {
                initializeUser();
                //changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
            } catch (PetRepeatException e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing),
                    Toast.LENGTH_LONG);
                toast.show();
            }
        }

        initializeActivity();
        setUpNavigationImage();
    }

    /**
     * Returns the instance of Firebase.
     * @return The instance of Firebase
     */
    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    /**
     * Initialize the current.
     * @throws PetRepeatException The pet has already been registered
     */
    private void initializeUser() throws PetRepeatException {
        trObtainUser.setUsername(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        trObtainUser.execute();

        user = trObtainUser.getResult();

        for (Pet pet : user.getPets()) {
            try {
                System.out.println("TRY");
                byte[] bytes = ImageManager.readImage(ImageManager.PROFILE_IMAGES_PATH,
                    pet.getOwner().getUsername() + '_' + pet.getName());
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                pet.setProfileImage(bitmap);
            } catch (IOException e) {
                System.out.println("CATCH");
                Drawable petImageDrawable = getResources().getDrawable(R.drawable.single_paw, null);
                pet.setProfileImage(((BitmapDrawable) petImageDrawable).getBitmap());
            }
        }

        View navigationHeader = navigationView.getHeaderView(0);
        TextView userName = navigationHeader.findViewById(R.id.lblUserName);
        TextView userEmail = navigationHeader.findViewById(R.id.lblUserEmail);
        CircularImageView circularImageView = navigationHeader.findViewById(R.id.imgUser);
        Drawable imgUser = new BitmapDrawable(getResources(), user.getUserProfileImage());

        userName.setText(getString(R.string.app_name));
        userEmail.setText(user.getEmail());
        circularImageView.setDrawable(imgUser);
    }

    /**
     * Initialize the controllers.
     */
    private void initializeControllers() {
        trRegisterNewPet = ControllersFactory.createTrRegisterNewPet();
        trUpdatePetImage = ControllersFactory.createTrUpdatePetImage();
        trChangePassword = ControllersFactory.createTrChangePassword();
        trDeletePet = ControllersFactory.createTrDeletePet();
        trDeleteUser = ControllersFactory.createTrDeleteUser();
        trObtainUser = ControllersFactory.createTrObtainUser();
        trUpdatePet = ControllersFactory.createTrUpdatePet();
        trChangeMail = ControllersFactory.createTrChangeMail();
    }

    /**
     * Initialize the views of this activity.
     */
    private void initializeActivity() {
        initializeActionbar();
        initializeActionDrawerToggle();
        setUpNavigationDrawer();
        setStartFragment();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static void hideFloatingPoint() {
        floatingActionButton.hide();
    }

    public static void setPetImage(Pet pet) {
        System.out.println("UPDATING PET");
        user.updatePetProfileImage(pet);
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
     * Set the actual fragment.
     * @param actualFragment The actual fragment to set
     */
    public static void setActualFragment(Fragment actualFragment) {
        MainActivity.actualFragment = actualFragment;
    }

    /**
     * Set the enable of the login activity.
     * @param enableLoginActivity The enable of the login activity to set
     */
    public static void setEnableLoginActivity(boolean enableLoginActivity) {
        MainActivity.enableLoginActivity = enableLoginActivity;
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
     * Method responsible for initializing the listener of the header view.
     */
    private void setUpNavigationImage() {
        setUpUserImage();
        //CircularImageView userImageView = findViewById(R.id.imgUser);
        /*userImageView.setDrawable(drawable);
        userImageView.setClickable(true);
        userImageView.setOnClickListener(v -> changeFragment(new ImageZoom(drawable)));*/

        navigationView.getHeaderView(0).setOnClickListener(v -> {
            Drawable drawable = new BitmapDrawable(getResources(), user.getUserProfileImage());
            ImageZoomFragment imageZoomFragment = new ImageZoomFragment(drawable);
            ImageZoomFragment.setIsMainActivity(true);
            floatingActionButton.hide();
            drawerLayout.closeDrawers();
            changeFragment(imageZoomFragment);
        });
    }

    /**
     * Method responsible for assigning an default image to the user.
     */
    private void setUpUserImage() {
        Bitmap userImageBitmap = user.getUserProfileImage();
        if (userImageBitmap == null) {
            user.setUserProfileImage(BitmapFactory.decodeResource(getResources(), R.drawable.test));
        }
    }

    /**
     * Sets up the new fragment.
     * @param title Title to display in the top bar
     * @param id Id of the navigation item
     */
    private void setUpNewFragment(CharSequence title, int id) {
        toolbar.setTitle(title);
        toolbar.setContentDescription(title);

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
        fragmentTransaction.replace(R.id.mainActivityFrameLayout, nextFragment, nextFragment.getClass()
            .getSimpleName());
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
        toolbar = binding.toolbar;
        Objects.requireNonNull(toolbar).setTitle(R.string.navigation_my_pets);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initializes the action drawer toggle of the navigation drawer.
     */
    private void initializeActionDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
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
            if (actualFragment instanceof ImageZoomFragment) {
                changeFromImageZoom();
                return true;
            } else if (!(actualFragment instanceof MyPetsFragment)){
                changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
                setUpNewFragment(getString(R.string.navigation_my_pets), NAVIGATION_OPTIONS[0]);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Change to next fragment from an ImageZoomFragment
     */
    private void changeFromImageZoom() {
        if (ImageZoomFragment.isMainActivity()) {
            Drawable drawable = ImageZoomFragment.getDrawable();
            user.setUserProfileImage(((BitmapDrawable) drawable).getBitmap());
            changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
        } else {
            InfoPetFragment.setPetProfileDrawable(ImageZoomFragment.getDrawable());
            changeFragment(new InfoPetFragment());
        }
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
        /*try {
            initializeUser();
        } catch (PetRepeatException e) {
            e.printStackTrace();
        }*/

        return user;
    }

    @Override
    public void changePetProfileImage(Pet actualPet) {
        user.updatePetProfileImage(actualPet);
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*if (enableLoginActivity && mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        if (mAuth.getCurrentUser() != null && actualFragment == null) {
            try {
                System.out.println("On START");
                initializeUser();
                changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
            } catch (PetRepeatException e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing),
                    Toast.LENGTH_LONG);
                toast.show();
            }
        }*/
    }


    @Override
    public void makeZoomImage(Drawable drawable) {
        floatingActionButton.hide();
        ImageZoomFragment.setIsMainActivity(false);
        changeFragment(new ImageZoomFragment(drawable));
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
        }

        changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
    }

    @Override
    public void updatePet(Pet pet) throws UserIsNotOwnerException {
        trUpdatePet.setUser(user);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    @Override
    public void changeToMainView() {

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
            System.out.println("I'm not null");

            if (actualFragment instanceof ImageZoomFragment) {
                System.out.println("I'm ImageZoomFragment");
            }
            else {
                System.out.println("I'm another one");
            }
            changeFragment(actualFragment);
        }
    }

    /**
     * Access the gallery and selects an image to display in the zoom fragment.
     * @param data Data received from the gallery
     */
    private void galleryImageZoom(@Nullable Intent data) {
        System.out.println("Decoding image");
        String imagePath = getImagePath(data);
        System.out.println("Finished decoding image");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            ((ImageZoomFragment) actualFragment).setDrawable(drawable);
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

    @Override
    public void changePassword(String password) {
        trChangePassword.setUser(user);
        try {
            trChangePassword.setNewPassword(password);
        } catch (SamePasswordException e) {
            Toast toast = Toast.makeText(this, "No change", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        trChangePassword.execute();
    }

    @Override
    public void deleteUser(User user) {
        trDeleteUser.setUser(user);
        try {
            trDeleteUser.execute();
        } catch (NotValidUserException e) {
            Toast toast = Toast.makeText(this, "Not valid user", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public User getUserForSettings() {
        return user;
    }

    @Override  
    public void changeMail(String newEmail) {
        trChangeMail.setUser(user);
        trChangeMail.setMail(newEmail);
        trChangeMail.execute();
    }
}
