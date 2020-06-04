package org.pesmypetcare.mypetcare.features.users;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.notification.Notification;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Xavier Campos & Daniel Clemente & Enric Hernando & Albert Pinto
 */
public class User {
    private String username;
    private String email;
    private String passwd;
    private ArrayList<Pet> pets;
    private Bitmap userProfileImage;
    private String token;
    private String googleCalendarToken;
    private ArrayList<Notification> notifications;
    private SortedSet<String> subscribedGroups;
    private List<UserAchievement> achievements;

    public User(String username, String email, String passwd) {
        this.username = username;
        this.email = email;
        this.passwd = passwd;
        this.pets = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.token = "token";
        this.subscribedGroups = new TreeSet<>();
        initializeAchievements();
    }

    /**
     * Get the token.
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token.
     * @param token The token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter of the username attribute.
     * @return The username attribute
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter of the username attribute.
     * @param username The username attribute to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the mail attribute.
     * @return The mail attribute to set
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter of the mail attribute.
     * @param email The mail attribute to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter of the password attribute.
     * @return The password attribute to set
     */
    public String getPasswd() {
        return this.passwd;
    }

    /**
     * Setter of the password attribute.
     * @param passwd The password attribute to set
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Method responsible for adding a new notification to the user.
     * @param notification The notification to be added to the user
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setUser(this);
    }

    /**
     * Method responsible for get a notification from the user.
     * @param notification The notification
     */
    public Notification getNotification(Notification notification) {
        Notification aux = null;
        for (Notification not : notifications) {
            if (not.equals(notification)) {
                aux = not;
            }
        }
        return aux;
    }

    /**
     * Method responsible for deleting a notification from the user.
     * @param notification The notification to be deleted from the user
     */
    public void deleteNotification(Notification notification) {
        notifications.remove(notification);
    }

    /**
     * Method responsible for adding a new pet to the user.
     * @param pet The pet to be added to the user
     */
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    /**
     * Method responsible for deleting a pet from the user.
     * @param pet The pet to be deleted from the user
     */
    public void deletePet(Pet pet) {
        pets.remove(pet);
    }

    /**
     * Getter of the pets from the user.
     * @return An arraylist containing all the pets from the user
     */
    public ArrayList<Pet> getPets() {
        return pets;
    }

    /**
     * Setter of the pets from the user.
     * @param pets The arraylist of pets to set
     */
    public void setPets(ArrayList<Pet> pets) {
        this.pets.clear();
        for (Pet pet : pets) {
            addPet(pet);
        }
    }

    /**
     * Getter of the userProfileImage attribute.
     * @return The userProfileImage
     */
    public Bitmap getUserProfileImage() {
        return userProfileImage;
    }

    /**
     * Setter of the userProfileImage attribute.
     * @param bitmap The new userProfileImage to set
     */
    public void setUserProfileImage(Bitmap bitmap) {
        this.userProfileImage = bitmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public void updatePetProfileImage(Pet actualPet) {
        int index = getActualPetIndex(actualPet.getName());
        pets.get(index).setProfileImage(actualPet.getProfileImage());
    }

    private int getActualPetIndex(String name) {
        for (int actual = 0; actual < pets.size(); ++actual) {
            if (pets.get(actual).getName().equals(name)) {
                return actual;
            }
        }

        return -1;
    }

    /**
     * Subscribe to the group.
     * @param group The group to subscribe
     */
    public void addSubscribedGroup(Group group) {
        subscribedGroups.add(group.getName());
        group.addSubscriber(this);
    }

    /**
     * Subscribe to the group but not include it to the group
     * @param group The group to add
     */
    public void addSubscribedGroupSimple(Group group) {
        subscribedGroups.add(group.getName());
    }

    /**
     * Remove a subscription to a group
     * @param group The group to remove the subscription to
     */
    public void removeSubscribedGroup(Group group) {
        subscribedGroups.remove(group.getName());
        group.removeSubscriber(this);
    }

    /**
     * Get the subscribed groups.
     * @return The subscribed groups
     */
    public SortedSet<String> getSubscribedGroups() {
        return subscribedGroups;
    }

    /**
     * Setter of the google calendar token attribute.
     * @param googleCalendarToken The google calendar token to set
     */
    public void setGoogleCalendarToken(String googleCalendarToken) {
        this.googleCalendarToken = googleCalendarToken;
    }

    /**
     * Getter of the google calendar token attribute.
     * @return The google calendar token attribute to set
     */
    public String getGoogleCalendarToken() {
        return this.googleCalendarToken;
    }

    public List<UserAchievement> getAchievements() {
        return achievements;
    }

    public void updateAchievementProgress(String name, Integer progress) throws InvalidFormatException {
        for (UserAchievement userAchievement : achievements) {
            if(userAchievement.getName().equals(name)) {
                userAchievement.updateProgress(progress);
            }
        }
    }
    private void initializeAchievements() {
        achievements = new ArrayList<>();
        initializeContributor();
        initializeZoo();
        initializeSuperwalker();
        initializeCleaner();
        initializeScaler();
        initializeFounder();
        iniitializeMealer();
        initializeEvent();
    }


    private void initializeContributor() {
        UserAchievement contributor = new UserAchievement("Contributor", R.string.contributor_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 5);
        levels.add(1, 20);
        levels.add(1, 40);
        contributor.setLevels(levels);
        achievements.add(contributor);
    }


    private void initializeZoo() {
        UserAchievement zoo = new UserAchievement("Zoo", R.string.walker_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 2);
        levels.add(1, 3);
        levels.add(1, 4);
        zoo.setLevels(levels);
        achievements.add(zoo);
    }

    private void initializeSuperwalker() {
        UserAchievement superwalker = new UserAchievement("Superwalker", R.string.superwalker_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 20);
        levels.add(1, 60);
        levels.add(1, 100);
        superwalker.setLevels(levels);
        achievements.add(superwalker);

    }

    private void initializeCleaner() {
        UserAchievement cleaner = new UserAchievement("Clean as a whistle", R.string.clean_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 3);
        levels.add(1, 10);
        levels.add(1, 20);
        cleaner.setLevels(levels);
        achievements.add(cleaner);
    }

    private void initializeScaler() {
        UserAchievement scaler = new UserAchievement("Master of scale", R.string.scale_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 5);
        levels.add(1, 10);
        levels.add(1, 20);
        scaler.setLevels(levels);
        achievements.add(scaler);
    }

    private void initializeFounder() {
        UserAchievement founder = new UserAchievement("Founder", R.string.founder_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 1);
        levels.add(1, 5);
        levels.add(1, 10);
        founder.setLevels(levels);
        achievements.add(founder);
    }

    private void iniitializeMealer() {
        UserAchievement mealer = new UserAchievement("You are what you eat", R.string.meals_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 15);
        levels.add(1, 100);
        levels.add(1, 200);
        mealer.setLevels(levels);
        achievements.add(mealer);
    }

    private void initializeEvent() {
        UserAchievement event = new UserAchievement("All under control", R.string.event_desc);
        List<Integer> levels = new ArrayList<>();
        levels.add(0, 0);
        levels.add(1, 10);
        levels.add(1, 30);
        levels.add(1, 50);
        event.setLevels(levels);
        achievements.add(event);
    }

}
