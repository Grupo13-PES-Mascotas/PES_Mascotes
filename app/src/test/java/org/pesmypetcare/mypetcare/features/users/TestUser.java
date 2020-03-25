package org.pesmypetcare.mypetcare.features.users;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrChangePassword;
import org.pesmypetcare.mypetcare.controllers.TrDeleteUser;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.controllers.TrObtainUser;
import org.pesmypetcare.mypetcare.features.pets.Gender;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUser {
    private User user;
    private TrRegisterNewPet trRegisterNewPet;
    private TrObtainUser trObtainUser;
    private TrChangePassword trChangePassword;
    private TrDeleteUser trDeleteUser;
    private final String MIKE = "Mike";
    private final String PASSWORD = "12En)(";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
        trRegisterNewPet = new TrRegisterNewPet(new StubPetManagerService());
        trObtainUser = new TrObtainUser(new StubUserManagerService(), new StubPetManagerService());
        trChangePassword = new TrChangePassword(new StubUserManagerService());
        trDeleteUser = new TrDeleteUser(new StubUserManagerService(), new StubPetManagerService());
    }

    @Test
    public void shouldAddOnePet() throws PetRepeatException {
        Pet pet = getLinuxPet();
        user.addPet(pet);
        assertTrue("should add one pet", user.getPets().contains(pet));
    }

    @Test
    public void shouldCommunicateWithService() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        boolean addingResult = trRegisterNewPet.isResult();
        assertTrue("should communicate with service to add a pet", addingResult);
    }

    @Test(expected = PetAlreadyExistingException.class)
    public void shouldNotAddPetIfExisting() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        trRegisterNewPet.execute();
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(Gender.MALE);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }

    @Test
    public void shouldObtainUser() throws UserNotExistingException {
        trObtainUser.setUsername("johnDoe");
        trObtainUser.execute();
        User user = trObtainUser.getResult();
        assertNotNull("should communicate with service to obtain a user", user);
    }

    @Test(expected = UserNotExistingException.class)
    public void shouldNotObtainUserIfNotExisting() throws UserNotExistingException {
        trObtainUser.setUsername(MIKE);
        trObtainUser.execute();
    }

    @Test
    public void shouldChangeUserPassword() throws SamePasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword("Ab12!@");
        trChangePassword.execute();
        boolean addingResult = trChangePassword.isResult();
        assertTrue("should communicate with service to change the password", addingResult);
    }

    @Test(expected = SamePasswordException.class)
    public void shouldNotChangePasswordIfIsTheCurrent() throws SamePasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword(PASSWORD);
    }

    @Test
    public void shouldDeleteUser() throws NotValidUserException {
        trDeleteUser.setUser(this.user);
        trDeleteUser.execute();
        boolean addingResult = trDeleteUser.isResult();
        assertTrue("should communicate with service to delete the user", addingResult);
    }

    @Test (expected = NotValidUserException.class)
    public void shouldNotDeleteIfNotExists() throws NotValidUserException {
        trDeleteUser.setUser(new User("Manuel", "manuel@gmail.com", "123"));
        trDeleteUser.execute();
    }
}
