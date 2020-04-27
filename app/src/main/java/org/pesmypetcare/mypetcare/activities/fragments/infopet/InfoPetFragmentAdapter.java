package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InfoPetFragmentAdapter extends FragmentStateAdapter {
    public static final int INFO_PET_BASIC = 0;
    public static final int INFO_PET_HEALTH = 1;
    public static final int INFO_PET_MEDICATION = 2;
    public static final int INFO_PET_MEALS = 3;
    private static final int NUM_TABS = 4;

    public InfoPetFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == INFO_PET_BASIC) {
            return new InfoPetBasicFragment();
        }
        if (position == INFO_PET_HEALTH) {
            return new InfoPetHealthFragment();
        }
        if (position == INFO_PET_MEDICATION) {
            return new InfoPetMedicationFragment();
        }
        if (position == INFO_PET_MEALS) {
            return new InfoPetMealsFragment();
        }
        return new InfoPetFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
