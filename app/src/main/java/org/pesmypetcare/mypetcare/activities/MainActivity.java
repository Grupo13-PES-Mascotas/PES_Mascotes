package org.pesmypetcare.mypetcare.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.BuildConfig;
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
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetExercise;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.login.AsyncResponse;
import org.pesmypetcare.mypetcare.activities.fragments.login.MyAsyncTask;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.settings.NewPasswordInterfaceCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsMenuFragment;
import org.pesmypetcare.mypetcare.activities.fragments.walks.WalkCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.walks.WalkFragment;
import org.pesmypetcare.mypetcare.activities.threads.GetPetImageRunnable;
import org.pesmypetcare.mypetcare.activities.threads.ThreadFactory;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.controllers.community.CommunityControllersFactory;
import org.pesmypetcare.mypetcare.controllers.community.TrAddGroupImage;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewForum;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewPost;
import org.pesmypetcare.mypetcare.controllers.community.TrAddSubscription;
import org.pesmypetcare.mypetcare.controllers.community.TrCreateNewGroup;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteForum;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteGroup;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteGroupImage;
import org.pesmypetcare.mypetcare.controllers.community.TrDeletePost;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteSubscription;
import org.pesmypetcare.mypetcare.controllers.community.TrGetGroupImage;
import org.pesmypetcare.mypetcare.controllers.community.TrGetPostImage;
import org.pesmypetcare.mypetcare.controllers.community.TrLikePost;
import org.pesmypetcare.mypetcare.controllers.community.TrObtainAllGroups;
import org.pesmypetcare.mypetcare.controllers.community.TrReportPost;
import org.pesmypetcare.mypetcare.controllers.community.TrUnlikePost;
import org.pesmypetcare.mypetcare.controllers.community.TrUpdatePost;
import org.pesmypetcare.mypetcare.controllers.event.EventControllersFactory;
import org.pesmypetcare.mypetcare.controllers.event.TrDeletePeriodicNotification;
import org.pesmypetcare.mypetcare.controllers.event.TrDeletePersonalEvent;
import org.pesmypetcare.mypetcare.controllers.event.TrNewPeriodicNotification;
import org.pesmypetcare.mypetcare.controllers.event.TrNewPersonalEvent;
import org.pesmypetcare.mypetcare.controllers.exercise.ExerciseControllersFactory;
import org.pesmypetcare.mypetcare.controllers.exercise.TrAddExercise;
import org.pesmypetcare.mypetcare.controllers.exercise.TrAddWalk;
import org.pesmypetcare.mypetcare.controllers.exercise.TrDeleteExercise;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetAllExercises;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetAllWalks;
import org.pesmypetcare.mypetcare.controllers.exercise.TrUpdateExercise;
import org.pesmypetcare.mypetcare.controllers.meals.MealsControllersFactory;
import org.pesmypetcare.mypetcare.controllers.meals.TrDeleteMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrNewPetMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrObtainAllPetMeals;
import org.pesmypetcare.mypetcare.controllers.meals.TrUpdateMeal;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.MedicalProfileControllersFactory;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrAddNewPetIllness;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrAddNewPetVaccination;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrDeletePetIllness;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrDeletePetVaccination;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrObtainAllPetIllness;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrObtainAllPetVaccinations;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrUpdatePetIllness;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrUpdatePetVaccination;
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
import org.pesmypetcare.mypetcare.controllers.pethealth.TrGetAllWeights;
import org.pesmypetcare.mypetcare.controllers.user.EmptyMessagingTokenException;
import org.pesmypetcare.mypetcare.controllers.user.TrChangeMail;
import org.pesmypetcare.mypetcare.controllers.user.TrChangePassword;
import org.pesmypetcare.mypetcare.controllers.user.TrChangeUsername;
import org.pesmypetcare.mypetcare.controllers.user.TrDeleteUser;
import org.pesmypetcare.mypetcare.controllers.user.TrExistsUsername;
import org.pesmypetcare.mypetcare.controllers.user.TrObtainUser;
import org.pesmypetcare.mypetcare.controllers.user.TrObtainUserImage;
import org.pesmypetcare.mypetcare.controllers.user.TrSendFirebaseMessagingToken;
import org.pesmypetcare.mypetcare.controllers.user.TrUpdateUserImage;
import org.pesmypetcare.mypetcare.controllers.user.UserControllersFactory;
import org.pesmypetcare.mypetcare.controllers.vetvisits.TrDeleteVetVisit;
import org.pesmypetcare.mypetcare.controllers.vetvisits.TrNewVetVisit;
import org.pesmypetcare.mypetcare.controllers.vetvisits.TrObtainAllVetVisits;
import org.pesmypetcare.mypetcare.controllers.vetvisits.TrUpdateVetVisit;
import org.pesmypetcare.mypetcare.controllers.vetvisits.VetVisitsControllersFactory;
import org.pesmypetcare.mypetcare.controllers.washes.TrDeleteWash;
import org.pesmypetcare.mypetcare.controllers.washes.TrNewPetWash;
import org.pesmypetcare.mypetcare.controllers.washes.TrObtainAllPetWashes;
import org.pesmypetcare.mypetcare.controllers.washes.TrUpdateWash;
import org.pesmypetcare.mypetcare.controllers.washes.WashesControllersFactory;
import org.pesmypetcare.mypetcare.databinding.ActivityMainBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumCreatedBeforeGroupException;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.forums.UserNotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotGroupOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.OwnerCannotDeleteSubscriptionException;
import org.pesmypetcare.mypetcare.features.community.posts.NotLikedPostException;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyLikedException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.PostReportedByAuthorException;
import org.pesmypetcare.mypetcare.features.notification.Notification;
import org.pesmypetcare.mypetcare.features.notification.NotificationReceiver;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.WalkPets;
import org.pesmypetcare.mypetcare.features.pets.events.meals.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.IllnessAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.medication.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisitAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.pets.events.wash.WashAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.SameUsernameException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.mypetcare.utilities.LocationUpdater;
import org.pesmypetcare.mypetcare.utilities.MessagingService;
import org.pesmypetcare.mypetcare.utilities.MessagingServiceCommunication;

