package org.pesmypetcare.mypetcare.activities.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentCommunityBinding;

public class CommunityFragment extends Fragment {
    private static CommunityCommunication communication;

    private FragmentCommunityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        communication = (CommunityCommunication) getActivity();

        setUpViewPager();

        return binding.getRoot();
    }

    /**
     * Set up the view pager.
     */
    private void setUpViewPager() {
        CommunityFragmentAdapter communityFragmentAdapter = new CommunityFragmentAdapter(this);
        ViewPager2 viewPager = binding.communityPager;
        viewPager.setAdapter(communityFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutCommunity;
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
                    case CommunityFragmentAdapter.GROUPS_FRAGMENT:
                        tab.setText(R.string.community_group_search);
                        break;
                    case CommunityFragmentAdapter.USER_SUBSCRIPTIONS_FRAGMENT:
                        tab.setText(R.string.community_my_subscriptions);
                        break;
                    default:
                }
            });
    }

    /**
     * Get the communication.
     * @return The communication
     */
    public static CommunityCommunication getCommunication() {
        return communication;
    }
}
