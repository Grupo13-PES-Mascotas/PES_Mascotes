package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InfoGroupFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 2;

    public InfoGroupFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ForumsFragment();
        }

        return new InfoGroupSubscriptionsFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
