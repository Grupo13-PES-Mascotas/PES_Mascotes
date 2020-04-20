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

        if (isOwner()) {
            btnSubscribe.setText(getString(R.string.unsubscribe_owner));
        } else if (isUserSubscriber()) {
            btnSubscribe.setText(getString(R.string.desubscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
            btnSubscribe.setFocusable(true);
        } else {
            btnSubscribe.setText(getString(R.string.subscribe));
            btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
            btnSubscribe.setFocusable(true);
        }

        addSubscribeButtonListener();

        return binding.getRoot();
    }

    /**
     * Add the listener for the subscribe button.
     */
    private void addSubscribeButtonListener() {
        btnSubscribe.setOnClickListener(v -> {
            if (!isOwner()) {
                if (isUserSubscriber()) {
                    btnSubscribe.setText(getString(R.string.subscribe));
                    btnSubscribe.setBackgroundColor(getResources().getColor(R.color.green));
                    InfoGroupFragment.getCommunication().removeSubscription(InfoGroupFragment.getGroup());
                    showSubscribers();
                } else {
                    btnSubscribe.setText(getString(R.string.desubscribe));
                    btnSubscribe.setBackgroundColor(getResources().getColor(R.color.red));
                    InfoGroupFragment.getCommunication().addSubscription(InfoGroupFragment.getGroup());
                    showSubscribers();
                }
            }
        });
    }

    /**
     * Check whether the user is the owner of the group.
     * @return True if the user is the owner or false otherwise
     */
    private boolean isOwner() {
        return InfoGroupFragment.getCommunication().getUser().getUsername()
            .equals(InfoGroupFragment.getGroup().getOwnerUsername());
    }

    /**
     * Check whether the user is subscribed.
     * @return True if the user is subscribed
     */
    private boolean isUserSubscriber() {
        User user = InfoGroupFragment.getCommunication().getUser();
        return InfoGroupFragment.getGroup().isUserSubscriber(user);
    }

    @Override
    public void onResume() {
        super.onResume();
        showSubscribers();
    }

    /**
     * Show the subscribers of the group.
     */
    private void showSubscribers() {
        binding.subscribersViewLayout.removeAllViews();
        binding.subscribersViewLayout.showSubscribers(InfoGroupFragment.getGroup());
    }
}
