package org.pesmypetcare.mypetcare.activities.views.circularentry.subscriber;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.features.community.groups.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class SubscribersView extends LinearLayout {
    public static final int MIN_SPACE_SIZE = 20;
    private Context context;
    private List<CircularEntryView> groupComponents;

    public SubscribersView(Context context, @Nullable AttributeSet attrs) {
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
     * Show the specified group subscribers.
     * @param group The group to display the subscribers
     */
    public void showSubscribers(Group group) {
        List<Map.Entry<String, DateTime>> subscriptions = new ArrayList<>(group.getSubscribers().entrySet());
        CircularEntryView[] components = new CircularEntryView[subscriptions.size()];
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int actual = 0; actual < subscriptions.size(); ++actual) {
            int finalActual = actual;
            executorService.execute(() -> {
                String username = subscriptions.get(finalActual).getKey();
                DateTime subscriptionDate = subscriptions.get(finalActual).getValue();
                components[finalActual] = new SubscriberComponentView(context, null, username,
                    subscriptionDate, group);
                components[finalActual].initializeComponent();
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (CircularEntryView component : components) {
            addView(component);
            groupComponents.add(component);

            Space space = createSpace();
            addView(space);
        }

        /*
        for (Map.Entry<String, DateTime> subscription : group.getSubscribers().entrySet()) {
            String username = subscription.getKey();
            DateTime subscriptionDate = subscription.getValue();
            CircularEntryView circularEntryView = new SubscriberComponentView(context, null, username,
                subscriptionDate, group);

            circularEntryView.initializeComponent();
            addView(circularEntryView);
            groupComponents.add(circularEntryView);

            Space space = createSpace();
            addView(space);
        }*/
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
