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

import java.util.Arrays;
import java.util.Locale;

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

        setLayoutConfiguration();

        String name = builder.getName();

        if (name != null) {
            addName(context, name);
        }

        addEntries(context, builder);
    }

    /**
     * Add the entries.
     * @param context The context of the application
     * @param builder The builder for the EntryView
     */
    private void addEntries(Context context, Builder builder) {
        String[] entryLabels = builder.getEntryLabels();
        String[] entries = builder.getEntries();
        int nEntries = entryLabels.length;

        for (int actual = 0; actual < nEntries; ++actual) {
            addLayoutEntry(context, getEntryTextView(context, entryLabels[actual] + ": "),
                getEntryTextView(context, entries[actual]));
        }
    }

    /**
     * Add the layout entry.
     * @param context The context of the application
     * @param entryLabel The layout entry label
     * @param entry The entry
     */
    private void addLayoutEntry(Context context, TextView entryLabel, TextView entry) {
        LinearLayout layoutEntry = new LinearLayout(context);
        layoutEntry.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT));
        layoutEntry.setOrientation(LinearLayout.HORIZONTAL);

        entryLabel.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        layoutEntry.addView(entryLabel);
        layoutEntry.addView(entry);

        addView(layoutEntry);

        Space space = createSpace();
        addView(space);
    }

    /**
     * Add the name.
     * @param context The context of the application
     * @param name The name to add
     */
    private void addName(Context context, String name) {
        TextView nameView = getEntryTextView(context, name.toUpperCase(Locale.getDefault()));
        nameView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        nameView.setTypeface(null, Typeface.BOLD);
        addView(nameView);

        Space space = createSpace();
        addView(space);
    }

    /**
     * Set the layout configuration file.
     */
    private void setLayoutConfiguration() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        setBackground(getResources().getDrawable(R.drawable.entry_background, null));

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING,
            getResources().getDisplayMetrics());

        setPadding(padding, padding, padding, padding);
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

    /**
     * Get the builder of the view.
     * @return The builder
     */
    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        private Context context;
        private String[] entryLabels;
        private String[] entries;
        private String name;

        /**
         * Builder of the context.
         * @param context The context
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Get the entry labels.
         * @return The entry labels
         */
        public String[] getEntryLabels() {
            return Arrays.copyOf(entryLabels, entryLabels.length);
        }

        /**
         * Setter of the entry labels.
         * @param entryLabels The entry labels
         */
        public void setEntryLabels(String[] entryLabels) {
            this.entryLabels = Arrays.copyOf(entryLabels, entryLabels.length);
        }

        /**
         * Get the entries.
         * @return The entries
         */
        public String[] getEntries() {
            return Arrays.copyOf(entries, entries.length);
        }

        /**
         * Setter of the entries.
         * @param entries The entries
         */
        public void setEntries(String[] entries) {
            this.entries = Arrays.copyOf(entries, entries.length);
        }

        /**
         * Get the name.
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * Setter of the name.
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * The builder of the EntryView.
         * @return The EntryView to build
         * @throws InvalidBuildParameters If the parameters to build are invalids
         */
        public EntryView build() throws InvalidBuildParameters {
            if (entryLabels == null || entries == null || entryLabels.length != entries.length) {
                throw new InvalidBuildParameters();
            }
            return new EntryView(context, null, this);
        }
    }
}
