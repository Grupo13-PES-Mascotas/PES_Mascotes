package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<PetComponentView> groupComponents;

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

    public void showGroups(List<Group> groups) {
        for (Group group : groups) {
            PetComponentView petComponentView = new GroupComponentView(context, null, group).initializeComponent();
            addView(petComponentView);
            groupComponents.add(petComponentView);

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


    public List<PetComponentView> getGroupComponents() {
        return groupComponents;
    }
}
