package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentGroupsBinding;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.List;
import java.util.Objects;

public class GroupsFragment extends Fragment {
    private FragmentGroupsBinding binding;
    private List<Group> groups;
    private CommunityCommunication communication;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        communication = (CommunityCommunication) getActivity();

        groups = Objects.requireNonNull(communication).getAllGroups();
        binding.groupInfoLayout.showGroups(groups);

        return binding.getRoot();
    }
}
