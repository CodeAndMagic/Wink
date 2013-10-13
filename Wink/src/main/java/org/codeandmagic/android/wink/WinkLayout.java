package org.codeandmagic.android.wink;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import static org.codeandmagic.android.wink.WinkUtils.hex;
import static org.codeandmagic.android.wink.WinkUtils.makeSelector;

/**
 * Created by evelyne24 on 12/10/2013.
 */
public class WinkLayout extends LinearLayout {

    public static class Builder {
        private final Context context;

        private int accentColor;
        private boolean useLightTheme;

        private int titleIcon;
        private String title;
        private Spannable titleSpannable;

        private String message;
        private Spannable messageSpannable;

        private String negativeButton;
        private String neutralButton;
        private String positiveButton;
        private OnClickListener clickListener;

        private ListAdapter listAdapter;
        private AdapterView.OnItemClickListener itemClickListener;
        private int listChoiceMode;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAccentColor(int accentColor) {
            this.accentColor = accentColor;
            return this;
        }

        public Builder setTitleIcon(int titleIcon) {
            this.titleIcon = titleIcon;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNegativeButton(String negativeButton) {
            this.negativeButton = negativeButton;
            return this;
        }

        public Builder setNeutralButton(String neutralButton) {
            this.neutralButton = neutralButton;
            return this;
        }

        public Builder setPositiveButton(String positiveButton) {
            this.positiveButton = positiveButton;
            return this;
        }

        public Builder setUseLightTheme(boolean useLightTheme) {
            this.useLightTheme = useLightTheme;
            return this;
        }

        public Builder setOnClickListener(OnClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setTitleSpannable(Spannable titleSpannable) {
            this.titleSpannable = titleSpannable;
            return this;
        }

        public Builder setMessageSpannable(Spannable messageSpannable) {
            this.messageSpannable = messageSpannable;
            return this;
        }

        public Builder setListItems(ListAdapter adapter, int choiceMode) {
            this.listAdapter = adapter;
            this.listChoiceMode = choiceMode;
            return this;
        }

        public Builder setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public WinkLayout build() {
            return new WinkLayout(this);
        }
    }

    private static final L log = L.getLogger(WinkLayout.class);

    private int winkThemeId;
    private int maxWidth;
    private int maxHeight;
    private TextView messageView;
    private Resources resources;

    public WinkLayout(Builder builder) {
        super(builder.context);
        resources = getResources();
        init(builder);
    }

    public TextView getMessageView() {
        return messageView;
    }

    public void setListItems(ListAdapter listAdapter, int listChoiceMode, int accentColor, OnItemClickListener itemClickListener) {
        final ViewGroup customPanel = (ViewGroup) findViewById(R.id.wink_custom_panel);
        if (listAdapter != null) {
            ListView listView = (ListView) customPanel.findViewById(R.id.wink_list_view);
            listView.setAdapter(listAdapter);
            listView.setChoiceMode(listChoiceMode);
            listView.setOnItemClickListener(itemClickListener);
            if (accentColor != 0) {
                listView.setSelector(makeSelector(accentColor));
            }
            customPanel.setVisibility(View.VISIBLE);
        }
    }

    private void init(Builder builder) {
        setOrientation(VERTICAL);

        winkThemeId = builder.useLightTheme ? R.style.WinkTheme_Light : R.style.WinkTheme_Dark;
        final int defaultBackground = builder.useLightTheme ?
                R.drawable.wink_dialog_background_light : R.drawable.wink_dialog_background_dark;
        Theme winkTheme = resolveWinkTheme(winkThemeId);
        setBackgroundResource(resolveWinkStyle(winkTheme, R.attr.winkBackground, defaultBackground));

        final int defaultWidth = getResources().getDimensionPixelSize(R.dimen.wink_dialog_max_width);
        final int defaultHeight = getResources().getDimensionPixelSize(R.dimen.wink_dialog_max_height);
        maxWidth = resolveWinkStyle(winkTheme, R.attr.winkMaxWidth, defaultWidth);
        maxHeight = resolveWinkStyle(winkTheme, R.attr.winkMaxHeight, defaultHeight);

        LayoutInflater.from(new ContextThemeWrapper(builder.context, winkThemeId)).inflate(R.layout.wink_layout, this, true);
        bindViews(builder);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (maxWidth > 0 && maxWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, measureMode);
        }
        if (maxHeight > 0 && maxHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void bindViews(Builder builder) {
        bindTitleView(builder);
        bindMessageView(builder);
        bindButtonsView(builder);
    }

    private void bindTitleView(Builder builder) {
        final View titlePanel = findViewById(R.id.wink_title_panel);
        if (!TextUtils.isEmpty(builder.title) || builder.titleSpannable != null) {
            final TextView titleView = (TextView) titlePanel.findViewById(R.id.wink_title);
            final View dividerView = titlePanel.findViewById(R.id.wink_title_divider);

            if (!TextUtils.isEmpty(builder.title)) {
                titleView.setText(builder.title);
            } else if (builder.titleSpannable != null) {
                titleView.setText(builder.titleSpannable);
            }

            if (builder.accentColor != 0) {
                titleView.setTextColor(builder.accentColor);
                dividerView.setBackgroundColor(builder.accentColor);
            }

            if (builder.titleIcon != 0) {
                titleView.setCompoundDrawablesWithIntrinsicBounds(builder.titleIcon, 0, 0, 0);
            }
        } else {
            titlePanel.setVisibility(View.GONE);
        }
    }

    private void bindMessageView(Builder builder) {
        final View messagePanel = findViewById(R.id.wink_message_panel);
        if (!TextUtils.isEmpty(builder.message) || builder.messageSpannable != null) {
            messageView = (TextView) messagePanel.findViewById(R.id.wink_message);

            if (!TextUtils.isEmpty(builder.message)) {
                messageView.setText(builder.message);
            } else if (builder.messageSpannable != null) {
                messageView.setText(builder.messageSpannable);
            }
        } else {
            messagePanel.setVisibility(View.GONE);
        }
    }

    private void bindButtonsView(Builder builder) {
        final View buttonsPanelParent = findViewById(R.id.wink_button_panel_parent);
        final boolean hasNegativeBtn = !TextUtils.isEmpty(builder.negativeButton);
        final boolean hasNeutralBtn = !TextUtils.isEmpty(builder.neutralButton);
        final boolean hasPositiveBtn = !TextUtils.isEmpty(builder.positiveButton);
        final boolean reverseButtons = hasNegativeBtn && hasPositiveBtn &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;

        if (hasNegativeBtn || hasNeutralBtn || hasPositiveBtn) {
            final ViewGroup buttonsPanel = (ViewGroup) findViewById(R.id.wink_button_panel);

            final WinkButton negativeBtn = new WinkButton.Builder(getContext())
                    .setId(reverseButtons ? R.id.positive_button : R.id.negative_button)
                    .setText(reverseButtons ? builder.positiveButton : builder.negativeButton)
                    .setAccentColor(builder.accentColor)
                    .setThemeId(winkThemeId)
                    .setOnClickListener(builder.clickListener).build();

            final WinkButton neutralBtn = new WinkButton.Builder(getContext())
                    .setId(R.id.neutral_button)
                    .setText(builder.neutralButton)
                    .setAccentColor(builder.accentColor)
                    .setThemeId(winkThemeId)
                    .setOnClickListener(builder.clickListener).build();

            final WinkButton positiveBtn = new WinkButton.Builder(getContext())
                    .setId(reverseButtons ? R.id.negative_button : R.id.positive_button)
                    .setText(reverseButtons ? builder.negativeButton : builder.positiveButton)
                    .setAccentColor(builder.accentColor)
                    .setThemeId(winkThemeId)
                    .setOnClickListener(builder.clickListener).build();

            if (hasNegativeBtn) {
                buttonsPanel.addView(negativeBtn);
            }
            if (hasNeutralBtn) {
                if (hasNegativeBtn) {
                    neutralBtn.showDivider();
                }
                buttonsPanel.addView(neutralBtn);
            }
            if (hasPositiveBtn) {
                if (hasNegativeBtn || hasNeutralBtn) {
                    positiveBtn.showDivider();
                }
                buttonsPanel.addView(positiveBtn);
            }
        } else {
            buttonsPanelParent.setVisibility(View.GONE);
        }
    }

    private Theme resolveWinkTheme(int winkThemeId) {
        log.d("Wink Theme", "hex", hex(winkThemeId), "name", getResourceName(winkThemeId));
        final Theme winkTheme = resources.newTheme();
        winkTheme.applyStyle(winkThemeId, true);
        return winkTheme;
    }

    private int resolveWinkStyle(Theme theme, int styleAttr, int defStyleId) {
        final TypedValue styleRef = new TypedValue();
        theme.resolveAttribute(styleAttr, styleRef, true);

        if (TypedValue.TYPE_REFERENCE == styleRef.type) {
            return styleRef.resourceId;
        } else if (TypedValue.TYPE_STRING == styleRef.type) {
            final String name = getDrawableName(styleRef.string);
            return resources.getIdentifier(name, "drawable", getContext().getPackageName());
        } else if (TypedValue.TYPE_DIMENSION == styleRef.type) {
            return (int) styleRef.getDimension(getResources().getDisplayMetrics());
        } else {
            return defStyleId;
        }
    }

    /**
     * Strips 'res/drawable-xxxx' from the resource name.
     *
     * @param resourceName the resource name including path
     * @return the actual name of the drawable without the path
     */
    private String getDrawableName(CharSequence resourceName) {
        String name = resourceName.toString();
        final int lastSeparator = name.lastIndexOf(File.separator);
        if (lastSeparator > 0) {
            name = name.substring(lastSeparator + 1);
        }
        final int extension = name.indexOf(".");
        if (extension > 0) {
            name = name.substring(0, extension);
        }
        return name;
    }

    private String getResourceName(int resId) {
        return resId > 0 ? resources.getResourceName(resId) : "??";
    }

}
