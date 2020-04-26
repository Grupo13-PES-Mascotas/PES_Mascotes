package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.posts.Post;

import java.util.ArrayList;
import java.util.List;

public class PostView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> postComponents;

    public PostView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.postComponents = new ArrayList<>();
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);
    }

    /**
     * Show the specified group subscribers.
     * @param forum The forum to display the posts
     */
    public void showPosts(Forum forum) {
        for (Post post : forum.getPosts()) {
            CircularEntryView circularEntryView = new PostComponentView(context, null, post);
            circularEntryView.initializeComponent();
            addView(circularEntryView);
            postComponents.add(circularEntryView);

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
    public List<CircularEntryView> getPostComponents() {
        return postComponents;
    }
}
