package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoGroupSubscriptionsBinding;
import org.pesmypetcare.mypetcare.features.users.User;

public class InfoGroupSubscriptionsFragment extends Fragment {
    private FragmentInfoGroupSubscriptionsBinding binding;
    private MaterialButton btnSubscribe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupSubscriptionsBinding.inflate(inflater, container, false);
        btnSubscribe = binding.addSubscription;

        if (isUserSubscriber()) {
            btnSubscribe.setText(getString(R.string.desubscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnSubscribe.setText(getString(R.string.subscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
        }

        btnSubscribe.setOnClickListener(v -> {
            if (isUserSubscriber()) {
                btnSubscribe.setText(getString(R.string.subscribe));
                btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                btnSubscribe.setText(getString(R.string.desubscribe));
                btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
                InfoGroupFragment.getCommunication().addSubscription(InfoGroupFragment.getGroup());
            }
        });

        return binding.getRoot();
    }

    private boolean isUserSubscriber() {
        User user = InfoGroupFragment.getCommunication().getUser();
        return InfoGroupFragment.getGroup().isUserSubscriber(user);
    }
}
