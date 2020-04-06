package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPet2Binding;

public class InfoPetFragment2 extends Fragment {
    private FragmentInfoPet2Binding binding;
    private InfoPetFragmentAdapter infoPetFragmentAdapter;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPet2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoPetFragmentAdapter = new InfoPetFragmentAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(infoPetFragmentAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutInfoPet);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch(position) {
                case InfoPetFragmentAdapter.INFO_PET_BASIC:
                    tab.setText(R.string.pet_info_basic);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_HEALTH:
                    tab.setText(R.string.pet_info_health);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_MEDICATION:
                    tab.setText(R.string.pet_info_medication);
                    break;
                default:

            }
        });

        tabLayoutMediator.attach();
    }
}
