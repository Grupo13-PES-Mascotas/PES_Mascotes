package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.community.groups.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class GroupsView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> groupComponents;

    public GroupsView(Context context, @Nullable AttributeSet attrs) {
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
     * @param groups The groups to display
     */
    public void showGroups(SortedSet<Group> groups) {
        for (Group group : groups) {
            CircularEntryView circularEntryView = new GroupComponentView(context, null, group).initializeComponent();
            addView(circularEntryView);
            groupComponents.add(circularEntryView);

            Space space = createSpace();
            addView(space);
        }
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
