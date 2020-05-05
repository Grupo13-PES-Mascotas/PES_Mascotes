package org.pesmypetcare.mypetcare.activities.views.entryview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.R;

/**
 * @author Albert Pinto
 */
@SuppressLint("ViewConstructor")
public class EntryView extends LinearLayout {
    private static final int MIN_SPACE_SIZE = 20;
    private static final int PADDING = 20;
    private Builder builder;

    private EntryView(Context context, @Nullable AttributeSet attrs, Builder builder) {
        super(context, attrs);
        this.builder = builder;

        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        setBackground(getResources().getDrawable(R.drawable.entry_background, null));

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING,
            getResources().getDisplayMetrics());

        setPadding(padding, padding, padding, padding);

        String name = builder.getName();

        if (name != null) {
            name = name.toUpperCase();
            TextView nameView = getEntryTextView(context, name);
            nameView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
            nameView.setTypeface(null, Typeface.BOLD);
            addView(nameView);

            Space space = createSpace();
            addView(space);
        }

        String[] entryLabels = builder.getEntryLabels();
        String[] entries = builder.getEntries();
        int nEntries = entryLabels.length;

        for (int actual = 0; actual < nEntries; ++actual) {
            LinearLayout layoutEntry = new LinearLayout(context);
            layoutEntry.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
            layoutEntry.setOrientation(LinearLayout.HORIZONTAL);

            TextView entryLabelView = getEntryTextView(context, entryLabels[actual] + ": ");
            entryLabelView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
            layoutEntry.addView(entryLabelView);

            TextView entryView = getEntryTextView(context, entries[actual]);
            layoutEntry.addView(entryView);

            addView(layoutEntry);

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

    @NonNull
    @SuppressLint("WrongConstant")
    private TextView getEntryTextView(Context context, String text) {
        TextView entryLabelView = new TextView(context);
        entryLabelView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT));
        entryLabelView.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);
        entryLabelView.setText(text);
        return entryLabelView;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        private Context context;
        private String[] entryLabels;
        private String[] entries;
        private String name;

        public Builder(Context context) {
            this.context = context;
        }

        public String[] getEntryLabels() {
            return entryLabels;
        }

        public void setEntryLabels(String[] entryLabels) {
            this.entryLabels = entryLabels;
        }

        public String[] getEntries() {
            return entries;
        }

        public void setEntries(String[] entries) {
            this.entries = entries;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public EntryView build() throws InvalidBuildParameters {
            if (entryLabels == null || entries == null || entryLabels.length != entries.length) {
                throw new InvalidBuildParameters();
            }
            return new EntryView(context, null, this);
        }
    }
}
