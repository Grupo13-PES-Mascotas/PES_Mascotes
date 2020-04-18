package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoGroupBinding;
import org.pesmypetcare.mypetcare.features.community.Group;

public class InfoGroupFragment extends Fragment {
    private static InfoGroupCommunication communication;

    private FragmentInfoGroupBinding binding;
    private InfoGroupFragmentAdapter infoGroupFragmentAdapter;
    private ViewPager2 viewPager;
    private Group group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupBinding.inflate(inflater, container, false);
        communication = (InfoGroupCommunication) getActivity();

        setUpViewPager();

        String tags = getTagsFromGroup();
        binding.txtGroupTags.setText(tags);

        return binding.getRoot();
    }

    private String getTagsFromGroup() {
        StringBuilder tags = new StringBuilder("");

        for (String actualTag : group.getTags()) {
            if (!actualTag.equals("")) {
                tags.append('#').append(actualTag).append(',');
            }
        }

        if (tags.length() == 0) {
            tags.append(getString(R.string.no_tags));
        } else {
            tags.deleteCharAt(tags.length() - 1);
        }

        return tags.toString();
    }

    private void setUpViewPager() {
        infoGroupFragmentAdapter = new InfoGroupFragmentAdapter(this);
        viewPager = binding.infoGroupPager;
        viewPager.setAdapter(infoGroupFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutInfoGroup;
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case InfoGroupFragmentAdapter.FORUMS_FRAGMENT:
                    tab.setText(R.string.info_group_forums);
                    break;
                case InfoGroupFragmentAdapter.GROUP_SUBSCRIPTIONS_FRAGMENT:
                    tab.setText(R.string.info_group_subscriptors);
                    break;
                default:
            }
        });

        tabLayoutMediator.attach();
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static InfoGroupCommunication getCommunication() {
        return communication;
    }

    public static boolean isUserSubscriber() {
        return true;
    }
}
