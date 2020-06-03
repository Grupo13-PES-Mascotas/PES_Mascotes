package org.pesmypetcare.mypetcare.activities.fragments.mypets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.databinding.FragmentMyPetsBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;
import java.util.Objects;

/**
 * @author Xavier Campos
 */
public class MyPetsFragment extends Fragment {
    private static int index;
    private FragmentMyPetsBinding binding;
    private User currentUser;
    private MyPetsCommunication communication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyPetsBinding.inflate(inflater, container, false);
        communication = (MyPetsCommunication) getActivity();
        MainActivity.showShareAppButton();
        if (MainActivity.getmAuth().getCurrentUser() != null) {
            currentUser = Objects.requireNonNull(communication).getUser();
            initializeMainMenuView();
        }
        return binding.getRoot();
    }

    /**
     * Method responsible for initializing and showing the main menu view.
     */
    private void initializeMainMenuView() {
        if (currentUser.getPets().size() > 0) {
            binding.emptyPetListText.setVisibility(View.GONE);
        }

        binding.mainMenu.showPets(currentUser);
        setPetComponentsListeners();
    }

    /**
     * Method responsible for setting the listeners for all the pet components.
     */
    private void setPetComponentsListeners() {
        List<CircularEntryView> petsComponents = binding.mainMenu.getPetComponents();
        index = 0;
        InfoPetFragment testFragment = new InfoPetFragment();
        while (!petsComponents.isEmpty()) {
            CircularEntryView tmp = petsComponents.remove(0);
            tmp.setClickable(true);
            tmp.setOnClickListener(v -> {
                InfoPetFragment.setPet((Pet) tmp.getObject());
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.replace(R.id.mainActivityFrameLayout, testFragment);
                ft.commit();
                MainActivity.setActualFragment(testFragment);
                MainActivity.hideFloatingButton();
                MainActivity.hideShareAppButton();
            });
            ++index;
        }
    }
}
