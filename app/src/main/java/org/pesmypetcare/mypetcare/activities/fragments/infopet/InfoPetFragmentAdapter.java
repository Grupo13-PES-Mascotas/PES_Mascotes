package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InfoPetFragmentAdapter extends FragmentStateAdapter {
    public static final int INFO_PET_BASIC = 0;
    public static final int INFO_PET_HEALTH = 1;
    public static final int INFO_PET_MEALS = 2;
    public static final int INFO_PET_EXERCISE = 3;
    public static final int INFO_PET_WASHING = 4;
    public static final int INFO_PET_MEDICAL_PROFILE = 5;
    public static final int INFO_PET_MEDICATION = 6;
    public static final int INFO_PET_VET_VISITS = 7;
    private static final int NUM_TABS = 8;

    public InfoPetFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case INFO_PET_BASIC:
                return new InfoPetBasicFragment();
            case INFO_PET_HEALTH:
                return new InfoPetHealthFragment();
            case INFO_PET_MEALS:
                return new InfoPetMealsFragment();
            case INFO_PET_EXERCISE:
                return new InfoPetExercise();
            case INFO_PET_WASHING:
                return new InfoPetWashing();
            case INFO_PET_MEDICAL_PROFILE:
                return new InfoPetMedicalProfile();
            case INFO_PET_MEDICATION:
                return new InfoPetMedicationFragment();
            case INFO_PET_VET_VISITS:
                return new InfoPetVetVisits();
            default:
                return new InfoPetFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
