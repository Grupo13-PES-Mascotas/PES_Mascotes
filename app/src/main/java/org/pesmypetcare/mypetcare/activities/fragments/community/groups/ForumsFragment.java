package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.databinding.FragmentForumsBinding;

public class ForumsFragment extends Fragment {
    private static FragmentForumsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        binding.forumsViewLayout.showForums(InfoGroupFragment.getGroup());

        /*List<CircularEntryView> components = binding.forumsViewLayout.getForumComponents();

        for (CircularEntryView component : components) {

        }*/

        return binding.getRoot();
    }

    public static void showGroups() {
        binding.forumsViewLayout.removeAllViews();
        binding.forumsViewLayout.showForums(InfoGroupFragment.getGroup());
    }

    @Override
    public void onResume() {
        super.onResume();
        showGroups();
        MainActivity.showFloatingButton();
    }
}
