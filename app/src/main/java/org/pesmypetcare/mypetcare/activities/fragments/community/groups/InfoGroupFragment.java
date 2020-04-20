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
    private static Group group;

    private FragmentInfoGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupBinding.inflate(inflater, container, false);
        communication = (InfoGroupCommunication) getActivity();

        setUpViewPager();

        communication.setToolbar(group.getName());
        String tags = getTagsFromGroup();
        binding.txtGroupTags.setText(tags);

        return binding.getRoot();
    }

    private String getTagsFromGroup() {
        StringBuilder tags = new StringBuilder("");

        for (String actualTag : group.getTags()) {
            if (!"".equals(actualTag)) {
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

    /**
     * Set up the view pager.
     */
    private void setUpViewPager() {
        InfoGroupFragmentAdapter infoGroupFragmentAdapter = new InfoGroupFragmentAdapter(this);
        ViewPager2 viewPager = binding.infoGroupPager;
        viewPager.setAdapter(infoGroupFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutInfoGroup;
        TabLayoutMediator tabLayoutMediator = createTabLayoutMediator(viewPager, tabLayout);

        tabLayoutMediator.attach();
    }

    /**
     * Create the tab layout mediator.
     * @param viewPager The viewpager of the fragment
     * @param tabLayout The tabLayout of the fragment
     * @return The TabLayoutMediator for the fragment
     */
    private TabLayoutMediator createTabLayoutMediator(ViewPager2 viewPager, TabLayout tabLayout) {
        return new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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
    }

    /**
     * Get the group.
     * @return The group
     */
    public static Group getGroup() {
        return group;
    }

    /**
     * Set the group.
     * @param group The group
     */
    public static void setGroup(Group group) {
        InfoGroupFragment.group = group;
    }

    /**
     * Get the communication.
     * @return The communication
     */
    public static InfoGroupCommunication getCommunication() {
        return communication;
    }
}
