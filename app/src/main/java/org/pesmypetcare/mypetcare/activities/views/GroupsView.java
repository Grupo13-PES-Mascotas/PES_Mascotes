package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsView extends LinearLayout {
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
            PetComponentView petComponentView = new GroupComponentView(context, null, group);
            addView(petComponentView);
            groupComponents.add(petComponentView);
        }
    }


    public List<PetComponentView> getGroupComponents() {
        return groupComponents;
    }
}
