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

import java.util.ArrayList;

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
        ArrayList<ConstraintLayout> petsComponents = binding.mainMenu.getPetComponents();
        while(!petsComponents.isEmpty()) {
            ConstraintLayout tmp = petsComponents.remove(0);
            tmp.setClickable(true);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainActivityFrameLayout, new InfoPetFragment());
                    ft.commit();
                }
            });
        }
    }

    /**
     * Method responsible for initializing a test user.
     */
    private void initializeTestUser() {
        currentUser = new User("Jaume" , "jaume@gmail.com", "1234");
        Bundle info = new Bundle();
        info.putString(Pet.BUNDLE_NAME, "Manolo");
        info.putString(Pet.BUNDLE_BREED, "Elephant");
        info.putString(Pet.BUNDLE_BIRTH_DATE, "20/11/1988");
        info.putFloat(Pet.BUNDLE_CALORIES, (float) 1500.00);
        info.putString(Pet.BUNDLE_GENDER, "Male");
        info.putString(Pet.BUNDLE_PATHOLOGIES, "None");
        info.putInt(Pet.BUNDLE_WASH, 1);
        info.putFloat(Pet.BUNDLE_WEIGHT, (float) 750.00);
        currentUser.addPet(new Pet(info));
        info = new Bundle();
        info.putString(Pet.BUNDLE_NAME, "Ernesto");
        info.putString(Pet.BUNDLE_BREED, "Pangolin");
        info.putString(Pet.BUNDLE_BIRTH_DATE, "05/01/1975");
        info.putFloat(Pet.BUNDLE_CALORIES, (float) 0.05);
        info.putString(Pet.BUNDLE_GENDER, "Male");
        info.putString(Pet.BUNDLE_PATHOLOGIES, "None");
        info.putInt(Pet.BUNDLE_WASH, 1);
        info.putFloat(Pet.BUNDLE_WEIGHT, (float) 0.00025);
        currentUser.addPet(new Pet(info));
        info = new Bundle();
        info.putString(Pet.BUNDLE_NAME, "Segismunda");
        info.putString(Pet.BUNDLE_BREED, "Tortuga");
        info.putString(Pet.BUNDLE_BIRTH_DATE, "15/11/1875");
        info.putFloat(Pet.BUNDLE_CALORIES, (float) 9.78);
        info.putString(Pet.BUNDLE_GENDER, "Female");
        info.putString(Pet.BUNDLE_PATHOLOGIES, "None");
        info.putInt(Pet.BUNDLE_WASH, 7);
        info.putFloat(Pet.BUNDLE_WEIGHT, (float) 37.25);
        currentUser.addPet(new Pet(info));
    }
}
