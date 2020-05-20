package org.pesmypetcare.mypetcare.activities.fragments.community;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author Albert Pinto
 */
public class CommunityFragmentAdapter extends FragmentStateAdapter {
    public static final int GROUPS_FRAGMENT = 0;
    public static final int USER_SUBSCRIPTIONS_FRAGMENT = 1;
    private static final int NUM_TABS = 2;

    public CommunityFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == GROUPS_FRAGMENT) {
            return new GroupsFragment();
        }

        return new UserSubscriptionsFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
