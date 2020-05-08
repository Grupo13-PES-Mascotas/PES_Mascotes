package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enric Hernando
 */
public class WashManagerAdapter implements WashManagerService {

    @Override
    public void createWash(User user, Pet pet, Wash wash) {
        /*String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        WashData body = new WashData(wash.getMealName(), wash.getDuration());
        Meal libMeal = new Meal(meal.getDateTime().toString(), body);
        try {
            ServiceLocator.getInstance().getWashManagerClient().createMeal(accessToken, owner, petName, libMeal);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
         */
    }

    @Override
    public void deleteWash(User user, Pet pet, Wash wash) {
        /*String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        DateTime dateTime = wash.getDateTime();
        try {
            ServiceLocator.getInstance().getWashManagerClient().deleteByDate(accessToken, owner, petName, dateTime);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

         */
    }

    @Override
    public List<Wash> findWashesByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        return obtainAllWashes(accessToken, owner, petName);
    }

    @Override
    public void updateWashBody(User user, Pet pet, Wash wash) {
        /*String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        DateTime washDate = wash.getDateTime();
        updateWashName(wash, accessToken, owner, petName, washDate);
        updateWashDuration(wash, accessToken, owner, petName, washDate);

         */
    }
    /**
     * Method responsible for accessing the service to update the wash duration.
     * @param wash The wash from which we want to update the duration.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet to which belong the wash
     * @param washDate The date of the wash
     */
    private void updateWashDuration(Wash wash, String accessToken, String owner, String petName, DateTime washDate) {
        /*String field = WashManagerClient.DURATION;
        Object value = wash.getDuration();
        try {
            ServiceLocator.getInstance().getWashManagerClient().updateWashField(accessToken,
                    owner, petName, washDate, field, value);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

         */
    }

    /**
     * Method responsible for accessing the service to update the wash name.
     * @param meal The wash from which we want to update the name
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet to which belong the wash
     * @param washDate The date of the wash
     */
    private void updateWashName(Meals meal, String accessToken, String owner, String petName, DateTime washDate) {
        /*String field = WashManagerClient.WASHNAME;
        Object value = wash.getWashName();
        try {
            ServiceLocator.getInstance().getWashManagerClient().updateWashField(accessToken,
                    owner, petName, washDate, field, value);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void updateWashDate(User user, Pet pet, String newDate, String oldDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        /*WashData mealData = obtainWashData(oldDate, accessToken, owner, petName);
        Meals currentMeal = new Meals(new Meal(oldDate, mealData));
        Meals newMeal = new Meals(new Meal(newDate, mealData));
        this.deleteWash(user, pet, currentWash);
        this.createWash(user, pet, newWash);*/
    }

    @Override
    public void deleteWashesFromPet(User user, Pet pet) {
        //
    }

    /**
     * Method responsible for accessing the service an obtaining all the washes for the indicated pet.
     * @param accessToken The accessToken of the owner
     * @param owner The owner of the pet
     * @param petName The name of the pet from which we want to obtain all the washes
     * @return The list with all the washes from the pet
     */
    private List<Wash> obtainAllWashes(String accessToken, String owner, String petName) {
        /*List<Wash> washList = null;
        try {
            washList = ServiceLocator.getInstance().getWashManagerClient().getAllWashData(accessToken,
                    owner, petName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        List<Wash> result = new ArrayList<>();
        for (Wash m:washList) {
            //result.add(new Wash(m));
        }
        return result;
         */
        return new ArrayList<>();
    }

}
