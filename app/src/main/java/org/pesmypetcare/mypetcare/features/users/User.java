package org.pesmypetcare.mypetcare.features.users;

import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String username;
    private String mail;
    private String passwd;
    private ArrayList<Pet> pets;

    public User(String username, String mail, String passwd) {
        this.username = username;
        this.mail = mail;
        this.passwd = passwd;
        this.pets = new ArrayList<>();
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    private void comunicateDatabase(String username, String mail, String passwd) {
        System.out.println("Es registrara l'usuari " + username + " amb mail "
            + mail + " i contrasenya " + passwd + " a la BD");
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    public void deletePet(Pet pet) {
        pets.remove(pet);
    }

    public ArrayList<Pet> getPets() {
        return pets;
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
