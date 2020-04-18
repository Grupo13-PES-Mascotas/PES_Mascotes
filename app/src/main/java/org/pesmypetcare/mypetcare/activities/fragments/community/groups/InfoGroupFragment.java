package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.mypetcare.databinding.FragmentInfoGroupBinding;

public class InfoGroupFragment extends Fragment {
    private FragmentInfoGroupBinding binding;
    private InfoGroupFragmentAdapter infoGroupFragmentAdapter;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupBinding.inflate(inflater, container, false);

        setUpViewPager();

        return binding.getRoot();
    }

    private void setUpViewPager() {
        infoGroupFragmentAdapter = new InfoGroupFragmentAdapter(this);
        viewPager = binding.infoGroupPager;
        viewPager.setAdapter(infoGroupFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutInfoGroup;
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {

        });
    }
}
