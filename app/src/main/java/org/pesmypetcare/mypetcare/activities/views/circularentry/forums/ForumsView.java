package org.pesmypetcare.mypetcare.activities.views.circularentry.forums;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class ForumsView extends LinearLayout {
    private static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> forumComponents;

    public ForumsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.forumComponents = new ArrayList<>();
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);
    }

    /**
     * Show the specified group subscribers.
     * @param group The group to display the forum
     */
    public void showForums(Group group) {
        for (Forum forum : group.getForums()) {
            CircularEntryView circularEntryView = new ForumsComponentView(context, null, forum);
            circularEntryView.initializeComponent();
            addView(circularEntryView);
            forumComponents.add(circularEntryView);

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
    public List<CircularEntryView> getForumComponents() {
        return forumComponents;
    }
}
