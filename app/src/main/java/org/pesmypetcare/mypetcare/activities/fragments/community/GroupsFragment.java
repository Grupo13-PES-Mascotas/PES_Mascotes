package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentGroupsBinding;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.List;
import java.util.Objects;

public class GroupsFragment extends Fragment {
    private static final int[] groupSearchTitleId = {
        R.string.search_group_name, R.string.search_group_tag
    };

    private FragmentGroupsBinding binding;
    private List<Group> groups;
    private CommunityCommunication communication;
    private int selectedSearchMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        communication = (CommunityCommunication) getActivity();

        AutoCompleteTextView searchType = binding.inputSearchType;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.search_group_name_menu),
            getString(R.string.search_group_tag_menu)});
        searchType.setAdapter(adapter);

        searchType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSearchMode = position;
                binding.inputGroupSearch.setHint(getString(groupSearchTitleId[selectedSearchMode]));
                Objects.requireNonNull(binding.inputGroupSearch.getEditText()).setText("");
            }
        });

        /*searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSearchMode = position;
                binding.inputGroupSearch.setHint(getString(groupSearchTitleId[selectedSearchMode]));
                binding.inputGroupSearch.setText("");
                System.out.println(selectedSearchMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.inputGroupSearch.setText("HI");
            }
        });*/

        groups = Objects.requireNonNull(communication).getAllGroups();
        binding.groupInfoLayout.showGroups(groups);

        return binding.getRoot();
    }
}
