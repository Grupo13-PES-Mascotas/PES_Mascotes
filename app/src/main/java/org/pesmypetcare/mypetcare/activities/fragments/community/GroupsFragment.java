package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentGroupsBinding;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GroupsFragment extends Fragment {
    private static final int[] groupSearchTitleId = {
        R.string.search_group_name, R.string.search_group_tag
    };
    private static final int NAME_SEARCH_MODE = 0;

    private FragmentGroupsBinding binding;
    private List<Group> groups;
    private CommunityCommunication communication;
    private int selectedSearchMode;
    private TextInputLayout inputGroupSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        communication = (CommunityCommunication) getActivity();
        inputGroupSearch = binding.inputGroupSearch;

        setTypeSearch();

        binding.btnGroupSearch.setOnClickListener(v -> {
            groups = Objects.requireNonNull(communication).getAllGroups();
            String inputText = Objects.requireNonNull(inputGroupSearch.getEditText()).getText().toString()
                .toLowerCase();
            if (selectedSearchMode == NAME_SEARCH_MODE) {
                groups = filterByName(inputText, groups);
                binding.groupInfoLayout.removeAllViews();
                if (isNotResults()) {
                    showErrorMessage(R.string.no_search_results);
                    return;
                }
            } else if (inputText.matches("^[a-zA-Z0-9,]*$")){
                groups = filterByTag(inputText, groups);
                binding.groupInfoLayout.removeAllViews();
                if (isNotResults()) {
                    showErrorMessage(R.string.no_search_results);
                    return;
                }
            } else {
                showErrorMessage(R.string.tag_not_valid);
                return;
            }

            binding.groupInfoLayout.showGroups(groups);
        });

        return binding.getRoot();
    }

    private boolean isNotResults() {
        return groups.size() == 0;
    }

    private void showErrorMessage(int messageId) {
        Toast toast = Toast.makeText(getContext(), getString(messageId), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private List<Group> filterByTag(String groupTags, List<Group> groups) {
        List<Group> selectedGroups = new ArrayList<>();
        String[] tags = groupTags.split(",");
        List<String> selectedTags = new ArrayList<>(Arrays.asList(tags));

        for (Group group : groups) {
            List<String> actualTags = group.getTags();

            for (int actual = 0; actual < actualTags.size(); ++actual) {
                actualTags.set(actual, actualTags.get(actual).toLowerCase());
            }

            if (containsAnyTag(actualTags, selectedTags)) {
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

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

    private List<Group> filterByName(String groupName, List<Group> groups) {
        List<Group> selectedGroups = new ArrayList<>();

        for (Group group : groups) {
            if (group.getName().toLowerCase().contains(groupName)) {
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

    private void setTypeSearch() {
        AutoCompleteTextView searchType = binding.inputSearchType;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.search_group_name_menu),
            getString(R.string.search_group_tag_menu)});
        searchType.setAdapter(adapter);

        searchType.setOnItemClickListener((parent, view, position, id) -> {
            selectedSearchMode = position;
            inputGroupSearch.setHint(getString(groupSearchTitleId[selectedSearchMode]));
            Objects.requireNonNull(binding.inputGroupSearch.getEditText()).setText("");
            binding.groupInfoLayout.removeAllViews();
        });
    }
}
