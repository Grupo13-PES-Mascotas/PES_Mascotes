package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoGroupBinding;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Albert Pinto
 */
public class InfoGroupFragment extends Fragment {
    public static final int INFO_GROUP_ZOOM_IDENTIFIER = 2;
    private static final int TIMEOUT = 5;
    private static InfoGroupCommunication communication;
    private static Group group;

    private FragmentInfoGroupBinding binding;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoGroupBinding.inflate(inflater, container, false);
        communication = (InfoGroupCommunication) getActivity();
        tabLayout = binding.tabLayoutInfoGroup;

        setUpViewPager();

        communication.setToolbar(group.getName());
        String tags = getTagsFromGroup();
        binding.txtGroupTags.setText(tags);
        binding.groupDescription.setText(group.getDescription());

        addButtonListeners();

        /*ExecutorService obtainSubscribersImages = Executors.newSingleThreadExecutor();
        obtainSubscribersImages.execute(() -> getImageExecutor());

        obtainSubscribersImages.shutdown();

        try {
            obtainSubscribersImages.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return binding.getRoot();
    }

    /**
     * Add the buttons listeners.
     */
    private void addButtonListeners() {
        if (group.getGroupIcon() == null) {
            binding.imgGroup.setDrawable(getResources().getDrawable(R.drawable.icon_group, null));
        } else {
            binding.imgGroup.setDrawable(new BitmapDrawable(getResources(), group.getGroupIcon()));
        }

        User user = communication.getUser();

        if (group.getOwnerUsername().equals(user.getUsername())) {
            binding.imgGroup.setOnClickListener(v -> communication.makeGroupZoomImage(binding.imgGroup.getDrawable()));
        }
    }

    /**
     * Get the image executor.
     */
    private void getImageExecutor() {
        List<Map.Entry<String, DateTime>> subscribers = new ArrayList<>(group.getSubscribers().entrySet());
        String[] username = new String[subscribers.size()];
        Bitmap[] images = new Bitmap[subscribers.size()];
        ExecutorService obtainSubscriberImage = Executors.newCachedThreadPool();

        for (int actual = 0; actual < subscribers.size(); ++actual) {
            int finalActual = actual;
            obtainSubscriberImage.execute(() -> {
                username[finalActual] = subscribers.get(finalActual).getKey();
                images[finalActual] = InfoGroupFragment.getCommunication().findImageByUser(username[finalActual]);
            });
        }

        obtainSubscriberImage.shutdown();
        try {
            obtainSubscriberImage.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int actual = 0; actual < subscribers.size(); ++actual) {
            group.addSubscriberImage(username[actual], images[actual]);
        }

        //InfoGroupSubscriptionsFragment.showSubscribers();
        //communication.refreshActualFragment();
    }

    /**
     * Get the tags from the group.
     * @return The tags from the group
     */
    private String getTagsFromGroup() {
        StringBuilder tags = new StringBuilder("");

        for (String actualTag : group.getTags()) {
            if (!"".equals(actualTag)) {
                tags.append('#').append(actualTag).append(',');
            }
        }

        if (tags.length() == 0) {
            tags.append(getString(R.string.no_tags));
        } else {
            tags.deleteCharAt(tags.length() - 1);
        }

        return tags.toString();
    }

    /**
     * Set up the view pager.
     */
    private void setUpViewPager() {
        InfoGroupFragmentAdapter infoGroupFragmentAdapter = new InfoGroupFragmentAdapter(this);
        ViewPager2 viewPager = binding.infoGroupPager;
        viewPager.setAdapter(infoGroupFragmentAdapter);

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
                case InfoGroupFragmentAdapter.FORUMS_FRAGMENT:
                    tab.setText(R.string.info_group_forums);
                    break;
                case InfoGroupFragmentAdapter.GROUP_SUBSCRIPTIONS_FRAGMENT:
                    tab.setText(R.string.info_group_subscriptors);
                    break;
                default:
            }
        });
    }

    /**
     * Get the group.
     * @return The group
     */
    public static Group getGroup() {
        return group;
    }

    /**
     * Set the group.
     * @param group The group
     */
    public static void setGroup(Group group) {
        InfoGroupFragment.group = group;
    }

    /**
     * Get the communication.
     * @return The communication
     */
    public static InfoGroupCommunication getCommunication() {
        return communication;
    }
}
