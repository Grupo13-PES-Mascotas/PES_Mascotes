package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoGroupSubscriptionsBinding;

public class InfoGroupSubscriptionsFragment extends Fragment {
    private FragmentInfoGroupSubscriptionsBinding binding;
    private MaterialButton btnSubscribe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupSubscriptionsBinding.inflate(inflater, container, false);
        btnSubscribe = binding.addSubscription;

        if (InfoGroupFragment.isUserSubscriber()) {
            btnSubscribe.setText(getString(R.string.desubscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnSubscribe.setText(getString(R.string.subscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
        }

        btnSubscribe.setOnClickListener(v -> {
            if (InfoGroupFragment.isUserSubscriber()) {
                btnSubscribe.setText(getString(R.string.desubscribe));
                btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                btnSubscribe.setText(getString(R.string.subscribe));
                btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        return binding.getRoot();
    }
}
