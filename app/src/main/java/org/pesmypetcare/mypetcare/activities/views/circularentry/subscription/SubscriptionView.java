package org.pesmypetcare.mypetcare.activities.views.circularentry.subscription;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class SubscriptionView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> groupComponents;

    public SubscriptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.groupComponents = new ArrayList<>();
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);
    }

    /**
     * Show the specified groups.
     * @param user The current user
     * @param groups The groups to display
     */
    public void showSubscriptions(User user, SortedSet<Group> groups) {
        for (String groupName : user.getSubscribedGroups()) {
            Group group = findByName(groupName, groups);
            CircularEntryView circularEntryView = new SubscriptionComponentView(context, null, user, group);
            circularEntryView.initializeComponent();
            addView(circularEntryView);
            groupComponents.add(circularEntryView);

            Space space = createSpace();
            addView(space);
        }
    }

    /**
     * Find a group by its name.
     * @param groupName The name of the group
     * @param groups The grups set
     * @return The selected group or null if it does not exist
     */
    private Group findByName(String groupName, SortedSet<Group> groups) {
        for (Group group : groups) {
            if (groupName.equals(group.getName())) {
                return group;
            }
        }

        return null;
    }

    /**
     * Method responsible for initializing the spacers.
     * @return The initialized spacer;
     */
    private Space createSpace() {
        Space space;
        space = new Space(getContext());
        space.setMinimumHeight(MIN_SPACE_SIZE);
        return space;
    }

    /**
     * Get the group components.
     * @return The group components
     */
    public List<CircularEntryView> getGroupComponents() {
        return groupComponents;
    }
}