import java.io.IOException;
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

/**
 * @author Xavier Campos & Daniel Clemente & Enric Hernando & Albert Pinto
 */
public class MainActivity extends AppCompatActivity implements RegisterPetCommunication, NewPasswordInterfaceCommunication,
    InfoPetCommunication, MyPetsCommunication, SettingsCommunication, CalendarCommunication, ImageZoomCommunication,
    CommunityCommunication, InfoGroupCommunication, WalkCommunication, MessagingServiceCommunication, AsyncResponse {

    public static final int MAIN_ACTIVITY_ZOOM_IDENTIFIER = 0;
    private static final int[] NAVIGATION_OPTIONS = {
        R.id.navigationMyPets, R.id.navigationPetsCommunity, R.id.navigationMyWalks,
        R.id.navigationNearEstablishments, R.id.navigationCalendar, R.id.navigationAchievements,
        R.id.navigationSettings
    };

    private static final Class[] APPLICATION_FRAGMENTS = {
        MyPetsFragment.class, CommunityFragment.class, WalkFragment.class,
        NotImplementedFragment.class, CalendarFragment.class, NotImplementedFragment.class,
        SettingsMenuFragment.class
    };
    public static final String TAG_REGEX = "^[a-zA-Z0-9,]*$";
    private static final String WALKING_PREFERENCES = "Walking";
    public static final String GROUPS_SHARED_PREFERENCES = "Groups";
    private static final String GOOGLE_CALENDAR_SHARED_PREFERENCES = "GoogleCalendar";
    private static final String START_WALKING_DATE_TIME = "startWalkingDateTime";
    public static final String ACTUAL_WALK = "ActualWalk";
    public static final int LAT = 0;
    public static final int LNG = 1;

    private static boolean enableLoginActivity = true;
    private static FloatingActionButton floatingActionButton;
    private static FloatingActionButton shareAppButton;
    private static FirebaseAuth mAuth;
    private static Fragment actualFragment;
    private static User user;
    private static SharedPreferences sharedpreferences;
    private static Resources resources;
    private static NavigationView navigationView;
    private static int[] countImagesNotFound;
    private static List<Group> groups;
    private static int notificationId;
    private static int requestCode;
    private static FragmentManager fragmentManager;
    private static SharedPreferences walkingSharedPreferences;
    private static Context context;
    private static int fragmentRequestCode;

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
    private TrNewPetWash trNewPetWash;
    private TrObtainAllPetWashes trObtainAllPetWashes;
    private TrDeleteWash trDeleteWash;
    private TrUpdateWash trUpdateWash;
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
    private TrLikePost trLikePost;
    private TrUnlikePost trUnlikePost;
    private TrReportPost trReportPost;
    private TrObtainAllVetVisits trObtainAllVetVisits;
    private TrNewVetVisit trNewVetVisit;
    private TrDeleteVetVisit trDeleteVetVisit;
    private TrUpdateVetVisit trUpdateVetVisit;
    private TrAddExercise trAddExercise;
    private TrDeleteExercise trDeleteExercise;
    private TrUpdateExercise trUpdateExercise;
    private TrAddNewPetVaccination trAddNewPetVaccination;
    private TrObtainAllPetVaccinations trObtainAllPetVaccinations;
    private TrUpdatePetVaccination trUpdatePetVaccination;
    private TrDeletePetVaccination trDeletePetVaccination;
    private TrObtainAllPetIllness trObtainAllPetIllness;
    private TrDeletePetIllness trDeletePetIllness;
    private TrAddNewPetIllness trAddNewPetIllness;
    private TrUpdatePetIllness trUpdatePetIllness;
    private TrAddWalk trAddWalk;
    private TrGetAllWalks trGetAllWalks;
    private TrGetAllExercises trGetAllExercises;
    private TrObtainUserImage trObtainUserImage;
    private TrGetPostImage trGetPostImage;
    private TrAddGroupImage trAddGroupImage;
    private TrDeleteGroupImage trDeleteGroupImage;
    private TrSendFirebaseMessagingToken trSendFirebaseMessagingToken;
    private TrGetGroupImage trGetGroupImage;
    private TrGetAllWeights trGetAllWeights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resources = getResources();

        setAttributes();

        if (enableLoginActivity) {
            askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        makeLogin();

        ExecutorService mainActivitySetUp = Executors.newCachedThreadPool();
        mainActivitySetUp.execute(this::initializeMainActivity);
        mainActivitySetUp.shutdown();

        try {
            mainActivitySetUp.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setMessagingToken();
    }

    /**
     * Set the token messaging.
     */
    private void setMessagingToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful() && user != null) {
                sendMessageToken(Objects.requireNonNull(task.getResult()).getToken());
            }
        });
    }

    /**
     * Set the attributes of the Main Activity.
     */
    private void setAttributes() {
        sharedpreferences = getSharedPreferences(GOOGLE_CALENDAR_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        walkingSharedPreferences = getSharedPreferences(ACTUAL_WALK, Context.MODE_PRIVATE);

        notificationId = 0;
        requestCode = 0;
        fragmentManager = getSupportFragmentManager();

        context = this;
    }

    /**
     * Initialize the Main Activity.
     */
    private void initializeMainActivity() {
        initializeControllers();
        getComponents();

        ImageManager.setPetDefaultImage(getResources().getDrawable(R.drawable.single_paw, null));
        initializeCurrentUser();
        initializeActivity();
        setUpNavigationImage();

        LocationUpdater.setContext(this);
        MessagingService.setCommunication(this);
    }

    /**
     * Initializes the current user.
     */
    private void initializeCurrentUser() {
        if (enableLoginActivity && mAuth.getCurrentUser() != null) {
            try {
                initializeUser();
                refreshGoogleCalendarToken();
                //changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
            } catch (PetRepeatException e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing),
                    Toast.LENGTH_LONG);
                toast.show();
            }

            if (mAuth.getCurrentUser() != null) {
                mAuth.getCurrentUser().getIdToken(false).addOnCompleteListener(task -> {
                    user.setToken(Objects.requireNonNull(task.getResult()).getToken());
                });
            }
        }
    }

    /**
     * Refresh the google calendar token.
     */
    private void refreshGoogleCalendarToken() {
        this.getGoogleToken();
    }

    /**
     * Get the google calendar token.
     */
    public void getGoogleToken() {
        String googleEmail = sharedpreferences.getString("GoogleEmail", "");
        String scopes = sharedpreferences.getString("GoogleScopes", "");
        if (!"".equals(googleEmail)) {
            MyAsyncTask asyncTask = new MyAsyncTask(googleEmail, scopes, this.getBaseContext());
            asyncTask.delegate = this;
            asyncTask.execute();
        }
    }

    /**
     * Get the components of the activity.
     */
    private void getComponents() {
        drawerLayout = binding.activityMainDrawerLayout;
        navigationView = binding.navigationView;
        floatingActionButton = binding.flAddPet;
        shareAppButton = binding.flShareApp;
    }

    /**
     * Make the login to the application.
     */
    private void makeLogin() {
        if (enableLoginActivity && mAuth.getCurrentUser() == null) {
            if (BuildConfig.DEBUG) {
                Log.d("MainActivity", "User is not logged in, starting log in activity");
            }
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

    /**
     * Get the fragment manager.
     * @return The fragment manager
     */
    public static FragmentManager getApplicationFragmentManager() {
        return fragmentManager;
    }

    /**
     * Initialize the current.
     * @throws PetRepeatException The pet has already been registered
     */
    private void initializeUser() throws PetRepeatException {
        trObtainUser.setUid(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        trObtainUser.setToken("token");

        try {
            trObtainUser.execute();
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }

        user = trObtainUser.getResult();

        for (Pet pet : user.getPets()) {
            obtainAllPetWeights(pet);
            obtainAllPetMeals(pet);
            obtainAllPetMedications(pet);
            obtainAllPetVetVisits(pet);
            obtainAllPetWashes(pet);
            obtainAllPetVaccinations(pet);
            obtainAllPetIllnesses(pet);
            obtainAllPetExercises(pet);
        }

        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Thread petsImagesThread = createPetsImagesThread();
        Thread updatePetImagesThread = createUpdatePetImagesThread(petsImagesThread);
        updatePetImagesThread.start();
        setUpNavigationHeader();
    }

    /**
     * Obtain all the pet weights.
     * @param pet The pet to get the weights from
     */
    private void obtainAllPetWeights(Pet pet) {
        trGetAllWeights.setUser(user);
        trGetAllWeights.setPet(pet);
        try {
            trGetAllWeights.execute();
        } catch (NotPetOwnerException e) {
            e.printStackTrace();
        }
    }
    /**
     * Obtain all the pet exercises.
     * @param pet The pet to get the exercises from
     */

    private void obtainAllPetExercises(Pet pet) {
        trGetAllExercises.setUser(user);
        trGetAllExercises.setPet(pet);

        try {
            trGetAllExercises.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Exercise exercise : trGetAllExercises.getResult()) {
            pet.addExercise(exercise);
        }
    }

    /**
     * Update the location.
     * @param latitude The latitude
     * @param longitude The longitude
     */
    public static void updateLocation(double latitude, double longitude) {
        SharedPreferences.Editor editor = walkingSharedPreferences.edit();
        String username = user.getUsername();
        int index = walkingSharedPreferences.getInt(username, 0);
        editor.putString(username + "_" + index, latitude + " " + longitude);
        editor.putInt(username, index + 1);
        editor.apply();
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

            if (imagesNotFound == nUserPets && nUserPets != 0) {
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
     * @param nUserPets The number of pets the user has got
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
        initializeWashControllers();
        initializeCommunityControllers();
        initializeMedicationControllers();
        initializeVetVisitsControllers();
        initializeExerciseControllers();
        initializeMedicalProfileControllers();
    }

    /**
     * Initialize the medical profile controllers.
     */
    private void initializeMedicalProfileControllers() {
        trAddNewPetVaccination = MedicalProfileControllersFactory.createTrAddNewVaccination();
        trObtainAllPetVaccinations = MedicalProfileControllersFactory.createTrObtainAllPetVaccinations();
        trUpdatePetVaccination = MedicalProfileControllersFactory.createTrUpdatePetVaccination();
        trDeletePetVaccination = MedicalProfileControllersFactory.createTrDeletePetVaccinations();
        trObtainAllPetIllness = MedicalProfileControllersFactory.createTrObtainAllPetIllnesses();
        trDeletePetIllness = MedicalProfileControllersFactory.createTrDeletePetIllness();
        trAddNewPetIllness = MedicalProfileControllersFactory.createTrAddNewIllness();
        trUpdatePetIllness = MedicalProfileControllersFactory.createTrPetIllness();
    }

    /**
     * Initialize the exercise controllers.
     */
    private void initializeExerciseControllers() {
        trAddExercise = ExerciseControllersFactory.createTrAddExercise();
        trDeleteExercise = ExerciseControllersFactory.createTrDeleteExercise();
        trUpdateExercise = ExerciseControllersFactory.createTrUpdateExercise();
        trAddWalk = ExerciseControllersFactory.createTrAddWalk();
        trGetAllWalks = ExerciseControllersFactory.createTrGetAllWalks();
        trGetAllExercises = ExerciseControllersFactory.createTrGetAllExercises();
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
        trObtainUserImage = UserControllersFactory.createTrObtainUserImage();
        trSendFirebaseMessagingToken = UserControllersFactory.createTrSendFirebaseMessagingToken();
    }

    /**
     * Initialize the pet health controllers.
     */
    private void initializePetHealthControllers() {
        trAddNewWeight = PetHealthControllersFactory.createTrAddNewWeight();
        trDeleteWeight = PetHealthControllersFactory.createTrDeleteWeight();
        trAddNewWashFrequency = PetHealthControllersFactory.createTrAddNewWashFrequency();
        trDeleteWashFrequency = PetHealthControllersFactory.createTrDeleteWashFrequency();
        trGetAllWeights = PetHealthControllersFactory.createTrGetAllWeights();
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
     * Initialize the wash controllers.
     */
    private void initializeWashControllers() {
        trNewPetWash = WashesControllersFactory.createTrNewPetWash();
        trObtainAllPetWashes = WashesControllersFactory.createTrObtainAllPetWashes();
        trDeleteWash = WashesControllersFactory.createTrDeleteWash();
        trUpdateWash = WashesControllersFactory.createTrUpdateWash();
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
        trLikePost = CommunityControllersFactory.createTrLikePost();
        trUnlikePost = CommunityControllersFactory.createTrUnlikePost();
        trReportPost = CommunityControllersFactory.createTrReportPost();
        trGetPostImage = CommunityControllersFactory.createTrGetPostImage();
        trAddGroupImage = CommunityControllersFactory.createTrAddGroupImage();
        trDeleteGroupImage = CommunityControllersFactory.createTrDeleteGroupImage();
        trGetGroupImage = CommunityControllersFactory.createTrGetGroupImage();
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
     * Initialize the vet visits controllers.
     */
    private void initializeVetVisitsControllers() {
        trObtainAllVetVisits = VetVisitsControllersFactory.createTrObtainAllVetVisits();
        trNewVetVisit = VetVisitsControllersFactory.createTrNewVetVisit();
        trDeleteVetVisit = VetVisitsControllersFactory.createTrDeleteVetVisit();
        trUpdateVetVisit = VetVisitsControllersFactory.createTrUpdateVetVisit();
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
        setUpShareAppListener();
        hideWindowSoftKeyboard();
    }

    /**
     * Set the share app button listener.
     */
    private void setUpShareAppListener() {
        shareAppButton.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String aux = "Download the app\n";
                aux = aux + "https://drive.google.com/file/d/11oh5KY-V3GAdRjmZECbD5cOD1T8syDJs/view?usp=sharing" + getBaseContext().getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, aux);
                startActivity(i);
            } catch (Exception e) {
            }
        });
    }

    /**
     * Set the floating button listener.
     */
    private void setFloatingButtonListener() {
        floatingActionButton.setOnClickListener(v -> {
            if (actualFragment instanceof MyPetsFragment) {
                hideShareAppButton();
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
        } catch (UserNotSubscribedException | ForumCreatedBeforeGroupException | GroupNotFoundException e) {
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
     * Hide the floating button.
     */
    public static void hideShareAppButton() {
        shareAppButton.hide();
    }

    /**
     * Show the floating button.
     */
    public static void showShareAppButton() {
        shareAppButton.show();
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
     * Get the enable of the login activity.
     * @return The neable of the login activity
     */
    public static boolean isEnableLoginActivity() {
        return enableLoginActivity;
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
            ImageZoomFragment.setOrigin(MAIN_ACTIVITY_ZOOM_IDENTIFIER);

            floatingActionButton.hide();
            hideShareAppButton();
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
        fragmentTransaction.commitAllowingStateLoss();
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
        if (index!=0) {
            hideShareAppButton();
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
        switch (ImageZoomFragment.getOrigin()) {
            case MAIN_ACTIVITY_ZOOM_IDENTIFIER:
                Drawable drawable = ImageZoomFragment.getDrawable();
                user.setUserProfileImage(((BitmapDrawable) drawable).getBitmap());
                changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
                break;
            case InfoPetFragment.INFO_PET_ZOOM_IDENTIFIER:
                InfoPetFragment.setPetProfileDrawable(ImageZoomFragment.getDrawable());
                changeFragment(new InfoPetFragment());
                break;
            case InfoGroupFragment.INFO_GROUP_ZOOM_IDENTIFIER:
                changeFragment(new InfoGroupFragment());
                Bitmap bitmap = ((BitmapDrawable) ImageZoomFragment.getDrawable()).getBitmap();
                updateGroupImage(InfoGroupFragment.getGroup(), bitmap);
                break;
            default:
        }
    }

    /**
     * Updates the image of the given group.
     * @param group The group which image has to be updated
     * @param bitmap The new image of the group
     */
    private void updateGroupImage(Group group, Bitmap bitmap) {
        SharedPreferences sharedPreferences = getSharedPreferences(GROUPS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        if (ImageZoomFragment.isImageDeleted()) {
            deleteGroupImage(group);
            executorService.execute(() -> ImageManager.deleteImage(ImageManager.GROUP_IMAGES_PATH, group.getName()));

            editor.remove(group.getName());
        } else {
            addGroupImage(group, bitmap);
            executorService.execute(() -> {
                byte[] imageBytes = ImageManager.getImageBytes(bitmap);
                ImageManager.writeImage(ImageManager.GROUP_IMAGES_PATH, group.getName(), imageBytes);
            });

            editor.putString(group.getName(), DateTime.getCurrentDateTime().toString());
        }

        executorService.shutdown();
        editor.apply();
    }

    /**
     * Adds the given image to the indicated group.
     * @param group The group where the image has to be added
     * @param image The image that has to be added to the groups
     */
    private void addGroupImage(Group group, Bitmap image) {
        trAddGroupImage.setUser(user);
        trAddGroupImage.setGroup(group);
        trAddGroupImage.setImage(image);
        try {
            trAddGroupImage.execute();
        } catch (NotGroupOwnerException | GroupNotFoundException | MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the image from the indicated group.
     * @param group The group which image has to be deleted
     */
    private void deleteGroupImage(Group group) {
        trDeleteGroupImage.setUser(user);
        trDeleteGroupImage.setGroup(group);
        try {
            trDeleteGroupImage.execute();
        } catch (NotGroupOwnerException | GroupNotFoundException e) {
            e.printStackTrace();
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

    /**
     * Set the fragment request code.
     * @param fragmentRequestCode The fragment request code to set
     */
    public static void setFragmentRequestCode(int fragmentRequestCode) {
        MainActivity.fragmentRequestCode = fragmentRequestCode;
    }

    @Override
    public void addSubscription(Group group) {
        trAddSubscription.setUser(user);
        trAddSubscription.setGroup(group);

        try {
            trAddSubscription.execute();
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubscription(Group group) {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(group);
        try {
            trDeleteSubscription.execute();
        } catch (NotSubscribedException | OwnerCannotDeleteSubscriptionException | GroupNotFoundException e) {
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
    public void addNewPost(Forum forum, @Nullable String postText, @Nullable Bitmap postImage) {
        trAddNewPost.setUser(user);
        trAddNewPost.setPostText(postText);
        trAddNewPost.setForum(forum);
        trAddNewPost.setPostImage(postImage);
        trAddNewPost.setPostCreationDate(DateTime.getCurrentDateTime());

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
    public void likePost(Post postToLike) {
        trLikePost.setUser(user);
        trLikePost.setPost(postToLike);

        try {
            trLikePost.execute();
        } catch (PostNotFoundException | PostAlreadyLikedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unlikePost(Post post) {
        trUnlikePost.setUser(user);
        trUnlikePost.setPost(post);

        try {
            trUnlikePost.execute();
        } catch (NotLikedPostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportPost(Post post, String reportMessage) {
        trReportPost.setUser(user);
        trReportPost.setPost(post);
        trReportPost.setReportMessage(reportMessage);
        try {
            trReportPost.execute();
        } catch (PostReportedByAuthorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap findImageByUser(String username) {
        trObtainUserImage.setUsername(username);
        trObtainUserImage.setAccessToken(user.getToken());

        try {
            trObtainUserImage.execute();
        } catch (ExecutionException | InterruptedException | MyPetCareException e) {
            e.printStackTrace();
        }
        Bitmap result = trObtainUserImage.getResult();
        if (result == null) {
            result = BitmapFactory.decodeResource(getResources(), R.drawable.user_icon);
        }
        return result;
    }

    @Override
    public void addPostImage(Post post, Bitmap image) {

    }

    @Override
    public void deletePostImage(Post post) {

    }

    @Override
    public void makeGroupZoomImage(Drawable drawable) {
        floatingActionButton.hide();
        //ImageZoomFragment.setIsMainActivity(false);
        ImageZoomFragment.setOrigin(InfoGroupFragment.INFO_GROUP_ZOOM_IDENTIFIER);

        changeFragment(new ImageZoomFragment(drawable));
    }

    @Override
    public byte[] getImageFromPost(Post post, MessageDisplay messageData) {
        trGetPostImage.setUser(user);
        trGetPostImage.setPost(post);
        trGetPostImage.setMessageData(messageData);
        trGetPostImage.execute();

        return trGetPostImage.getResult();
    }

    @Override
    public void changePetProfileImage(Pet actualPet) {
        user.updatePetProfileImage(actualPet);
    }

    @Override
    public void newPersonalEvent(Pet pet, String description, String dateTime) throws ExecutionException,
        InterruptedException, InvalidFormatException, InvalidFormatException {
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

    public void getMyLocations() {
        LocationUpdater.startRoute();
    }


    @Override
    public void makeZoomImage(Drawable drawable) {
        floatingActionButton.hide();
        ImageZoomFragment.setOrigin(InfoPetFragment.INFO_PET_ZOOM_IDENTIFIER);

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
    public void addPetMeal(Pet pet, Meals meal) {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(pet);
        trNewPetMeal.setMeal(meal);
        try {
            trNewPetMeal.execute();
        } catch (MealAlreadyExistingException | InterruptedException | ExecutionException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePetMeal(Pet pet, Meals meal, String newDate, boolean updatesDate) {
        trUpdateMeal.setUser(user);
        trUpdateMeal.setPet(pet);
        trUpdateMeal.setMeal(meal);
        if (updatesDate) {
            trUpdateMeal.setNewDate(newDate);
        }
        try {
            trUpdateMeal.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetMeal(Pet pet, Meals meal) {
        trDeleteMeal.setUser(user);
        trDeleteMeal.setPet(pet);
        trDeleteMeal.setMeal(meal);
        try {
            trDeleteMeal.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainAllPetMeals(Pet pet) {
        trObtainAllPetMeals.setUser(user);
        trObtainAllPetMeals.setPet(pet);
        try {
            trObtainAllPetMeals.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPetWash(Pet pet, Wash wash) throws WashAlreadyExistingException {
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(pet);
        trNewPetWash.setWash(wash);
        try {
            trNewPetWash.execute();
        } catch (InterruptedException | ExecutionException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePetWash(Pet pet, Wash wash, String newDate, boolean updatesDate) {
        trUpdateWash.setUser(user);
        trUpdateWash.setPet(pet);
        trUpdateWash.setWash(wash);
        if (updatesDate) {
            trUpdateWash.setNewDate(newDate);
        }
        trUpdateWash.execute();
    }

    @Override
    public void deletePetWash(Pet pet, Wash wash) {
        trDeleteWash.setUser(user);
        trDeleteWash.setPet(pet);
        trDeleteWash.setWash(wash);
        try {
            trDeleteWash.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainAllPetWashes(Pet pet) {
        trObtainAllPetWashes.setUser(user);
        trObtainAllPetWashes.setPet(pet);
        trObtainAllPetWashes.execute();
    }

    @Override
    public void addPetMedication(Pet pet, Medication medication) {
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        try {
            trNewPetMedication.execute();
        } catch (MedicationAlreadyExistingException | ExecutionException | InterruptedException
            | InvalidFormatException e) {
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
    public void addPetVetVisit(Pet pet, VetVisit vetVisit) {
        trNewVetVisit.setUser(user);
        trNewVetVisit.setPet(pet);
        trNewVetVisit.setVetVisit(vetVisit);
        try {
            trNewVetVisit.execute();
        } catch (VetVisitAlreadyExistingException | NotPetOwnerException | InterruptedException | ExecutionException
            | InvalidFormatException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePetVetVisit(Pet pet, VetVisit vetVisit, String newDate, boolean updatesDate) {
        trUpdateVetVisit.setUser(user);
        trUpdateVetVisit.setPet(pet);
        trUpdateVetVisit.setVetVisit(vetVisit);
        if (updatesDate) {
            trUpdateVetVisit.setNewDate(newDate);
        }
        try {
            trUpdateVetVisit.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addExercise(Pet pet, String exerciseName, String exerciseDescription, DateTime startExerciseDateTime,
                            DateTime endExerciseDateTime) {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName(exerciseName);
        trAddExercise.setExerciseDescription(exerciseDescription);
        trAddExercise.setStartDateTime(startExerciseDateTime);
        trAddExercise.setEndDateTime(endExerciseDateTime);

        try {
            trAddExercise.execute();
        } catch (NotPetOwnerException | InvalidPeriodException | InterruptedException | ExecutionException
            | InvalidFormatException e) {
            e.printStackTrace();
        }

        InfoPetExercise.showExercises();
    }

    @Override
    public void removeExercise(Pet pet, DateTime dateTime) {
        trDeleteExercise.setUser(user);
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(dateTime);

        try {
            trDeleteExercise.execute();
        } catch (NotPetOwnerException | NotExistingExerciseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetVetVisit(Pet pet, VetVisit vetVisit) {
        trDeleteVetVisit.setUser(user);
        trDeleteVetVisit.setPet(pet);
        trDeleteVetVisit.setVetVisit(vetVisit);
        try {
            trDeleteVetVisit.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateExercise(Pet pet, String exerciseName, String exerciseDescription, DateTime originalDateTime,
                               DateTime startExerciseDateTime, DateTime endExerciseDateTime) {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName(exerciseName);
        trUpdateExercise.setExerciseDescription(exerciseDescription);
        trUpdateExercise.setOriginalStartDateTime(originalDateTime);
        trUpdateExercise.setStartDateTime(startExerciseDateTime);
        trUpdateExercise.setEndDateTime(endExerciseDateTime);

        try {
            trUpdateExercise.execute();
        } catch (NotPetOwnerException | InvalidPeriodException | NotExistingExerciseException | ExecutionException |
            InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPetVaccination(Pet pet, String vaccinationDescription, DateTime vaccinationDate) {
        Vaccination vaccination = new Vaccination(vaccinationDescription, vaccinationDate);
        trAddNewPetVaccination.setUser(user);
        trAddNewPetVaccination.setPet(pet);
        trAddNewPetVaccination.setVaccination(vaccination);
        try {
            trAddNewPetVaccination.execute();
        } catch (NotPetOwnerException | VaccinationAlreadyExistingException | ExecutionException
            | InterruptedException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updatePetVaccination(Pet pet, Vaccination vaccination, String newDate, boolean updatesDate) {
        trUpdatePetVaccination.setUser(user);
        trUpdatePetVaccination.setPet(pet);
        trUpdatePetVaccination.setVaccination(vaccination);
        if (updatesDate) {
            trUpdatePetVaccination.setNewDate(newDate);
        }
        try {
            trUpdatePetVaccination.execute();
        } catch (NotPetOwnerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetVaccination(Pet pet, Vaccination vaccination) {
        trDeletePetVaccination.setUser(user);
        trDeletePetVaccination.setPet(pet);
        trDeletePetVaccination.setVaccination(vaccination);
        try {
            trDeletePetVaccination.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainAllPetVaccinations(Pet pet) {
        trObtainAllPetVaccinations.setUser(user);
        trObtainAllPetVaccinations.setPet(pet);
        try {
            trObtainAllPetVaccinations.execute();
        } catch (NotPetOwnerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPetIllness(Pet pet, String description, String type, String severity, DateTime startDate,
                              DateTime endDate) {
        Illness illness = new Illness(description, startDate, endDate, type, severity);
        trAddNewPetIllness.setUser(user);
        trAddNewPetIllness.setPet(pet);
        trAddNewPetIllness.setIllness(illness);
        try {
            trAddNewPetIllness.execute();
        } catch (NotPetOwnerException | IllnessAlreadyExistingException | InterruptedException | ExecutionException
            | InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void obtainAllPetIllnesses(Pet pet) {
        trObtainAllPetIllness.setUser(user);
        trObtainAllPetIllness.setPet(pet);
        try {
            trObtainAllPetIllness.execute();
        } catch (NotPetOwnerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetIllness(Pet pet, Illness illness) {
        trDeletePetIllness.setUser(user);
        trDeletePetIllness.setPet(pet);
        trDeletePetIllness.setIllness(illness);
        try {
            trDeletePetIllness.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePetIllness(Pet pet, Illness illness, String newDate, boolean updatesDate) {
        trUpdatePetIllness.setUser(user);
        trUpdatePetIllness.setPet(pet);
        trUpdatePetIllness.setIllness(illness);
        if (updatesDate) {
            trUpdatePetIllness.setNewDate(newDate);
        }
        try {
            trUpdatePetIllness.execute();
        } catch (NotPetOwnerException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainAllPetVetVisits(Pet pet) {
        trObtainAllVetVisits.setUser(user);
        trObtainAllVetVisits.setPet(pet);
        try {
            trObtainAllVetVisits.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pet> getUserPets() {
        return user.getPets();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (fragmentRequestCode == PostsFragment.POST_FRAGMENT_REQUEST_CODE) {
                galleryPostFragment(data);
            } else {
                galleryImageZoom(data);
            }
        }
    }

    private void galleryPostFragment(Intent data) {
        Bitmap bitmap = getGalleryBitmap(data);
        //PostsFragment.getSelectedPost().setPostImage(bitmap);
        changeFragment(actualFragment);

        byte[] imageBytes = ImageManager.getImageBytes(bitmap);

        if (imageBytes.length > PostsFragment.IMAGE_ALLOWED_SIZE) {
            Toast toast = Toast.makeText(this, R.string.error_too_big_image, Toast.LENGTH_LONG);
            toast.show();
        } else {
            ((PostsFragment) actualFragment).setPostImage(bitmap);
        }

        //addPostImage(PostsFragment.getSelectedPost(), bitmap);
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
        Bitmap bitmap = getGalleryBitmap(data);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        ((ImageZoomFragment) actualFragment).setDrawable(drawable);
        ImageZoomFragment.setIsDefaultImage(false);
        ImageZoomFragment.setIsImageDeleted(false);

        switch (ImageZoomFragment.getOrigin()) {
            case MAIN_ACTIVITY_ZOOM_IDENTIFIER:
                updateUserImage(drawable);
                updateUserProfileImage(user.getUserProfileImage());
                break;
            case InfoPetFragment.INFO_PET_ZOOM_IDENTIFIER:
                InfoPetFragment.setIsDefaultPetImage(false);
                break;
            default:
        }
    }

    /**
     * Get the galley bitmap.
     * @param data The data from the gallery
     * @return The bitmap of the gallery
     */
    private Bitmap getGalleryBitmap(@Nullable Intent data) {
        String imagePath = getImagePath(data);
        Thread askPermissionThread = ThreadFactory.createAskPermissionThread(this);

        askPermissionThread.start();

        try {
            askPermissionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeFile(imagePath);
    }

    @Override
    public void askForPermission(String... permissions) {
        Thread thread = new Thread(() -> {
            ActivityCompat.requestPermissions(this, permissions, 1);
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startWalk(List<String> walkingPetNames) {
        addPetsToWalkRegister(walkingPetNames);
        LocationUpdater.startRoute();
    }

    @Override
    public boolean isWalking() {
        SharedPreferences sharedPreferences = getSharedPreferences(WALKING_PREFERENCES, Context.MODE_PRIVATE);

        for (Pet pet : user.getPets()) {
            if (sharedPreferences.getBoolean(getUserPetIdentifier(pet.getName()), false)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void endWalk(String name, String description) {
        DateTime endDateTime = DateTime.getCurrentDateTime();
        SharedPreferences sharedPreferences = getSharedPreferences(WALKING_PREFERENCES, Context.MODE_PRIVATE);
        String strStartDateTime = sharedPreferences.getString(START_WALKING_DATE_TIME, "");
        List<Pet> pets = new ArrayList<>();

        if ("".equals(strStartDateTime)) {
            Toast toast = Toast.makeText(this, R.string.error_missing_start_date_time, Toast.LENGTH_LONG);
            toast.show();
        } else {
            endPetWalking(sharedPreferences, pets);
        }

        addWalk(pets, name, description, DateTime.Builder.buildFullString(strStartDateTime), endDateTime);
    }

    /**
     * Add the walk to the pets
     * @param pets The pets that are being walked
     * @param name The name of the walk
     * @param description The description of the walk
     * @param startDateTime The start DateTime of the walk
     * @param endDateTime The end DateTime of the walk
     */
    private void addWalk(List<Pet> pets, String name, String description, DateTime startDateTime,
                         DateTime endDateTime) {
        trAddWalk.setUser(user);
        trAddWalk.setPets(pets);
        trAddWalk.setName(name);
        trAddWalk.setDescription(description);
        trAddWalk.setStartDateTime(startDateTime);
        trAddWalk.setEndDateTime(endDateTime);
        trAddWalk.setCoordinates(getCoordinates());

        try {
            trAddWalk.execute();
        } catch (NotPetOwnerException | InvalidPeriodException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the coordinated of the walk.
     * @return The coordinates of the walk
     */
    private List<LatLng> getCoordinates() {

        List<LatLng> list = new ArrayList<>();
        LocationUpdater.endRoute();
        SharedPreferences.Editor editor = walkingSharedPreferences.edit();
        int size = walkingSharedPreferences.getInt(user.getUsername(), 0);
        for(int actual = 0; actual < size; actual++) {
            String pos = walkingSharedPreferences.getString(user.getUsername() + "_" + actual, "");
            editor.remove(user.getUsername() + "_" + actual);
            String[] splitPos = pos.split(" ");
            LatLng latLng = new LatLng(Double.parseDouble(splitPos[LAT]), Double.parseDouble(splitPos[LNG]));
            list.add(latLng);
        }
        editor.remove(user.getUsername());
        editor.apply();
        return list;

    }

    @Override
    public void cancelWalking() {
        SharedPreferences sharedPreferences = getSharedPreferences(WALKING_PREFERENCES, Context.MODE_PRIVATE);
        endPetWalking(sharedPreferences, null);
    }

    /**
     * End the pet walking.
     * @param sharedPreferences The shared preferences
     * @param pets The pets that has take place in the walk
     */
    private void endPetWalking(SharedPreferences sharedPreferences, @Nullable List<Pet> pets) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Pet pet : user.getPets()) {
            String userPet = getUserPetIdentifier(pet.getName());
            boolean isWalking = sharedPreferences.getBoolean(userPet, false);

            if (isWalking) {
                editor.putBoolean(userPet, false);

                if (pets != null) {
                    pets.add(pet);
                }
            }
        }

        editor.apply();
    }

    private void addPetsToWalkRegister(List<String> walkingPetNames) {
        SharedPreferences sharedPreferences = getSharedPreferences(WALKING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        DateTime startDateTime = DateTime.getCurrentDateTime();
        editor.putString(START_WALKING_DATE_TIME, startDateTime.toString());

        for (String petName : walkingPetNames) {
            editor.putBoolean(getUserPetIdentifier(petName), true);
        }

        editor.apply();
    }

    @NonNull
    private String getUserPetIdentifier(String petName) {
        return user.getUsername() + "_" + petName;
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
        if (deleted != null) {
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra(getString(R.string.title), deleted.getTitle());
            intent.putExtra(getString(R.string.text), deleted.getText());
            intent.putExtra(getString(R.string.notificationid), deleted.getNotificationID());
            PendingIntent pending = PendingIntent.getBroadcast(context, deleted.getRequestCode(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

            user.deleteNotification(notification);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            assert manager != null;
            manager.cancel(pending);
        }
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
    public void deleteUser() {
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

    /**
     * Get the groups.
     * @return The groups
     */
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
        try {
            trUpdateUserImage.execute();
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
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

        getAllGroupImages(groups);
        return groups;
    }

    /**
     * Get all the groups images.
     * @param groups The group images
     */
    private void getAllGroupImages(SortedSet<Group> groups) {
        ExecutorService getGroupsImages = Executors.newSingleThreadExecutor();
        getGroupsImages.execute(() -> getGroupsImages(groups));

        getGroupsImages.shutdown();
        try {
            getGroupsImages.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the groups images.
     * @param groups The groups to get the images from
     */
    private void getGroupsImages(SortedSet<Group> groups) {
        List<Group> groupList = new ArrayList<>(groups);
        ExecutorService getGroupImage = Executors.newCachedThreadPool();

        for (int actual = 0; actual < groupList.size(); ++actual) {
            int finalActual = actual;
            getGroupImage.execute(() -> updateGroupImage(groupList, finalActual));
        }

        getGroupImage.shutdown();
        try {
            getGroupImage.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the group image.
     * @param groupList The list of the groups to update
     * @param finalActual THe actual group index
     */
    private void updateGroupImage(List<Group> groupList, int finalActual) {
        SharedPreferences sharedPreferences = getSharedPreferences(GROUPS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Group actualGroup = groupList.get(finalActual);
        String currentGroupDate = sharedPreferences.getString(actualGroup.getName(), "");
        byte[] bytesImage;

        if (needToUpdateImage(groupList, finalActual, currentGroupDate)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(actualGroup.getName(), actualGroup.getLastGroupImage().toString());
            editor.apply();

            bytesImage = getGroupImage(actualGroup);
            ImageManager.writeImage(ImageManager.GROUP_IMAGES_PATH, actualGroup.getName(), bytesImage);
        } else {
            try {
                bytesImage = ImageManager.readImage(ImageManager.GROUP_IMAGES_PATH, actualGroup.getName());
            } catch (IOException e) {
                bytesImage = new byte[0];
            }
        }

        if (bytesImage.length == 0) {
            groupList.get(finalActual).setGroupIcon(null);
        } else {
            groupList.get(finalActual).setGroupIcon(BitmapFactory.decodeByteArray(bytesImage, 0,
                bytesImage.length));
        }
    }

    /**
     * Get the group image from the server.
     * @param group The group to get the image from
     * @return The image of the group in he form of an array of byte
     */
    private byte[] getGroupImage(Group group) {
        trGetGroupImage.setUser(user);
        trGetGroupImage.setGroup(group);
        trGetGroupImage.execute();

        return trGetGroupImage.getResult();
    }

    /**
     * Check whether we need to update the group image.
     * @param groupList The list of groups
     * @param finalActual The index of the actual group
     * @param currentGroupDate The current group date
     * @return True if we need to update the group image
     */
    private boolean needToUpdateImage(List<Group> groupList, int finalActual, String currentGroupDate) {
        return "".equals(currentGroupDate) || groupList.get(finalActual).getLastGroupImage()
            .compareTo(DateTime.Builder.buildFullString(currentGroupDate)) > 0;
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
     * Initializes the SharedPreferences.
     * @param acct The google account
     */
    public static void setGoogleAccount(GoogleSignInAccount acct) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("GoogleEmail", acct.getEmail());

        StringBuilder scopes = new StringBuilder();
        for (Scope s : acct.getRequestedScopes())
            scopes.append(s.toString()).append(" ");
        scopes = new StringBuilder(scopes.substring(0, scopes.toString().lastIndexOf(' ')));

        editor.putString("GoogleScopes", scopes.toString());

        editor.apply();
    }

    /**
     * Setter of the Google Calendar Token after the refresh.
     * @param token The token
     */
    @Override
    public void processFinish(String token) {
        user.setGoogleCalendarToken(token);
    }

    @Override
    public List<WalkPets> getWalkingRoutes() {
        trGetAllWalks.setUser(user);
        trGetAllWalks.execute();

        return trGetAllWalks.getResult();
    }

    @Override
    public void schedulePostNotification(String title, String text, long time) {
        scheduleNotification(this, time, title, text);
    }

    @Override
    public void sendMessageToken(String messageToken) {
        if (user != null) {
            trSendFirebaseMessagingToken.setUser(user);
            trSendFirebaseMessagingToken.setToken(messageToken);

            try {
                trSendFirebaseMessagingToken.execute();
            } catch (EmptyMessagingTokenException e) {
                e.printStackTrace();
            }
        }
    }
}
