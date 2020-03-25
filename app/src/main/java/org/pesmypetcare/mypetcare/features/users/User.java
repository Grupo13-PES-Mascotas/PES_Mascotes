package org.pesmypetcare.mypetcare.features.users;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String username;
    private String email;
    private String passwd;
    private ArrayList<Pet> pets;
    private Bitmap userProfileImage;

    public User(String username, String email, String passwd) {
        this.username = username;
        this.email = email;
        this.passwd = passwd;
        this.pets = new ArrayList<>();
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
     * Method responsible for adding a new pet to the user.
     * @param pet The pet to be added to the user
     */
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    /**
     * Method responsible for deleting a new pet to the user.
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
        this.pets = pets;
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

}
