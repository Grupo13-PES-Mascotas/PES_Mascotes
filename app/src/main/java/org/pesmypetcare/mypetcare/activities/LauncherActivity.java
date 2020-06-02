package org.pesmypetcare.mypetcare.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.login.AsyncResponse;
import org.pesmypetcare.mypetcare.activities.fragments.login.MyAsyncTask;
import org.pesmypetcare.mypetcare.controllers.community.CommunityControllersFactory;
import org.pesmypetcare.mypetcare.controllers.community.TrGetGroupImage;
import org.pesmypetcare.mypetcare.controllers.community.TrObtainAllGroups;
import org.pesmypetcare.mypetcare.controllers.exercise.ExerciseControllersFactory;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetAllExercises;
import org.pesmypetcare.mypetcare.controllers.meals.MealsControllersFactory;
import org.pesmypetcare.mypetcare.controllers.meals.TrObtainAllPetMeals;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.MedicalProfileControllersFactory;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrObtainAllPetIllness;
import org.pesmypetcare.mypetcare.controllers.medicalprofile.TrObtainAllPetVaccinations;
import org.pesmypetcare.mypetcare.controllers.medication.MedicationControllersFactory;
import org.pesmypetcare.mypetcare.controllers.medication.TrObtainAllPetMedications;
import org.pesmypetcare.mypetcare.controllers.pet.PetControllersFactory;
import org.pesmypetcare.mypetcare.controllers.pet.TrGetPetImage;
import org.pesmypetcare.mypetcare.controllers.pethealth.PetHealthControllersFactory;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrGetAllWeights;
import org.pesmypetcare.mypetcare.controllers.user.TrObtainUser;
import org.pesmypetcare.mypetcare.controllers.user.UserControllersFactory;
import org.pesmypetcare.mypetcare.controllers.vetvisits.TrObtainAllVetVisits;
import org.pesmypetcare.mypetcare.controllers.vetvisits.VetVisitsControllersFactory;
import org.pesmypetcare.mypetcare.controllers.washes.TrObtainAllPetWashes;
import org.pesmypetcare.mypetcare.controllers.washes.WashesControllersFactory;
import org.pesmypetcare.mypetcare.databinding.ActivityLauncherBinding;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.mypetcare.utilities.ServerData;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LauncherActivity extends AppCompatActivity implements AsyncResponse {
    private static final String GOOGLE_CALENDAR_SHARED_PREFERENCES = "GoogleCalendar";
    private static final int MAX_PROGRESS_VALUE = 100;
    private static final int NUM_PET_INFO = 9;
    private static boolean enableLoginActivity = true;

    private ActivityLauncherBinding binding;
    private StatusCommunication statusCommunication;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnStatusChanges(text -> binding.loadingStatus.setText(text));

        ExecutorService loadingData = Executors.newSingleThreadExecutor();
        loadingData.execute(() -> {
            loadData();
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
        });

        loadingData.shutdown();
    }

    private void loadData() {
        ServerData.getInstance().setMAuth(FirebaseAuth.getInstance());
        makeLogin();
        statusCommunication.updateText(getString(R.string.progress_bar_loading_your_pets));
        initializeLoggedUser();

        int nPets = ServerData.getInstance().getUser().getPets().size();
        int nGroups = 0;
        int progressIncrement = getIncrement(nPets, nGroups);

        initializeLoggedUserPets(nPets, progressIncrement);
    }

    void initializeLoggedUserPets(int nPets, int progressIncrement) {
        ExecutorService petData = Executors.newCachedThreadPool();

        for (int actual = 0; actual < nPets; ++actual) {
            int finalActual = actual;
            petData.execute(() -> getActualPet(progressIncrement, finalActual));
        }

        petData.shutdown();

        try {
            petData.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeLoggedUser() {
        if (enableLoginActivity) {
            askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        ExecutorService userData = Executors.newSingleThreadExecutor();
        userData.execute(this::initializeCurrentUser);
        userData.shutdown();

        try {
            userData.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getActualPet(int progressIncrement, int finalActual) {
        obtainAllPetWeights(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetMeals(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetMedications(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetVetVisits(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetWashes(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetVaccinations(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetIllnesses(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        obtainAllPetExercises(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
        getPetImage(ServerData.getInstance().getUser().getPets().get(finalActual));
        updateProgress(progressIncrement);
    }

    private void getActualGroup(int progressIncrement, int finalActual) {
        if (isUserSubscriber(ServerData.getInstance().getGroups(), finalActual)) {
            ServerData.getInstance().getUser().addSubscribedGroup(ServerData.getInstance().getGroups()
                .get(finalActual));
        }

        TrGetGroupImage trGetGroupImage = CommunityControllersFactory.createTrGetGroupImage();
        trGetGroupImage.setUser(ServerData.getInstance().getUser());
        trGetGroupImage.setGroup(ServerData.getInstance().getGroups().get(finalActual));
        trGetGroupImage.execute();

        byte[] imageBytes = trGetGroupImage.getResult();
        Bitmap groupImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ServerData.getInstance().getGroups().get(finalActual).setGroupIcon(groupImage);
        updateProgress(progressIncrement);
    }

    private int getIncrement(int nPets, int nGroups) {
        return (int) Math.ceil((double) MAX_PROGRESS_VALUE / (nPets * NUM_PET_INFO + nGroups));
    }

    private boolean isUserSubscriber(List<Group> groupList, int finalActual) {
        return groupList.get(finalActual).getSubscribers().containsKey(ServerData.getInstance().getUser()
            .getUsername());
    }

    /**
     * Make the login to the application.
     */
    private void makeLogin() {
        if (enableLoginActivity && ServerData.getInstance().getMAuth().getCurrentUser() == null) {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            finish();
        }
        else if (!enableLoginActivity) {
            ServerData.getInstance().setUser(new User("johnDoe", "johnDoe@gmail.com", "1234"));
        }
    }

    /**
     * Initializes the current user.
     */
    private void initializeCurrentUser() {
        if (enableLoginActivity && ServerData.getInstance().getMAuth().getCurrentUser() != null) {
            try {
                initializeUser();
                refreshGoogleCalendarToken();
                //changeFragment(getFragment(APPLICATION_FRAGMENTS[0]));
            } catch (PetRepeatException e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_pet_already_existing),
                    Toast.LENGTH_LONG);
                toast.show();
            }

            if (ServerData.getInstance().getMAuth().getCurrentUser() != null) {
                ServerData.getInstance().getMAuth().getCurrentUser().getIdToken(false).addOnCompleteListener(task ->
                    ServerData.getInstance().getUser().setToken(Objects.requireNonNull(task.getResult()).getToken()));
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
        SharedPreferences sharedPreferences = getSharedPreferences(GOOGLE_CALENDAR_SHARED_PREFERENCES,
            Context.MODE_PRIVATE);
        String googleEmail = sharedPreferences.getString("GoogleEmail", "");
        String scopes = sharedPreferences.getString("GoogleScopes", "");
        if (!"".equals(googleEmail)) {
            MyAsyncTask asyncTask = new MyAsyncTask(googleEmail, scopes, this.getBaseContext());
            asyncTask.delegate = this;
            asyncTask.execute();
        }
    }

    /**
     * Initialize the current.
     * @throws PetRepeatException The pet has already been registered
     */
    private void initializeUser() throws PetRepeatException {
        TrObtainUser trObtainUser = UserControllersFactory.createTrObtainUser();
        trObtainUser.setUid(Objects.requireNonNull(ServerData.getInstance().getMAuth().getCurrentUser()).getUid());
        trObtainUser.setToken("token");

        try {
            trObtainUser.execute();
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }

        ServerData.getInstance().setUser(trObtainUser.getResult());
        binding.progressBar.setIndeterminate(false);
    }

    private synchronized void updateProgress(int progressIncrement) {
        int nextProgress = Math.min(progress + progressIncrement, MAX_PROGRESS_VALUE);

        for (int actual = progress + 1; actual <= nextProgress; ++actual) {
            binding.progressBar.setProgress(actual);
        }

        progress = nextProgress;
    }

    private void getPetImage(Pet pet) {
        Bitmap petImage = null;
        String username = ServerData.getInstance().getUser().getUsername();
        String petName = pet.getName();
        byte[] bytes = new byte[0];

        try {
            bytes = ImageManager.readImage(ImageManager.PET_PROFILE_IMAGES_PATH, username + '_' + petName);
        } catch (IOException e) {
            bytes = getPetImageFromServer(pet);
        } finally {
            if (bytes.length > 0) {
                petImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }

        pet.setProfileImage(petImage);
    }

    private byte[] getPetImageFromServer(Pet pet) {
        TrGetPetImage trGetPetImage = PetControllersFactory.createTrGetPetImage();
        trGetPetImage.setUser(ServerData.getInstance().getUser());
        trGetPetImage.setPet(pet);

        try {
            trGetPetImage.execute();
        } catch (NotPetOwnerException e) {
            e.printStackTrace();
        }

        return trGetPetImage.getResult();
    }

    /**
     * Obtain all the pet weights.
     * @param pet The pet to get the weights from
     */
    private void obtainAllPetWeights(Pet pet) {
        TrGetAllWeights trGetAllWeights = PetHealthControllersFactory.createTrGetAllWeights();
        trGetAllWeights.setUser(ServerData.getInstance().getUser());
        trGetAllWeights.setPet(pet);
        try {
            trGetAllWeights.execute();
        } catch (NotPetOwnerException e) {
            e.printStackTrace();
        }
    }

    private void obtainAllPetMeals(Pet pet) {
        TrObtainAllPetMeals trObtainAllPetMeals = MealsControllersFactory.createTrObtainAllPetMeals();
        trObtainAllPetMeals.setUser(ServerData.getInstance().getUser());
        trObtainAllPetMeals.setPet(pet);
        try {
            trObtainAllPetMeals.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void obtainAllPetMedications(Pet pet) {
        TrObtainAllPetMedications trObtainAllPetMedications;
        trObtainAllPetMedications = MedicationControllersFactory.createTrObtainAllPetMedications();

        trObtainAllPetMedications.setUser(ServerData.getInstance().getUser());
        trObtainAllPetMedications.setPet(pet);
        try {
            trObtainAllPetMedications.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void obtainAllPetVetVisits(Pet pet) {
        TrObtainAllVetVisits trObtainAllVetVisits = VetVisitsControllersFactory.createTrObtainAllVetVisits();
        trObtainAllVetVisits.setUser(ServerData.getInstance().getUser());
        trObtainAllVetVisits.setPet(pet);
        try {
            trObtainAllVetVisits.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void obtainAllPetWashes(Pet pet) {
        TrObtainAllPetWashes trObtainAllPetWashes = WashesControllersFactory.createTrObtainAllPetWashes();
        trObtainAllPetWashes.setUser(ServerData.getInstance().getUser());
        trObtainAllPetWashes.setPet(pet);
        trObtainAllPetWashes.execute();
    }

    private void obtainAllPetVaccinations(Pet pet) {
        TrObtainAllPetVaccinations trObtainAllPetVaccinations;
        trObtainAllPetVaccinations = MedicalProfileControllersFactory.createTrObtainAllPetVaccinations();

        trObtainAllPetVaccinations.setUser(ServerData.getInstance().getUser());
        trObtainAllPetVaccinations.setPet(pet);
        try {
            trObtainAllPetVaccinations.execute();
        } catch (NotPetOwnerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void obtainAllPetIllnesses(Pet pet) {
        TrObtainAllPetIllness trObtainAllPetIllness = MedicalProfileControllersFactory.createTrObtainAllPetIllnesses();
        trObtainAllPetIllness.setUser(ServerData.getInstance().getUser());
        trObtainAllPetIllness.setPet(pet);
        try {
            trObtainAllPetIllness.execute();
        } catch (NotPetOwnerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtain all the pet exercises.
     * @param pet The pet to get the exercises from
     */
    private void obtainAllPetExercises(Pet pet) {
        TrGetAllExercises trGetAllExercises = ExerciseControllersFactory.createTrGetAllExercises();
        trGetAllExercises.setUser(ServerData.getInstance().getUser());
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
     * Setter of the Google Calendar Token after the refresh.
     * @param token The token
     */
    @Override
    public void processFinish(String token) {
        ServerData.getInstance().getUser().setGoogleCalendarToken(token);
    }

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

    public SortedSet<Group> getAllGroups() {
        TrObtainAllGroups trObtainAllGroups = CommunityControllersFactory.createTrObtainAllGroups();
        trObtainAllGroups.execute();

        return trObtainAllGroups.getResult();
    }

    public static void setEnableLoginActivity(boolean enableLoginActivity) {
        LauncherActivity.enableLoginActivity = enableLoginActivity;
    }

    public void setOnStatusChanges(StatusCommunication statusCommunication) {
        this.statusCommunication = statusCommunication;
    }

    public interface StatusCommunication {
        void updateText(String text);
    }
}
