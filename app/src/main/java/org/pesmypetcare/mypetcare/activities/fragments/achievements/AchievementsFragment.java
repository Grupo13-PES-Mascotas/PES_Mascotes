package org.pesmypetcare.mypetcare.activities.fragments.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentAchievementsBinding;

/**
 * @author Daniel Clemente & Álvaro Trius
 */
public class AchievementsFragment extends Fragment {
    private FragmentAchievementsBinding binding;
    private AchievementsCommunication communication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        communication = (AchievementsCommunication) getActivity();

        return binding.getRoot();
    }
}
