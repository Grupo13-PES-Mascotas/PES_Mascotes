package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

public class InfoPetFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 4;
    public static final int INFO_PET_BASIC = 0;
    public static final int INFO_PET_HEALTH = 1;
    public static final int INFO_PET_MEDICATION = 2;
    public static final int INFO_PET_MEALS = 3;

    public InfoPetFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        switch (position) {
            case INFO_PET_BASIC:
                fragment = new InfoPetBasicFragment();
                break;
            case INFO_PET_HEALTH:
                fragment = new InfoPetHealthFragment();
                break;
            case INFO_PET_MEDICATION:
                fragment = new InfoPetMedicationFragment();
                break;
            case INFO_PET_MEALS:
                fragment = new InfoPetMealsFragment();
                break;
            default:
                fragment = null;
        }

        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
