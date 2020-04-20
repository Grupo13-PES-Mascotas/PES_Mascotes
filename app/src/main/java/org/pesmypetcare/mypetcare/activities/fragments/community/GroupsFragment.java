package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.views.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.GroupComponentView;
import org.pesmypetcare.mypetcare.databinding.FragmentGroupsBinding;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GroupsFragment extends Fragment {
    private static final int[] GROUP_SEARCH_TITLE_ID = {
        R.string.search_group_name, R.string.search_group_tag
    };
    private static final int NAME_SEARCH_MODE = 0;
    private static final String TAG_REGEX = "^[a-zA-Z0-9,]*$";
    private static final String INTERROGATION_SIGN = "?";

    private FragmentGroupsBinding binding;
    private List<Group> groups;
    private int selectedSearchMode;
    private TextInputLayout inputGroupSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        inputGroupSearch = binding.inputGroupSearch;

        setTypeSearch();
        addSearchButtonListener();

        return binding.getRoot();
    }

    /**
     * Add the listeners to the search button.
     */
    private void addSearchButtonListener() {
        binding.btnGroupSearch.setOnClickListener(v -> {
            groups = Objects.requireNonNull(CommunityFragment.getCommunication()).getAllGroups();
            String inputText = Objects.requireNonNull(inputGroupSearch.getEditText()).getText().toString()
                .toLowerCase(Locale.getDefault());
            boolean isCorrect = getGroups(inputText);

            if (isCorrect) {
                binding.groupInfoLayout.showGroups(groups);
                addGroupsViewListeners();
            }
        });
    }

    /**
     * Add the listeners to the groups view.
     */
    private void addGroupsViewListeners() {
        List<CircularEntryView> views = binding.groupInfoLayout.getGroupComponents();
        InfoGroupFragment infoGroupFragment = new InfoGroupFragment();

        for (CircularEntryView circularEntryView : views) {
            circularEntryView.setLongClickable(true);
            circularEntryView.setOnLongClickListener(v1 -> setGroupLongClickEvent(circularEntryView,
                (GroupComponentView) v1));

            circularEntryView.setOnClickListener(v -> setGroupOnClickEvent(infoGroupFragment, circularEntryView));
        }
    }

    private void setGroupOnClickEvent(InfoGroupFragment infoGroupFragment, CircularEntryView circularEntryView) {
        Group group = (Group) circularEntryView.getObject();
        infoGroupFragment.setGroup(group);
        CommunityFragment.getCommunication().showGroupFragment(infoGroupFragment);
    }

    private boolean setGroupLongClickEvent(CircularEntryView circularEntryView, GroupComponentView v1) {
        Group group = (Group) v1.getObject();
        String actualUser = CommunityFragment.getCommunication().getUser().getUsername();

        if (actualUser.equals(group.getOwnerUsername())) {
            MaterialAlertDialogBuilder dialog = createDeleteGroupDialog(circularEntryView);
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
     * Set the delete positive button listener.
     * @param group The group that has to be deleted
     * @param dialog The displayed dialog
     */
    private void setDeleteGroupPositiveButton(Group group, DialogInterface dialog) {
        CommunityFragment.getCommunication().deleteGroup(group.getName());
        binding.groupInfoLayout.removeAllViews();
        dialog.dismiss();
    }

    /**
     * Get the groups and check whether the introduced values are correct.
     * @param inputText The text that has been input
     * @return True if the introduced text is correct
     */
    private boolean getGroups(String inputText) {
        boolean isCorrect = true;

        if (selectedSearchMode == NAME_SEARCH_MODE) {
            groups = filterByName(inputText, groups);
            isCorrect = checkNoResults();
        } else if (inputText.matches(TAG_REGEX)) {
            groups = filterByTag(inputText, groups);
            isCorrect = checkNoResults();
        } else {
            showErrorMessage(R.string.tag_not_valid);
        }

        return isCorrect;
    }

    /**
     * Check whether there is no results.
     * @return True if there are not any results
     */
    private boolean checkNoResults() {
        binding.groupInfoLayout.removeAllViews();

        if (isNotResults()) {
            showErrorMessage(R.string.no_search_results);
            return false;
        }

        return true;
    }

    /**
     * Check whether there has not been any results in the group search.
     * @return True if there has not been any results in the group search
     */
    private boolean isNotResults() {
        return groups.size() == 0;
    }

    /**
     * Show an error message.
     * @param messageId The id of the message to display
     */
    private void showErrorMessage(int messageId) {
        Toast toast = Toast.makeText(getContext(), getString(messageId), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Filter the groups by the tag.
     * @param groupTags The tags of the groups to filter
     * @param groups The existing groups
     * @return The groups that contain any of the tags or a part of it
     */
    private List<Group> filterByTag(String groupTags, List<Group> groups) {
        List<Group> selectedGroups = new ArrayList<>();
        String[] tags = groupTags.split(",");
        List<String> selectedTags = new ArrayList<>(Arrays.asList(tags));

        for (Group group : groups) {
            List<String> actualTags = group.getTags();

            for (int actual = 0; actual < actualTags.size(); ++actual) {
                actualTags.set(actual, actualTags.get(actual).toLowerCase(Locale.getDefault()));
            }

            if (containsAnyTag(actualTags, selectedTags)) {
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

    /**
     * Check whether a group contains any of the specified tags.
     * @param groupTags The tags of the groups
     * @param selectedTags The selected tags
     * @return True if any of the selected tags exists in the group tags
     */
    private boolean containsAnyTag(List<String> groupTags, List<String> selectedTags) {
        for (String groupTag : groupTags) {
            for (String selectedTag : selectedTags) {
                if (groupTag.contains(selectedTag)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Filter the groups by the name.
     * @param groupName The name of the group
     * @param groups The existing groups
     * @return The groups that contains the groupName in their name
     */
    private List<Group> filterByName(String groupName, List<Group> groups) {
        List<Group> selectedGroups = new ArrayList<>();

        for (Group group : groups) {
            if (group.getName().toLowerCase(Locale.getDefault()).contains(groupName)) {
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

    /**
     * Set the search select type component.
     */
    private void setTypeSearch() {
        AutoCompleteTextView searchType = binding.inputSearchType;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.search_group_name_menu),
            getString(R.string.search_group_tag_menu)});
        searchType.setAdapter(adapter);

        searchType.setOnItemClickListener((parent, view, position, id) -> {
            selectedSearchMode = position;
            inputGroupSearch.setHint(getString(GROUP_SEARCH_TITLE_ID[selectedSearchMode]));
            Objects.requireNonNull(binding.inputGroupSearch.getEditText()).setText("");
            binding.groupInfoLayout.removeAllViews();
        });
    }
}
