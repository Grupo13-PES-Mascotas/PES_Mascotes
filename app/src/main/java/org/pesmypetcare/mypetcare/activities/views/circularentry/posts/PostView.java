package org.pesmypetcare.mypetcare.activities.views.circularentry.posts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class PostView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> postComponents;

    public PostView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.postComponents = new ArrayList<>();
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);
    }

    /**
     * Show the specified group subscribers.
     * @param forum The forum to display the posts
     */
    public void showPosts(Forum forum) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START;

        User user = InfoGroupFragment.getCommunication().getUser();
        DateTime actualDate = null;

        for (Post post : forum.getPosts()) {
            actualDate = updateActualDate(actualDate, post);

            CircularEntryView circularEntryView = new PostComponentView(context, null, post, user);
            circularEntryView.initializeComponent();
            addView(circularEntryView);
            postComponents.add(circularEntryView);

            Space verticalSpace = createSpace();
            addView(verticalSpace);
        }
    }

    /**
     * Update the actual date if it has changed.
     * @param actualDate The actual date
     * @param post The post that has to be displayed
     * @return THe actual date updated
     */
    @NonNull
    private DateTime updateActualDate(DateTime actualDate, Post post) {
        DateTime postCreationDate = post.getCreationDate();
        DateTime postDate = null;

        try {
            postDate = DateTime.Builder.build(postCreationDate.getYear(), postCreationDate.getMonth(),
                postCreationDate.getDay());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        if (actualDate == null || Objects.requireNonNull(postDate).compareTo(actualDate) > 0) {
            actualDate = addDateLabel(actualDate, postDate);
        }

        return actualDate;
    }

    /**
     * Add the actual date.
     * @param actualDate The actual date
     * @param postDate The date of the post
     * @return The new actual date
     */
    @NonNull
    private DateTime addDateLabel(DateTime actualDate, DateTime postDate) {
        if (actualDate != null) {
            Space verticalSpace = createSpace();
            addView(verticalSpace);
        }

        TextView dateLabel = new TextView(getContext());
        dateLabel.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT));
        dateLabel.setGravity(Gravity.CENTER);
        String strPostDate = Objects.requireNonNull(postDate).toString();
        dateLabel.setText(strPostDate.substring(0, strPostDate.indexOf('T')));
        dateLabel.setTextColor(getResources().getColor(R.color.colorPrimary, null));

        addView(dateLabel);

        Space verticalSpace = createSpace();
        addView(verticalSpace);

        return postDate;
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
