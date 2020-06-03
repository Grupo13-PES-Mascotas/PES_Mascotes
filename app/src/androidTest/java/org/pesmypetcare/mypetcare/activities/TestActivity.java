package org.pesmypetcare.mypetcare.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.activities.fragments.achievements.AchievementsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.community.CommunityCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.PostsFragment;
import org.pesmypetcare.mypetcare.activities.fragments.establishments.EstablishmentsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.imagezoom.ImageZoomCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.login.AsyncResponse;
import org.pesmypetcare.mypetcare.activities.fragments.mypets.MyPetsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.registerpet.RegisterPetCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.NewPasswordInterfaceCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.settings.SettingsCommunication;
import org.pesmypetcare.mypetcare.activities.fragments.walks.WalkCommunication;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.notification.Notification;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.WalkPets;
import org.pesmypetcare.mypetcare.features.pets.events.meals.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.MessagingServiceCommunication;
import org.pesmypetcare.mypetcare.utilities.MessagingTokenServiceCommunication;

import java.util.List;
import java.util.SortedSet;

public class TestActivity extends AppCompatActivity implements RegisterPetCommunication,
    NewPasswordInterfaceCommunication, InfoPetCommunication, MyPetsCommunication, SettingsCommunication,
    CalendarCommunication, ImageZoomCommunication, CommunityCommunication, InfoGroupCommunication, WalkCommunication,
    MessagingServiceCommunication, MessagingTokenServiceCommunication, EstablishmentsCommunication,
    AchievementsCommunication, AsyncResponse {
    private static FragmentManager fragmentManager;
    private static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        id = View.generateViewId();
        frameLayout.setId(id);
        setContentView(frameLayout);

        fragmentManager = getSupportFragmentManager();
    }

    public static void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void newPersonalEvent(Pet pet, String description, String dateTime) {

    }

    @Override
    public void deletePersonalEvent(Pet pet, Event event) {

    }

    @Override
    public void scheduleNotification(Context context, long time, String title, String text) {

    }

    @Override
    public void cancelNotification(Context context, Notification notification) {

    }

    @Override
    public void newPeriodicNotification(Pet selectedPet, int periodicity, String reasonText, DateTime dateTime) {

    }

    @Override
    public void deletePeriodicNotification(Pet selectedPet, Event event, User user) {

    }

    @Override
    public void schedulePeriodicNotification(Context context, long timeInMillis, String name, String toString,
                                             int period) {

    }

    @Override
    public SortedSet<Group> getAllGroups() {
        return null;
    }

    @Override
    public void deleteGroup(String groupName) {

    }

    @Override
    public void showGroupFragment(InfoGroupFragment infoGroupFragment) {

    }

    @Override
    public void setToolbar(String title) {

    }

    @Override
    public void addSubscription(Group group) {

    }

    @Override
    public void removeSubscription(Group group) {

    }

    @Override
    public void deleteForum(Forum forum) {

    }

    @Override
    public void showForum(PostsFragment postsFragment) {

    }

    @Override
    public void addNewPost(Forum forum, @Nullable String postText, @Nullable Bitmap postImage) {

    }

    @Override
    public void deletePost(Forum forum, DateTime postCreationDate) {

    }

    @Override
    public void updatePost(Post postToUpdate, String newText) {

    }

    @Override
    public void likePost(Post postToLike) {

    }

    @Override
    public void unlikePost(Post post) {

    }

    @Override
    public void reportPost(Post post, String reportMessage) {

    }

    @Override
    public Bitmap findImageByUser(String username) {
        return null;
    }

    @Override
    public void addPostImage(Post post, Bitmap image) {

    }

    @Override
    public void deletePostImage(Post post) {

    }

    @Override
    public void makeGroupZoomImage(Drawable drawable) {

    }

    @Override
    public byte[] getImageFromPost(Post post, MessageDisplay messageData) {
        return new byte[0];
    }

    @Override
    public void updateUserImage(Drawable drawable) {

    }

    @Override
    public void makeZoomImage(Drawable drawable) {

    }

    @Override
    public void updatePetImage(Pet pet, Bitmap newImage) {

    }

    @Override
    public void deletePet(Pet myPet) throws UserIsNotOwnerException {

    }

    @Override
    public void updatePet(Pet pet) throws UserIsNotOwnerException {

    }

    @Override
    public void changeToMainView() {

    }

    @Override
    public void addWeightForDate(Pet pet, double newWeight, String date) {

    }

    @Override
    public void deleteWeightForDate(Pet pet, String date) {

    }

    @Override
    public void addWashFrequencyForDate(Pet pet, int newWashFrequency, String date) {

    }

    @Override
    public void deleteWashFrequencyForDate(Pet pet, String date) {

    }

    @Override
    public void addPetMeal(Pet pet, Meals meal) throws MealAlreadyExistingException {

    }

    @Override
    public void updatePetMeal(Pet pet, Meals meal, String newDate, boolean updatesDate) {

    }

    @Override
    public void deletePetMeal(Pet pet, Meals meal) {

    }

    @Override
    public void obtainAllPetMeals(Pet pet) {

    }

    @Override
    public void addPetMedication(Pet pet, Medication medication) {

    }

    @Override
    public void updatePetMedication(Pet pet, Medication medication, String newDate, boolean updatesDate, String newName,
                                    boolean updatesName) {

    }

    @Override
    public void deletePetMedication(Pet pet, Medication medication) {

    }

    @Override
    public void obtainAllPetMedications(Pet pet) {

    }

    @Override
    public void addPetVetVisit(Pet pet, VetVisit vetVisit) {

    }

    @Override
    public void updatePetVetVisit(Pet pet, VetVisit vetVisit, String newDate, boolean updatesDate) {

    }

    @Override
    public void deletePetVetVisit(Pet pet, VetVisit vetVisit) {

    }

    @Override
    public void obtainAllPetVetVisits(Pet pet) {

    }

    @Override
    public void addPetWash(Pet pet, Wash wash) {

    }

    @Override
    public void updatePetWash(Pet pet, Wash wash, String newDate, boolean updatesDate) {

    }

    @Override
    public void deletePetWash(Pet pet, Wash wash) {

    }

    @Override
    public void obtainAllPetWashes(Pet pet) {

    }

    @Override
    public void addExercise(Pet pet, String exerciseName, String exerciseDescription, DateTime startExerciseDateTime,
                            DateTime endExerciseDateTime) {

    }

    @Override
    public void removeExercise(Pet pet, DateTime dateTime) {

    }

    @Override
    public void updateExercise(Pet pet, String txtExerciseName, String txtDescription, DateTime originalStartDateTime,
                               DateTime startExerciseDateTime, DateTime endExerciseDateTime) {

    }

    @Override
    public void addPetVaccination(Pet pet, String vaccinationDescription, DateTime vaccinationDate) {

    }

    @Override
    public void updatePetVaccination(Pet pet, Vaccination vaccination, String newDate, boolean updatesDate) {

    }

    @Override
    public void deletePetVaccination(Pet pet, Vaccination vaccination) {

    }

    @Override
    public void obtainAllPetVaccinations(Pet pet) {

    }

    @Override
    public void addPetIllness(Pet pet, String description, String type, String severity, DateTime startDate,
                              DateTime endDate) {

    }

    @Override
    public void updatePetIllness(Pet pet, Illness illness, String newDate, boolean updatesDate) {

    }

    @Override
    public void obtainAllPetIllnesses(Pet pet) {

    }

    @Override
    public void deletePetIllness(Pet pet, Illness illness) {

    }

    @Override
    public List<Pet> getUserPets() {
        return null;
    }

    @Override
    public List<WalkPets> getWalkingRoutes() {
        return null;
    }

    @Override
    public void changeToMyPets() {

    }

    @Override
    public void askForPermission(String... permission) {

    }

    @Override
    public void startWalk(List<String> walkingPetNames) {

    }

    @Override
    public boolean isWalking() {
        return false;
    }

    @Override
    public void endWalk(String name, String description) {

    }

    @Override
    public void cancelWalking() {

    }

    @Override
    public void processFinish(String token) {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void changePetProfileImage(Pet actualPet) {

    }

    @Override
    public void addNewPet(Bundle petInfo) {

    }

    @Override
    public void changeFragmentPass(Fragment fragment) {

    }

    @Override
    public void changeMail(String newEmail) {

    }

    @Override
    public void changePassword(String password) {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public User getUserForSettings() {
        return null;
    }

    @Override
    public boolean usernameExists(String newUsername) {
        return false;
    }

    @Override
    public void changeUsername(String newUsername) {

    }

    @Override
    public void schedulePostNotification(String title, String text, long time) {

    }

    @Override
    public void sendMessageToken(String messageToken) {

    }
}
