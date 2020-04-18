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
    private FragmentCommunityBinding binding;
    private CommunityFragmentAdapter communityFragmentAdapter;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);

        setUpViewPager();

        return binding.getRoot();
    }

    private void setUpViewPager() {
        communityFragmentAdapter = new CommunityFragmentAdapter(this);
        viewPager = binding.communityPager;
        viewPager.setAdapter(communityFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutCommunity;
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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

        tabLayoutMediator.attach();
    }
}
