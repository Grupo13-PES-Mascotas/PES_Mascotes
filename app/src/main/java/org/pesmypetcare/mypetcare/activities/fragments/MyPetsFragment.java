package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentMyPetsBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPetsFragment extends Fragment {
    private FragmentMyPetsBinding binding;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPetsBinding.inflate(inflater, container, false);
        initializeTestUser();
        initializeMainMenuView();
        return binding.getRoot();
    }

    /**
     * Method responsible for initializing and showing the main menu view.
     */
    private void initializeMainMenuView() {
        binding.mainMenu.showPets(currentUser);
        setPetComponentsListeners();
    }

    /**
     * Method responsible for setting the listeners for all the pet components.
     */
    private void setPetComponentsListeners() {
        List<ConstraintLayout> petsComponents = binding.mainMenu.getPetComponents();
        while (!petsComponents.isEmpty()) {
            ConstraintLayout tmp = petsComponents.remove(0);
            tmp.setClickable(true);
            tmp.setOnClickListener(v -> {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainActivityFrameLayout, new InfoPetFragment());
                ft.commit();
            });
        }
    }

    /**
     * Method responsible for initializing a test user.
     */
    private void initializeTestUser() {
        currentUser = new User("Jaume", "jaume@gmail.com", "1234");
        String [] bundleNames = {"Manolo", "Ernesto", "Segismunda"};
        String [] bundleBreeds = {"Elephant", "Pangolin", "Turtle"};
        String [] bundleBirths = {"02/12/2003", "11/09/1999", "21/02/1711"};
        String [] bundleGenders = {"Male", "Male", "Female"};
        String bundlePathologies = "None";
        initializeTestBundle(bundleNames, bundleBreeds, bundleBirths, bundleGenders, bundlePathologies);
    }

    /**
     * Method responsible for creating the test pets.
     * @param bundleNames An array containing the pets names
     * @param bundleBreeds An array containing the pets breeds
     * @param bundleBirths An array containing the pets birth dates
     * @param bundleGenders An array containing the pets genders
     * @param bundlePathologies An string containing the pets pathologies
     */
    private void initializeTestBundle(String[] bundleNames, String[] bundleBreeds, String[] bundleBirths, String[] bundleGenders, String bundlePathologies) {
        Bundle info;
        for (int i = 0; i < bundleNames.length; ++i) {
            info = new Bundle();
            info.putString(Pet.BUNDLE_NAME, bundleNames[i]);
            info.putString(Pet.BUNDLE_BREED, bundleBreeds[i]);
            info.putString(Pet.BUNDLE_BIRTH_DATE, bundleBirths[i]);
            info.putFloat(Pet.BUNDLE_CALORIES, (float) i);
            info.putString(Pet.BUNDLE_GENDER, bundleGenders[i]);
            info.putString(Pet.BUNDLE_PATHOLOGIES, bundlePathologies);
            info.putInt(Pet.BUNDLE_WASH, i);
            info.putFloat(Pet.BUNDLE_WEIGHT, (float) i);
            currentUser.addPet(new Pet(info));
        }
    }
}
