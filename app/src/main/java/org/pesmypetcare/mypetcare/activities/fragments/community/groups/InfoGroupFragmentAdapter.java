package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author Albert Pinto
 */
public class InfoGroupFragmentAdapter extends FragmentStateAdapter {
    public static final int FORUMS_FRAGMENT = 0;
    public static final int GROUP_SUBSCRIPTIONS_FRAGMENT = 1;
    private static final int NUM_TABS = 2;

    public InfoGroupFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == FORUMS_FRAGMENT) {
            return new ForumsFragment();
        }

        return new InfoGroupSubscriptionsFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
