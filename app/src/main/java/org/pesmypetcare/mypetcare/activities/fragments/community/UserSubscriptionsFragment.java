package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.views.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.SubscriptionComponentView;
import org.pesmypetcare.mypetcare.databinding.FragmentUserSubscriptionsBinding;
import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

public class UserSubscriptionsFragment extends Fragment {
    private static final String INTERROGATION_SIGN = "?";

    private FragmentUserSubscriptionsBinding binding;
    private SortedSet<Group> groups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserSubscriptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Show the subscriptions.
     */
    private void showSubscriptions() {
        groups = CommunityFragment.getCommunication().getAllGroups();
        User user = CommunityFragment.getCommunication().getUser();

        binding.subscriptionViewLayout.removeAllViews();
        binding.subscriptionViewLayout.showSubscriptions(user, groups);

        List<CircularEntryView> components = binding.subscriptionViewLayout.getGroupComponents();
        InfoGroupFragment infoGroupFragment = new InfoGroupFragment();

        for (CircularEntryView circularEntryView : components) {
            circularEntryView.setLongClickable(true);
            circularEntryView.setOnLongClickListener(v1 -> setGroupLongClickEvent(circularEntryView,
                (SubscriptionComponentView) v1));

            circularEntryView.setOnClickListener(v -> setGroupOnClickEvent(infoGroupFragment, circularEntryView));
        }
    }

    /**
     * Set the group on click listener.
     * @param subscriptionComponentView The SubscriptionComponenetView fragment to display
     * @param circularEntryView The entry
     */
    private boolean setGroupLongClickEvent(CircularEntryView circularEntryView,
                                           SubscriptionComponentView subscriptionComponentView) {
        Group group = (Group) subscriptionComponentView.getObject();
        String actualUser = CommunityFragment.getCommunication().getUser().getUsername();

        if (actualUser.equals(group.getOwnerUsername())) {
            MaterialAlertDialogBuilder dialog = createDeleteGroupDialog(circularEntryView);
            dialog.show();
            return true;
        }

        return false;
    }

    /**
     * Set the delete positive button listener.
     * @param group The group that has to be deleted
     * @param dialog The displayed dialog
     */
    private void setDeleteGroupPositiveButton(Group group, DialogInterface dialog) {
        CommunityFragment.getCommunication().deleteGroup(group.getName());
        binding.subscriptionViewLayout.removeAllViews();
        dialog.dismiss();
    }

    /**
     * Create a dialog to delete the group.
     * @param circularEntryView The entry to which the dialog is associated to
     * @return The dialog that is associated with the entry
     */
    private MaterialAlertDialogBuilder createDeleteGroupDialog(CircularEntryView circularEntryView) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        Group group = (Group) circularEntryView.getObject();
        dialog.setTitle(getString(R.string.delete_group_title) + " " + group.getName() + INTERROGATION_SIGN);
        dialog.setMessage(getString(R.string.delete_group_message) + " " + group.getName() + INTERROGATION_SIGN);

        dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
            setDeleteGroupPositiveButton(group, dialog1);
        });

        dialog.setNegativeButton(R.string.no, (dialog1, which) -> {
            dialog1.dismiss();
        });

        return dialog;
    }

    /**
     * Set the group on click listener.
     * @param infoGroupFragment The InfoGrup fragment to display
     * @param circularEntryView The entry
     */
    private void setGroupOnClickEvent(InfoGroupFragment infoGroupFragment, CircularEntryView circularEntryView) {
        Group group = (Group) circularEntryView.getObject();
        InfoGroupFragment.setGroup(group);
        CommunityFragment.getCommunication().showGroupFragment(infoGroupFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        showSubscriptions();
    }
}
