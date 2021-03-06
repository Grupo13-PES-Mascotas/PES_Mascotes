package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.databinding.FragmentForumsBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;

import java.util.List;
import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class ForumsFragment extends Fragment {
    private static final String INTERROGATION_SIGN = "?";
    private static final String WHITE_SPACE = " ";
    private static FragmentForumsBinding binding;
    private static String deleteForumTitle;
    private static String deleteForumMessage;
    private static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        binding.forumsViewLayout.showForums(InfoGroupFragment.getGroup());
        showForums();

        deleteForumTitle = getString(R.string.delete_forum_title);
        deleteForumMessage = getString(R.string.delete_group_message);
        context = getContext();

        return binding.getRoot();
    }

    /**
     * Method responsible for setting the listeners of the forum view.
     */
    private static void setListenersForumsView() {
        List<CircularEntryView> components = binding.forumsViewLayout.getForumComponents();
        PostsFragment postsFragment = new PostsFragment();

        for (CircularEntryView component : components) {
            component.setOnLongClickListener(v -> setForumLongClickEvent(component));
            component.setOnClickListener(v -> setForumOnClickEvent(postsFragment, component));
        }
    }

    /**
     * Method responsible for setting the on click event of a component of the view.
     * @param postsFragment The post fragment to load
     * @param component The component where the listener will be added
     */
    private static void setForumOnClickEvent(PostsFragment postsFragment, CircularEntryView component) {
        PostsFragment.setForum((Forum) component.getObject());
        InfoGroupFragment.getCommunication().showForum(postsFragment);
        MainActivity.hideFloatingButton();
    }

    /**
     * Setter of the long click event of a component.
     * @param component The component for which we want to add the long click event
     * @return True if the user is the forum owner or false otherwise
     */
    private static boolean setForumLongClickEvent(CircularEntryView component) {
        Forum forum = (Forum) component.getObject();
        String username = InfoGroupFragment.getCommunication().getUser().getUsername();

        if (forum.getOwnerUsername().equals(username)) {
            MaterialAlertDialogBuilder dialog = createDeleteForumDialog(component);
            dialog.show();
            return true;
        }

        return false;
    }

    /**
     * Create a dialog to delete the group.
     * @param circularEntryView The entry to which the dialog is associated to
     * @return The dialog that is associated with the entry
     */
    private static MaterialAlertDialogBuilder createDeleteForumDialog(CircularEntryView circularEntryView) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(Objects.requireNonNull(context),
            R.style.AlertDialogTheme);
        Forum forum = (Forum) circularEntryView.getObject();
        dialog.setTitle(deleteForumTitle + WHITE_SPACE + forum.getName() + INTERROGATION_SIGN);
        dialog.setMessage(deleteForumMessage + WHITE_SPACE + forum.getName() + INTERROGATION_SIGN);

        dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
            setDeleteGroupPositiveButton(forum, dialog1);
        });

        dialog.setNegativeButton(R.string.no, (dialog1, which) -> {
            dialog1.dismiss();
        });

        return dialog;
    }

    /**
     * Setter of the delete forum listener.
     * @param forum The forum to delete
     * @param dialog1 The dialog to hide
     */
    private static void setDeleteGroupPositiveButton(Forum forum, DialogInterface dialog1) {
        InfoGroupFragment.getCommunication().deleteForum(forum);
        dialog1.dismiss();
        showForums();
    }

    /**
     * Method responsible for showing all the forums.
     */
    public static void showForums() {
        binding.forumsViewLayout.removeAllViews();
        binding.forumsViewLayout.showForums(InfoGroupFragment.getGroup());
        setListenersForumsView();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.forumsViewLayout.removeAllViews();
        binding.forumsViewLayout.showForums(InfoGroupFragment.getGroup());
        setListenersForumsView();
        MainActivity.showFloatingButton();
    }
}
