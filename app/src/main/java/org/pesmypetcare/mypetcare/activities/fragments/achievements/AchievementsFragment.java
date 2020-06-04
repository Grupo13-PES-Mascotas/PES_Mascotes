package org.pesmypetcare.mypetcare.activities.fragments.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.databinding.FragmentAchievementsBinding;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente & Álvaro Trius
 */
public class AchievementsFragment extends Fragment {
    private FragmentAchievementsBinding binding;
    private AchievementsCommunication communication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        communication = (AchievementsCommunication) getActivity(); //debería pedir al mainActivity de todas las medals

        try {
            initializeAchievementsController();
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }

        return binding.getRoot();
    }

    /**
     * Initializes de achievements controller
     */
    private void initializeAchievementsController() throws MyPetCareException {
        List<UserMedalData> userAchievementList = communication.getAllAchievements();
    }
}
