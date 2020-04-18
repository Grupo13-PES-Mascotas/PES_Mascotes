package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentUserSubscriptionsBinding;

public class UserSubscriptionsFragment extends Fragment {
    private FragmentUserSubscriptionsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserSubscriptionsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
