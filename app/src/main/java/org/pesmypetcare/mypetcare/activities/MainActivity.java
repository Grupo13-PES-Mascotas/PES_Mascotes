package org.pesmypetcare.mypetcare.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.NotImplementedFragment;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarFragment;
import org.pesmypetcare.mypetcare.activities.fragments.community.CommunityCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.community.CommunityFragment;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.ForumsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.PostsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.imagezoom.ImageZoomCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.imagezoom.ImageZoomFragment;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsComunication;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.settings.NewPasswordInterface;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsMenuFragment;
import org.pesmypetcare.mypetcare.activities.threads.GetPetImageRunnable;
import org.pesmypetcare.mypetcare.activities.threads.ThreadFactory;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.controllers.community.CommunityControllersFactory;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewForum;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewPost;
import org.pesmypetcare.mypetcare.controllers.community.TrAddSubscription;
import org.pesmypetcare.mypetcare.controllers.community.TrCreateNewGroup;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteForum;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteGroup;
import org.pesmypetcare.mypetcare.controllers.community.TrDeletePost;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteSubscription;
import org.pesmypetcare.mypetcare.controllers.community.TrObtainAllGroups;
import org.pesmypetcare.mypetcare.controllers.community.TrUpdatePost;
import org.pesmypetcare.mypetcare.controllers.event.EventControllersFactory;
import org.pesmypetcare.mypetcare.controllers.event.TrDeletePeriodicNotification;
import org.pesmypetcare.mypetcare.controllers.event.TrDeletePersonalEvent;
import org.pesmypetcare.mypetcare.controllers.event.TrNewPeriodicNotification;
import org.pesmypetcare.mypetcare.controllers.event.TrNewPersonalEvent;
import org.pesmypetcare.mypetcare.controllers.meals.MealsControllersFactory;
import org.pesmypetcare.mypetcare.controllers.meals.TrDeleteMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrNewPetMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrObtainAllPetMeals;
import org.pesmypetcare.mypetcare.controllers.meals.TrUpdateMeal;
import org.pesmypetcare.mypetcare.controllers.medication.MedicationControllersFactory;
import org.pesmypetcare.mypetcare.controllers.medication.TrDeleteMedication;
import org.pesmypetcare.mypetcare.controllers.medication.TrNewPetMedication;
import org.pesmypetcare.mypetcare.controllers.medication.TrObtainAllPetMedications;
import org.pesmypetcare.mypetcare.controllers.medication.TrUpdateMedication;
import org.pesmypetcare.mypetcare.controllers.pet.PetControllersFactory;
import org.pesmypetcare.mypetcare.controllers.pet.TrDeletePet;
import org.pesmypetcare.mypetcare.controllers.pet.TrObtainAllPetImages;
import org.pesmypetcare.mypetcare.controllers.pet.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.controllers.pet.TrUpdatePet;
import org.pesmypetcare.mypetcare.controllers.pet.TrUpdatePetImage;
import org.pesmypetcare.mypetcare.controllers.pethealth.PetHealthControllersFactory;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrAddNewWashFrequency;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrAddNewWeight;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrDeleteWashFrequency;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrDeleteWeight;
import org.pesmypetcare.mypetcare.controllers.user.TrChangeMail;
import org.pesmypetcare.mypetcare.controllers.user.TrChangePassword;
import org.pesmypetcare.mypetcare.controllers.user.TrChangeUsername;
import org.pesmypetcare.mypetcare.controllers.user.TrDeleteUser;
import org.pesmypetcare.mypetcare.controllers.user.TrExistsUsername;
import org.pesmypetcare.mypetcare.controllers.user.TrObtainUser;
import org.pesmypetcare.mypetcare.controllers.user.TrUpdateUserImage;
import org.pesmypetcare.mypetcare.controllers.user.UserControllersFactory;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumCreatedBeforeGroupException;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.forums.UserNotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.OwnerCannotDeleteSubscriptionException;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.notification.Notification;
import org.pesmypetcare.mypetcare.features.notification.NotificationReceiver;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.SameUsernameException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements RegisterPetCommunication, NewPasswordInterface,
    InfoPetCommunication, MyPetsComunication, SettingsCommunication, CalendarCommunication, ImageZoomCommunication,
    CommunityCommunication, InfoGroupCommunication {
    private static final int[] NAVIGATION_OPTIONS = {R.id.navigationMyPets, R.id.navigationPetsCommunity,
        R.id.navigationMyWalks, R.id.navigationNearEstablishments, R.id.navigationCalendar,
        R.id.navigationAchievements, R.id.navigationSettings
    };

    private static final Class[] APPLICATION_FRAGMENTS = {
        MyPetsFragment.class, CommunityFragment.class, NotImplementedFragment.class,
        NotImplementedFragment.class, CalendarFragment.class, NotImplementedFragment.class,
        SettingsMenuFragment.class
    };
    public static final String TAG_REGEX = "^[a-zA-Z0-9,]*$";

    private static boolean enableLoginActivity = true;
    private static FloatingActionButton floatingActionButton;
    private static FirebaseAuth mAuth;
    private static Fragment actualFragment;
    private static User user;
    private static String googleCalendarToken;
    private static Resources resources;
    private static NavigationView navigationView;
    private static int[] countImagesNotFound;
    private static List<Group> groups;
    private static int notificationId;
    private static int requestCode;

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MaterialToolbar toolbar;
    private TrRegisterNewPet trRegisterNewPet;
    private TrUpdatePetImage trUpdatePetImage;
    private TrChangePassword trChangePassword;
    private TrDeletePet trDeletePet;
    private TrDeleteUser trDeleteUser;
    private TrObtainUser trObtainUser;
    private TrUpdatePet trUpdatePet;
    private TrChangeMail trChangeMail;
    private TrChangeUsername trChangeUsername;
    private TrExistsUsername trExistsUsername;
    private TrObtainAllPetImages trObtainAllPetImages;
    private TrUpdateUserImage trUpdateUserImage;
    private TrNewPersonalEvent trNewPersonalEvent;
    private TrDeletePersonalEvent trDeletePersonalEvent;
    private TrAddNewWeight trAddNewWeight;
    private TrDeleteWeight trDeleteWeight;
    private TrAddNewWashFrequency trAddNewWashFrequency;
    private TrDeleteWashFrequency trDeleteWashFrequency;
    private TrNewPetMeal trNewPetMeal;
    private TrObtainAllPetMeals trObtainAllPetMeals;
    private TrDeleteMeal trDeleteMeal;
    private TrUpdateMeal trUpdateMeal;
    private TrObtainAllGroups trObtainAllGroups;
    private TrCreateNewGroup trCreateNewGroup;
    private TrDeleteGroup trDeleteGroup;
    private TrAddSubscription trAddSubscription;
    private TrDeleteSubscription trDeleteSubscription;
    private TrAddNewForum trAddNewForum;
    private TrDeleteForum trDeleteForum;
    private TrAddNewPost trAddNewPost;
    private TrDeletePost trDeletePost;
    private TrUpdatePost trUpdatePost;
    private TrNewPetMedication trNewPetMedication;
    private TrObtainAllPetMedications trObtainAllPetMedications;
    private TrDeleteMedication trDeleteMedication;
    private TrUpdateMedication trUpdateMedication;
    private TrNewPeriodicNotification trNewPeriodicNotification;
    private TrDeletePeriodicNotification trDeletePeriodicNotification;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resources = getResources();

        notificationId = 0;
        requestCode = 0;

        makeLogin();
        initializeControllers();
        getComponents();

        ImageManager.setPetDefaultImage(getResources().getDrawable(R.drawable.single_paw));
        initializeCurrentUser();
        initializeActivity();
        setUpNavigationImage();
    }

    /**
     * Initializes the current user.
     */
    private void initializeCurrentUser() {
        if (mAuth.getCurrentUser() != null) {
            try {
                initializeUser();
                user.setGoogleCalendarToken(googleCalendarToken);
                //changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
            } catch (PetRepeatException e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing),
                    Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    /**
     * Get the components of the activity.
     */
    private void getComponents() {
        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;
        floatingActionButton = binding.flAddPet;
    }

    /**
     * Make the login to the application.
     */
    private void makeLogin() {
        if (enableLoginActivity && mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else if (!enableLoginActivity) {
            user = new User("johnDoe", "johnDoe@gmail.com", "1234");
        }
    }

    /**
     * Returns the instance of Firebase.
     * @return The instance of Firebase
     */
    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setToolbarText(String text) {
        toolbar.setTitle(text);
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
            obtainAllPetMeals(pet);
            obtainAllPetMedications(pet);
        }

        Thread askPermissionThread = ThreadFactory.createAskPermissionThread(this);
        Thread petsImagesThread = createPetsImagesThread();
        Thread updatePetImagesThread = createUpdatePetImagesThread(petsImagesThread);

        askPermissionThread.start();

        try {
            askPermissionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updatePetImagesThread.start();

        setUpNavigationHeader();
    }

    /**
     * Create the update pet images thread.
     * @param petsImagesThread The thread for getting the pet image
     * @return The update pet images thread
     */
    private Thread createUpdatePetImagesThread(Thread petsImagesThread) {
        return new Thread(() -> {
                petsImagesThread.start();

                try {
                    petsImagesThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (actualFragment instanceof MyPetsFragment) {
                    changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
                } else if (actualFragment instanceof InfoPetFragment) {
                    changeFragment(actualFragment);
                }
            });
    }

    /**
     * Create the pets images thread.
     * @return The pet images thread
     */
    private Thread createPetsImagesThread() {
        return new Thread(() -> {
            int imagesNotFound = getPetImages();
            int nUserPets = user.getPets().size();

            if (imagesNotFound == nUserPets) {
                getImagesFromServer();
            }
        });
    }

    /**
     * Get the images of the pets from the server.
     */
    private void getImagesFromServer() {
        trObtainAllPetImages.setUser(user);
        trObtainAllPetImages.execute();
        Map<String, byte[]> petImages = trObtainAllPetImages.getResult();
        Set<String> names = petImages.keySet();

        for (String petName : names) {
            byte[] bytes = petImages.get(petName);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, Objects.requireNonNull(bytes).length);
            int index = user.getPets().indexOf(new Pet(petName));
            user.getPets().get(index).setProfileImage(bitmap);
            ImageManager.writeImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername() + '_' + petName, bytes);
        }
    }

    /**
     * Get the images for the pets.
     * @return The number of time an image is not found in local storage
     */
    private int getPetImages() {
        int nUserPets = user.getPets().size();
        Drawable defaultDrawable = getResources().getDrawable(R.drawable.single_paw, null);
        Bitmap defaultBitmap = ((BitmapDrawable) defaultDrawable).getBitmap();
        countImagesNotFound = new int[nUserPets];
        Arrays.fill(countImagesNotFound, 0);

        ExecutorService executorService = Executors.newCachedThreadPool();
        startRunnable(nUserPets, executorService);
        executorService.shutdown();

        try {
            executorService.awaitTermination(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return calculateImagesNotFound();
    }

    /**
     * Calculates how many time an image is not found in local storage.
     * @return The number of times an image is not found in local storage
     */
    private int calculateImagesNotFound() {
        int imagesNotFound = 0;
        for (int count : countImagesNotFound) {
            imagesNotFound += count;
        }

        return imagesNotFound;
    }

    /**
     * Start the runnable to obtain the images of the pets.
     * @param nUserPets The number of pets the user has
     * @param executorService The executor service of the runnable
     */
    private void startRunnable(int nUserPets, ExecutorService executorService) {
        for (int actual = 0; actual < nUserPets; ++actual) {
            executorService.execute(new GetPetImageRunnable(actual, user.getUsername(),
                user.getPets().get(actual).getName()));
        }
    }

    /**
     * Set up the navigation header.
     */
    private static void setUpNavigationHeader() {
        View navigationHeader = navigationView.getHeaderView(0);
        TextView userName = navigationHeader.findViewById(R.id.lblUserName);
        TextView userEmail = navigationHeader.findViewById(R.id.lblUserEmail);
        CircularImageView circularImageView = navigationHeader.findViewById(R.id.imgUser);

        userName.setText(user.getUsername());
        userEmail.setText(user.getEmail());

        if (user.getUserProfileImage() == null) {
            circularImageView.setDrawable(resources.getDrawable(R.drawable.user_icon_sample));
        } else {
            Drawable imgUser = new BitmapDrawable(resources, user.getUserProfileImage());
            circularImageView.setDrawable(imgUser);
        }
    }

    /**
     * Set the pet image.
     * @param actual The position of the actual pet
     * @param petImage The bitmap of the selected pet
     */
    public static void setPetBitmapImage(int actual, Bitmap petImage) {
        user.getPets().get(actual).setProfileImage(petImage);
    }

    /**
     * Increment the count of the pet that has not got an image.
     * @param actual The actual pet position
     */
    public static void incrementCountNotImage(int actual) {
        ++countImagesNotFound[actual];
    }

    /**
     * Set the default user image.
     */
    public static void setDefaultUserImage() {
        user.setUserProfileImage(null);
        setUpNavigationHeader();
        ImageManager.deleteImage(ImageManager.USER_PROFILE_IMAGES_PATH, user.getUsername());
    }

    /**
     * Initialize the controllers.
     */
    private void initializeControllers() {
        initializePetControllers();
        initializeEventControllers();
        initializeUserControllers();
        initializePetHealthControllers();
        initializeMealsControllers();
        initializeCommunityControllers();
        initializeMedicationControllers();
    }

    /**
     * Initialize the pet controllers.
     */
    private void initializePetControllers() {
        trRegisterNewPet = PetControllersFactory.createTrRegisterNewPet();
        trUpdatePetImage = PetControllersFactory.createTrUpdatePetImage();
        trDeletePet = PetControllersFactory.createTrDeletePet();
        trUpdatePet = PetControllersFactory.createTrUpdatePet();
        trObtainAllPetImages = PetControllersFactory.createTrObtainAllPetImages();
    }

    /**
     * Initialize the event controllers.
     */
    private void initializeEventControllers() {
        trNewPersonalEvent = EventControllersFactory.createTrNewPersonalEvent();
        trDeletePersonalEvent = EventControllersFactory.createTrDeletePersonalEvent();
        trNewPeriodicNotification = EventControllersFactory.createTrNewPeriodicNotification();
        trDeletePeriodicNotification = EventControllersFactory.createTrDeletePeriodicNotification();
    }

    /**
     * Initialize the user controllers.
     */
    private void initializeUserControllers() {
        trUpdateUserImage = UserControllersFactory.createTrUpdateUserImage();
        trDeleteUser = UserControllersFactory.createTrDeleteUser();
        trObtainUser = UserControllersFactory.createTrObtainUser();
        trChangePassword = UserControllersFactory.createTrChangePassword();
        trChangeMail = UserControllersFactory.createTrChangeMail();
        trChangeUsername = UserControllersFactory.createTrChangeUsername();
        trExistsUsername = UserControllersFactory.createTrExistsUsername();
    }

    /**
     * Initialize the pet health controllers.
     */
    private void initializePetHealthControllers() {
        trAddNewWeight = PetHealthControllersFactory.createTrAddNewWeight();
        trDeleteWeight = PetHealthControllersFactory.createTrDeleteWeight();
        trAddNewWashFrequency = PetHealthControllersFactory.createTrAddNewWashFrequency();
        trDeleteWashFrequency = PetHealthControllersFactory.createTrDeleteWashFrequency();
    }

    /**
     * Initialize the meal controllers.
     */
    private void initializeMealsControllers() {
        trNewPetMeal = MealsControllersFactory.createTrNewPetMeal();
        trObtainAllPetMeals = MealsControllersFactory.createTrObtainAllPetMeals();
        trDeleteMeal = MealsControllersFactory.createTrDeleteMeal();
        trUpdateMeal = MealsControllersFactory.createTrUpdateMeal();
    }

    /**
     * Initialize the community controllers.
     */
    private void initializeCommunityControllers() {
        trObtainAllGroups = CommunityControllersFactory.createTrObtainAllGroups();
        trCreateNewGroup = CommunityControllersFactory.createTrCreateNewGroup();
        trDeleteGroup = CommunityControllersFactory.createTrDeleteGroup();
        trAddSubscription = CommunityControllersFactory.createTrAddSubscription();
        trDeleteSubscription = CommunityControllersFactory.createTrDeleteSubscription();
        trAddNewForum = CommunityControllersFactory.createTrAddNewForum();
        trDeleteForum = CommunityControllersFactory.createTrDeleteForum();
        trAddNewPost = CommunityControllersFactory.createTrAddNewPost();
        trDeletePost = CommunityControllersFactory.createTrDeletePost();
        trUpdatePost = CommunityControllersFactory.createTrUpdatePost();
    }

    /**
     * Initialize the medication controllers.
     */
    private void initializeMedicationControllers() {
        trNewPetMedication = MedicationControllersFactory.createTrNewPetMedication();
        trObtainAllPetMedications = MedicationControllersFactory.createTrObtainAllPetMedications();
        trDeleteMedication = MedicationControllersFactory.createTrDeleteMedication();
        trUpdateMedication = MedicationControllersFactory.createTrUpdateMedication();
    }


    /**
     * Initialize the views of this activity.
     */
    private void initializeActivity() {
        initializeActionbar();
        initializeActionDrawerToggle();
        setUpNavigationDrawer();
        setStartFragment();
        setFloatingButtonListener();
        hideWindowSoftKeyboard();
    }

    /**
     * Set the floating button listener.
     */
    private void setFloatingButtonListener() {
        floatingActionButton.setOnClickListener(v -> {
            if (actualFragment instanceof MyPetsFragment) {
                addPet();
            } else if (actualFragment instanceof CommunityFragment){
                createGroupDialog();
            } else if (actualFragment instanceof InfoGroupFragment){
                if (InfoGroupFragment.getGroup().isUserSubscriber(user)) {
                    createForumDialog();
                } else {
                    Toast toast = Toast.makeText(this, getString(R.string.should_be_subscribed), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    /**
     * Create the forum dialog.
     */
    private void createForumDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(this),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.add_new_forum_title);
        dialog.setMessage(R.string.add_new_forum_description);

        View newForumDialog = getLayoutInflater().inflate(R.layout.new_forum_dialog, null);
        dialog.setView(newForumDialog);

        AlertDialog alertDialog = dialog.create();
        setAddForumButtonListener(alertDialog, newForumDialog);

        alertDialog.show();
    }

    /**
     * Set the add button listener.
     * @param alertDialog The alert dialog
     * @param newForumDialog The new forum dialog
     */
    private void setAddForumButtonListener(AlertDialog alertDialog, View newForumDialog) {
        MaterialButton btnAddForum = newForumDialog.findViewById(R.id.btnAddForum);
        btnAddForum.setOnClickListener(v -> {
            TextInputLayout forumName = newForumDialog.findViewById(R.id.addForumName);
            TextInputLayout forumTags = newForumDialog.findViewById(R.id.addForumTags);

            boolean isCorrect = checkEmptyTitle(forumName);
            isCorrect = isCorrect && checkTagPattern(forumTags);

            if (isCorrect) {
                String[] tags = Objects.requireNonNull(forumTags.getEditText()).getText().toString().split(",");
                createForum(InfoGroupFragment.getGroup(), Objects.requireNonNull(forumName.getEditText()).getText()
                        .toString(), new ArrayList<>(Arrays.asList(tags)));
                alertDialog.dismiss();
                ForumsFragment.showForums();
            }
        });
    }

    /**
     * Create the forum.
     * @param group The group to add the forum
     * @param forumName The name of the forum
     * @param forumTags The tags of the forum
     */
    private void createForum(Group group, String forumName, List<String> forumTags) {
        trAddNewForum.setUser(user);
        trAddNewForum.setForumName(forumName);
        trAddNewForum.setTags(forumTags);
        trAddNewForum.setGroup(group);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        String strData = dateFormat.format(date);

        trAddNewForum.setCreationDate(DateTime.Builder.buildFullString(strData));

        try {
            trAddNewForum.execute();
        } catch (UserNotSubscribedException | GroupNotExistingException | ForumCreatedBeforeGroupException e) {
            Toast toast = Toast.makeText(this, getString(R.string.should_be_subscribed), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Create the group dialog.
     */
    private void createGroupDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(this),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.add_new_group_title);
        dialog.setMessage(R.string.add_new_group_description);

        View newGroupDialog = getLayoutInflater().inflate(R.layout.new_group_dialog, null);
        dialog.setView(newGroupDialog);

        AlertDialog alertDialog = dialog.create();
        setAddGroupButtonListener(alertDialog, newGroupDialog);

        alertDialog.show();
    }

    /**
     * Set the add group button listener.
     * @param alertDialog The alert dialog
     * @param newGroupDialog The new group layout
     */
    private void setAddGroupButtonListener(AlertDialog alertDialog, View newGroupDialog) {
        MaterialButton btnAddGroup = newGroupDialog.findViewById(R.id.btnAddGroup);

        btnAddGroup.setOnClickListener(v -> {
            TextInputLayout groupName = newGroupDialog.findViewById(R.id.addGroupName);
            TextInputLayout groupDescription = newGroupDialog.findViewById(R.id.addDescription);
            TextInputLayout groupTags = newGroupDialog.findViewById(R.id.addTags);

            boolean isCorrect = checkEmptyTitle(groupName);
            isCorrect = isCorrect && checkTagPattern(groupTags);

            if (isCorrect) {
                String[] tags = Objects.requireNonNull(groupTags.getEditText()).getText().toString().split(",");
                createGroup(Objects.requireNonNull(groupName.getEditText()).getText().toString(),
                    Objects.requireNonNull(groupDescription.getEditText()).getText().toString(),
                    new ArrayList<>(Arrays.asList(tags)));
                alertDialog.dismiss();
            }
        });
    }

    /**
     * Check whether the tag follows the correct pattern.
     * @param tags Tags to check
     * @return True if the pattern is followed
     */
    private boolean checkTagPattern(TextInputLayout tags) {
        if (!Objects.requireNonNull(tags.getEditText()).getText().toString().matches(TAG_REGEX)) {
            tags.setErrorEnabled(true);
            tags.setError(getString(R.string.tag_not_valid));
            return false;
        }
        return true;
    }

    /**
     * Check whether the title is empty.
     * @param name The name to check
     * @return True if the field is not empty
     */
    private boolean checkEmptyTitle(TextInputLayout name) {
        if ("".equals(Objects.requireNonNull(name.getEditText()).getText().toString())) {
            name.setErrorEnabled(true);
            name.setError(getString(R.string.non_empty_field));
            return false;
        }

        return true;
    }

    /**
     * Hides the soft keyboard.
     */
    public void hideWindowSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Hide the floating button.
     */
    public static void hideFloatingButton() {
        floatingActionButton.hide();
    }

    /**
     * Show the floating button.
     */
    public static void showFloatingButton() {
        floatingActionButton.show();
    }

    /**
     * Enters the fragment to create a pet.
     */
    public void addPet() {
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
        navigationView.getHeaderView(0).setOnClickListener(v -> {
            toolbar.setTitle(R.string.user_profile_image);
            Bitmap userProfileImage = user.getUserProfileImage();
            Drawable drawable = getDrawable(R.drawable.user_icon_sample);

            if (userProfileImage != null) {
                drawable = new BitmapDrawable(getResources(), userProfileImage);
            }

            ImageZoomFragment imageZoomFragment = new ImageZoomFragment(drawable);
            ImageZoomFragment.setIsMainActivity(true);
            floatingActionButton.hide();
            drawerLayout.closeDrawers();
            changeFragment(imageZoomFragment);
        });
    }

    /**
     * Sets up the new fragment.
     * @param title Title to display in the top bar
     * @param id Id of the navigation item
     */
    private void setUpNewFragment(CharSequence title, int id) {
        toolbar.setTitle(title);
        toolbar.setContentDescription(title);

        if (id == R.id.navigationMyPets || id == R.id.navigationPetsCommunity) {
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
                toolbar.setTitle(R.string.navigation_my_pets);
                changeFromImageZoom();
                return true;
            } else if (actualFragment instanceof InfoGroupFragment) {
                toolbar.setTitle(R.string.navigation_pets_community);
                floatingActionButton.show();
                changeFragment(new CommunityFragment());
                return true;
            } else if (actualFragment instanceof PostsFragment) {
                Group group = PostsFragment.getForum().getGroup();
                toolbar.setTitle(group.getName());
                InfoGroupFragment.setGroup(group);
                changeFragment(new InfoGroupFragment());
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
     * Change to next fragment from an ImageZoomFragment.
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
    public void setToolbar(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void addSubscription(Group group) {
        trAddSubscription.setUser(user);
        trAddSubscription.setGroup(group);

        try {
            trAddSubscription.execute();
        } catch (GroupNotExistingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubscription(Group group) {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(group);
        try {
            trDeleteSubscription.execute();
        } catch (GroupNotExistingException | NotSubscribedException | OwnerCannotDeleteSubscriptionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteForum(Forum forum) {
        trDeleteForum.setUser(user);
        trDeleteForum.setGroup(forum.getGroup());
        trDeleteForum.setForum(forum);
        try {
            trDeleteForum.execute();
        } catch (ForumNotFoundException | NotForumOwnerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showForum(PostsFragment postsFragment) {
        changeFragment(postsFragment);
    }

    @Override
    public void addNewPost(Forum forum, String postText) {
        trAddNewPost.setUser(user);
        trAddNewPost.setPostText(postText);
        trAddNewPost.setForum(forum);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);

        trAddNewPost.setPostCreationDate(DateTime.Builder.buildFullString(strData));

        try {
            trAddNewPost.execute();
        } catch (PostAlreadyExistingException | ForumNotFoundException | PostCreatedBeforeForumException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePost(Forum forum, DateTime postCreationDate) {
        trDeletePost.setUser(user);
        trDeletePost.setPostCreationDate(postCreationDate);
        trDeletePost.setForum(forum);
        try {
            trDeletePost.execute();
        } catch (ForumNotFoundException | PostNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePost(Post postToUpdate, String newText) {
        trUpdatePost.setUser(user);
        trUpdatePost.setPost(postToUpdate);
        trUpdatePost.setNewText(newText);
        try {
            trUpdatePost.execute();
        } catch (NotPostOwnerException | ForumNotFoundException | PostNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePetProfileImage(Pet actualPet) {
        user.updatePetProfileImage(actualPet);
    }

    @Override
    public void newPersonalEvent(Pet pet, String description, String dateTime) throws ExecutionException, InterruptedException {
        System.out.println("Token " + pet.getOwner().getGoogleCalendarToken());
        trNewPersonalEvent.setPet(pet);
        trNewPersonalEvent.setEvent(new Event(description, DateTime.Builder.buildFullString(dateTime)));
        trNewPersonalEvent.execute();

    }

    @Override
    public void deletePersonalEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        trDeletePersonalEvent.setPet(pet);
        trDeletePersonalEvent.setEvent(event);
        trDeletePersonalEvent.execute();
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
    public void addWeightForDate(Pet pet, double newWeight, String date) {
        trAddNewWeight.setUser(user);
        trAddNewWeight.setPet(pet);
        trAddNewWeight.setNewWeight(newWeight);

        DateTime dateTime = DateTime.Builder.buildDateString(date);
        trAddNewWeight.setDateTime(dateTime);
        try {
            trAddNewWeight.execute();
        } catch (NotPetOwnerException e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        hideWindowSoftKeyboard();
    }

    @Override
    public void deleteWeightForDate(Pet pet, String date) {
        trDeleteWeight.setUser(user);
        trDeleteWeight.setPet(pet);
        trDeleteWeight.setDateTime(DateTime.Builder.buildDateString(date));
        try {
            trDeleteWeight.execute();
        } catch (NotPetOwnerException e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWashFrequencyForDate(Pet pet, int newWashFrequency, String date) {
        trAddNewWashFrequency.setUser(user);
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setDateTime(DateTime.Builder.buildDateString(date));
        trAddNewWashFrequency.setNewWashFrequency(newWashFrequency);
        try {
            trAddNewWashFrequency.execute();
        } catch (NotPetOwnerException e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        hideWindowSoftKeyboard();
    }

    @Override
    public void deleteWashFrequencyForDate(Pet pet, String date) {
        trDeleteWashFrequency.setUser(user);
        trDeleteWashFrequency.setPet(pet);
        trDeleteWashFrequency.setDateTime(DateTime.Builder.buildDateString(date));
        try {
            trDeleteWashFrequency.execute();
        } catch (NotPetOwnerException e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPetMeal(Pet pet, Meals meal) throws MealAlreadyExistingException {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(pet);
        trNewPetMeal.setMeal(meal);
        trNewPetMeal.execute();
    }

    @Override
    public void updatePetMeal(Pet pet, Meals meal, String newDate, boolean updatesDate) {
        trUpdateMeal.setUser(user);
        trUpdateMeal.setPet(pet);
        trUpdateMeal.setMeal(meal);
        System.out.println("Meal date : " + meal.getDateTime() + " meal name : " + meal.getMealName());
        if (updatesDate) {
            trUpdateMeal.setNewDate(newDate);
        }
        trUpdateMeal.execute();
    }

    @Override
    public void deletePetMeal(Pet pet, Meals meal) {
        trDeleteMeal.setUser(user);
        trDeleteMeal.setPet(pet);
        trDeleteMeal.setMeal(meal);
        trDeleteMeal.execute();
    }

    @Override
    public void obtainAllPetMeals(Pet pet) {
        trObtainAllPetMeals.setUser(user);
        trObtainAllPetMeals.setPet(pet);
        trObtainAllPetMeals.execute();
    }

    @Override
    public void addPetMedication(Pet pet, Medication medication) {
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        try {
            trNewPetMedication.execute();
        } catch (MedicationAlreadyExistingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePetMedication(Pet pet, Medication medication, String newDate, boolean updatesDate,
                                    String newName, boolean updatesName) {
        trUpdateMedication.setUser(user);
        trUpdateMedication.setPet(pet);
        trUpdateMedication.setMedication(medication);
        if (updatesDate) {
            trUpdateMedication.setNewDate(newDate);
        }
        if (updatesName) {
            trUpdateMedication.setNewName(newName);
        }
        try {
            trUpdateMedication.execute();
        } catch (InterruptedException | ExecutionException | MedicationAlreadyExistingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetMedication(Pet pet, Medication medication) {
        trDeleteMedication.setUser(user);
        trDeleteMedication.setPet(pet);
        trDeleteMedication.setMedication(medication);
        try {
            trDeleteMedication.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainAllPetMedications(Pet pet) {
        trObtainAllPetMedications.setUser(user);
        trObtainAllPetMedications.setPet(pet);
        try {
            trObtainAllPetMedications.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
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
        Thread askPermissionThread = ThreadFactory.createAskPermissionThread(this);

        askPermissionThread.start();

        try {
            askPermissionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        ((ImageZoomFragment) actualFragment).setDrawable(drawable);
        ImageZoomFragment.setIsDefaultImage(false);

        if (ImageZoomFragment.isMainActivity()) {
            updateUserImage(drawable);
        } else {
            InfoPetFragment.setIsDefaultPetImage(false);
        }

        if (ImageZoomFragment.isMainActivity()) {
            updateUserProfileImage(user.getUserProfileImage());
        }
    }

    /**
     * Updates user profile image.
     * @param bitmap The bitmap of the profile image
     */
    private void updateUserProfileImage(Bitmap bitmap) {
        user.setUserProfileImage(bitmap);
        setUpNavigationHeader();
        byte[] imageBytes = ImageManager.getImageBytes(bitmap);
        Thread writeUserImageThread = ThreadFactory.createWriteImageThread(ImageManager.USER_PROFILE_IMAGES_PATH,
            user.getUsername(), imageBytes);
        writeUserImageThread.start();
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

    /**
     * Schedule a notification.
     * @param context The context
     * @param text The notification's text
     * @param title The notification's title
     * @param time The alarm time of the notification
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scheduleNotification(Context context, long time, String title, String text) {
        Notification notification = new Notification(title, text, new Date(time), Long.toString(time));
        notification.setNotificationID(notificationId);
        notification.setRequestCode(requestCode);
        user.addNotification(notification);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(getString(R.string.title), title);
        intent.putExtra(getString(R.string.text), text);
        intent.putExtra(getString(R.string.notificationid), Integer.toString(notificationId));
        notificationId++;
        PendingIntent pending = PendingIntent.getBroadcast(context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        requestCode++;
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert manager != null;
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pending);
    }

    /**
     * Cancel a notification.
     * @param context The context
     * @param notification The notification
     */
    public void cancelNotification(Context context, Notification notification) {
        Notification deleted = user.getNotification(notification);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(getString(R.string.title), deleted.getTitle());
        intent.putExtra(getString(R.string.text), deleted.getText());
        intent.putExtra(getString(R.string.notificationid) , deleted.getNotificationID());
        PendingIntent pending = PendingIntent.getBroadcast(context, deleted.getRequestCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        user.deleteNotification(notification);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert manager != null;
        manager.cancel(pending);
    }

    @Override
    public void newPeriodicNotification(Pet selectedPet, int periodicity, String reasonText, DateTime dateTime)
            throws ParseException, UserIsNotOwnerException, ExecutionException, InterruptedException {

        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPeriodicity(periodicity);
        trNewPeriodicNotification.setPet(selectedPet);
        trNewPeriodicNotification.setEvent(new Event(reasonText, dateTime));
        trNewPeriodicNotification.execute();

    }

    @Override
    public void deletePeriodicNotification(Pet selectedPet, Event event, User user)
            throws ParseException, UserIsNotOwnerException, ExecutionException, InterruptedException {

        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setPet(selectedPet);
        trDeletePeriodicNotification.setEvent(event);
        trDeletePeriodicNotification.execute();

    }

    /**
     * Schedule a periodic notification.
     * @param context The context
     * @param text The notification's text
     * @param title The notification's title
     * @param time The alarm time of the notification
     * @param period The period of the alarm
     */
    @Override
    public void schedulePeriodicNotification(Context context, long time, String title, String text, int period) {
        Notification notification = new Notification(title, text, new Date(time), Long.toString(time));
        notification.setNotificationID(notificationId);
        notification.setRequestCode(requestCode);
        user.addNotification(notification);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(getString(R.string.title), title);
        intent.putExtra(getString(R.string.text), text);
        intent.putExtra(getString(R.string.notificationid), Integer.toString(notificationId));
        notificationId++;
        PendingIntent pending = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        requestCode++;
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert manager != null;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time,
                AlarmManager.INTERVAL_DAY*period, pending);
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserForSettings() {
        return user;
    }

    public List<Group> getGroups() {
        return groups;
    }
  
    @Override
    public boolean usernameExists(String newUsername) {
        trExistsUsername.setNewUsername(newUsername);
        trExistsUsername.execute();
        return trExistsUsername.isResult();
    }

    @Override
    public void changeUsername(String newUsername) {
        trChangeUsername.setUser(user);
        try {
            trChangeUsername.setNewUsername(newUsername);
        } catch (SameUsernameException e) {
            Toast toast = Toast.makeText(this, "No change", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        trChangeUsername.execute();
        View navigationHeader = navigationView.getHeaderView(0);
        TextView userName = navigationHeader.findViewById(R.id.lblUserName);
        userName.setText(newUsername);
    }

    @Override  
    public void changeMail(String newEmail) {
        trChangeMail.setUser(user);
        trChangeMail.setMail(newEmail);
        trChangeMail.execute();
        View navigationHeader = navigationView.getHeaderView(0);
        TextView userEmail = navigationHeader.findViewById(R.id.lblUserEmail);
        userEmail.setText(newEmail);
    }

    @Override
    public void updateUserImage(Drawable drawable) {
        trUpdateUserImage.setUser(user);
        trUpdateUserImage.setImage(((BitmapDrawable) drawable).getBitmap());
        trUpdateUserImage.execute();
    }

    @Override
    public SortedSet<Group> getAllGroups() {
        trObtainAllGroups.execute();
        SortedSet<Group> groups = trObtainAllGroups.getResult();

        for (Group group : groups) {
            if (group.getSubscribers().containsKey(user.getUsername())) {
                user.addSubscribedGroupSimple(group);
            }
        }

        return groups;
    }

    /**
     * Method responsible for creating a new group.
     * @param groupName The name of the new group
     * @param description The description of the new group
     * @param tags The tags of the new group
     */
    private void createGroup(String groupName, String description, List<String> tags) {
        trCreateNewGroup.setGroupName(groupName);
        trCreateNewGroup.setOwner(user);
        trCreateNewGroup.setDescription(description);
        trCreateNewGroup.setTags(tags);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();
        String strData = dateFormat.format(date);

        trCreateNewGroup.setCreationDate(DateTime.Builder.buildDateString(strData));

        try {
            trCreateNewGroup.execute();
        } catch (GroupAlreadyExistingException e) {
            Toast toast = Toast.makeText(this, getString(R.string.group_already_created), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void deleteGroup(String groupName) {
        trDeleteGroup.setGroupName(groupName);
        try {
            trDeleteGroup.execute();
        } catch (GroupNotFoundException e) {
            Toast toast = Toast.makeText(this, R.string.group_not_found, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void showGroupFragment(InfoGroupFragment infoGroupFragment) {
        changeFragment(infoGroupFragment);
    }

    /**
     * Setter of the google calendar token attribute.
     * @param googleCalendarToken The google calendar token to set
     */
    public static void setGoogleCalendarToken(String googleCalendarToken) {
        MainActivity.googleCalendarToken = googleCalendarToken;
    }
}
