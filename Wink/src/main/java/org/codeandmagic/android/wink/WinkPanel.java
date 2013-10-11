package org.codeandmagic.android.wink;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import static org.codeandmagic.android.wink.WinkUtils.*;

/**
 * Created by evelyne24.
 */
public class WinkPanel extends LinearLayout {

    public static class Builder {
        private final Context context;

        private int themeId;
        private int accentColor;
        private boolean useHoloTheme;
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

        public Builder setThemeId(int themeId) {
            this.themeId = themeId;
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

        public Builder setUseHoloTheme(boolean useHoloTheme) {
            this.useHoloTheme = useHoloTheme;
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

        public WinkPanel build() {
            return new WinkPanel(this);
        }
    }

    private static final L log = L.getLogger(WinkPanel.class);

    private int defaultTheme;
    private int defaultBackgroundId;
    private int defaultTitlePanelStyle;
    private int defaultTitleDividerStyle;
    private int defaultTitleIconStyle;
    private int defaultTitleStyle;
    private int defaultMessagePanelStyle;
    private int defaultMessageStyle;
    private int defaultButtonPanelStyle;
    private int defaultButtonDividerStyle;
    private int defaultButtonStyle;
    private int defaultListStyle;

    private int titlePanelStyle;
    private int titleDividerStyle;
    private int titleIconStyle;
    private int titleStyle;
    private int messagePanelStyle;
    private int messageStyle;
    private int buttonPanelStyle;
    private int buttonDividerStyle;
    private int buttonStyle;
    private int listStyle;

    private int accentColor;
    private int themeId;
    private boolean useHoloTheme;
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
    private int listChoiceMode;
    private AdapterView.OnItemClickListener itemClickListener;

    private Context context;
    private Resources resources;

    public WinkPanel(Builder builder) {
        super(builder.context);
        context = getContext();
        resources = getResources();

        accentColor = builder.accentColor;
        themeId = builder.themeId;
        useHoloTheme = builder.useHoloTheme;
        useLightTheme = builder.useLightTheme;

        title = builder.title;
        titleIcon = builder.titleIcon;
        titleSpannable = builder.titleSpannable;

        message = builder.message;
        messageSpannable = builder.messageSpannable;

        negativeButton = builder.negativeButton;
        neutralButton = builder.neutralButton;
        positiveButton = builder.positiveButton;
        clickListener = builder.clickListener;

        listAdapter = builder.listAdapter;
        itemClickListener = builder.itemClickListener;
        listChoiceMode = builder.listChoiceMode;

        init(context);
    }

    protected void init(Context context) {
        final Theme appTheme = context.getTheme();
        if (appTheme == null) {
            throw new IllegalArgumentException("To use Wink you must declare a Theme for your Application!");
        }
        //log.d("Application Theme", getAppThemeName(context));

        setOrientation(VERTICAL);
        initDefaultWinkStyles();
        initWinkStyles(appTheme);
        createViewHierarchy(context);
    }

    private Theme resolveWinkTheme(Theme appTheme, int defThemeId) {
        if (themeId == 0) {
            if (useHoloTheme) {
                themeId = useLightTheme ? R.style.WinkTheme_Light : R.style.WinkTheme_Dark;
            } else {
                themeId = resolveWinkStyle(appTheme, R.attr.winkTheme, defThemeId);
                if (themeId == 0) {
                    themeId = R.style.WinkTheme_Dark;
                }
                useLightTheme = getResourceName(themeId).toLowerCase().contains("light");
            }
        }
        log.d("Wink Theme", "hex", hex(themeId), "name", getResourceName(themeId));

        final Theme winkTheme = resources.newTheme();
        winkTheme.applyStyle(themeId, true);
        return winkTheme;
    }

    private int resolveWinkStyle(Theme theme, int styleAttr, int defStyleId) {
        final TypedValue styleRef = new TypedValue();
        theme.resolveAttribute(styleAttr, styleRef, true);

        if (TypedValue.TYPE_REFERENCE == styleRef.type) {
            return styleRef.resourceId;
        } else if (TypedValue.TYPE_STRING == styleRef.type) {
            final String name = getDrawableName(styleRef.string);
            return resources.getIdentifier(name, "drawable", context.getPackageName());
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

//    private String getAppThemeName(Context context) {
//        return getResourceName(context.getApplicationContext().getApplicationInfo().theme);
//    }

    private void initDefaultWinkStyles() {
        defaultTitlePanelStyle = R.style.WinkTitlePanelStyle;
        defaultMessagePanelStyle = R.style.WinkMessagePanelStyle;
        defaultButtonPanelStyle = R.style.WinkButtonPanelStyle;

        if(useHoloTheme) {
            if(useLightTheme) {
                defaultTheme = R.style.WinkTheme_Light;
                defaultBackgroundId = R.drawable.wink_dialog_background_light;
                defaultTitleDividerStyle = R.style.WinkTitleDividerStyle_Light;
                defaultTitleIconStyle = R.style.WinkTitleIconStyle_Light;
                defaultTitleStyle = R.style.WinkTitleStyle_Light;
                defaultMessageStyle = R.style.WinkMessageStyle_Light;
                defaultButtonDividerStyle = R.style.WinkButtonDividerStyle_Light;
                defaultButtonStyle = R.style.WinkButtonStyle_Light;
                defaultListStyle = R.style.WinkListStyle_Light;
            }
            else {
                defaultTheme = R.style.WinkTheme_Dark;
                defaultBackgroundId = R.drawable.wink_dialog_background_dark;
                defaultTitleDividerStyle = R.style.WinkTitleDividerStyle_Dark;
                defaultTitleIconStyle = R.style.WinkTitleIconStyle_Dark;
                defaultTitleStyle = R.style.WinkTitleStyle_Dark;
                defaultMessageStyle = R.style.WinkMessageStyle_Dark;
                defaultButtonDividerStyle = R.style.WinkButtonDividerStyle_Dark;
                defaultButtonStyle = R.style.WinkButtonStyle_Dark;
                defaultListStyle = R.style.WinkListStyle_Dark;
            }
        }
        else {
            defaultTheme = R.style.WinkTheme;
            defaultTitleDividerStyle = R.style.WinkTitleDividerStyle;
            defaultTitleIconStyle = R.style.WinkTitleIconStyle;
            defaultTitleStyle = R.style.WinkTitleStyle;
            defaultMessageStyle = R.style.WinkMessageStyle;
            defaultButtonDividerStyle = R.style.WinkButtonDividerStyle;
            defaultButtonStyle = R.style.WinkButtonStyle;
            defaultListStyle = R.style.WinkListStyle;
        }
    }

    private void initWinkStyles(Theme appTheme) {
        final Theme winkTheme = resolveWinkTheme(appTheme, defaultTheme);
        final int backgroundId = resolveWinkStyle(winkTheme, R.attr.winkBackground, defaultBackgroundId);
        if (backgroundId > 0) {
            setBackgroundResource(backgroundId);
            //log.d("Wink Background", "hex", hex(backgroundId), "name", getResourceName(backgroundId));
        }

        titlePanelStyle = resolveWinkStyle(winkTheme, R.attr.winkTitlePanelStyle, defaultTitlePanelStyle);
        titleStyle = resolveWinkStyle(winkTheme, R.attr.winkTitleStyle, defaultTitleStyle);
        titleIconStyle = resolveWinkStyle(winkTheme, R.attr.winkTitleIconStyle, defaultTitleIconStyle);
        titleDividerStyle = resolveWinkStyle(winkTheme, R.attr.winkTitleDividerStyle, defaultTitleDividerStyle);
        messagePanelStyle = resolveWinkStyle(winkTheme, R.attr.winkMessagePanelStyle, defaultMessagePanelStyle);
        messageStyle = resolveWinkStyle(winkTheme, R.attr.winkMessageStyle, defaultMessageStyle);
        buttonPanelStyle = resolveWinkStyle(winkTheme, R.attr.winkButtonPanelStyle, defaultButtonPanelStyle);
        buttonDividerStyle = resolveWinkStyle(winkTheme, R.attr.winkButtonDividerStyle, defaultButtonDividerStyle);
        buttonStyle = resolveWinkStyle(winkTheme, R.attr.winkButtonStyle, defaultButtonStyle);
        listStyle = resolveWinkStyle(winkTheme, R.attr.winkListStyle, defaultListStyle);

        log.d("Wink Title Panel Style", "hex", hex(titlePanelStyle), "name", getResourceName(titlePanelStyle));
        log.d("Wink Title Style", "hex", hex(titleStyle), "name", getResourceName(titleStyle));
        log.d("Wink Title Icon Style", "hex", hex(titleIconStyle), "name", getResourceName(titleIconStyle));
        log.d("Wink Title Divider Style", "hex", hex(titleDividerStyle), "name", getResourceName(titleDividerStyle));
        log.d("Wink Message Panel Style", "hex", hex(messagePanelStyle), "name", getResourceName(messagePanelStyle));
        log.d("Wink Message Style", "hex", hex(messageStyle), "name", getResourceName(messageStyle));
        log.d("Wink Button Panel Style", "hex", hex(buttonPanelStyle), "name", getResourceName(buttonPanelStyle));
        log.d("Wink Button Divider Style", "hex", hex(buttonDividerStyle), "name", getResourceName(buttonDividerStyle));
        log.d("Wink Button Style", "hex", hex(buttonStyle), "name", getResourceName(buttonStyle));
    }

    private void createViewHierarchy(Context context) {
        if (!TextUtils.isEmpty(title) || titleSpannable != null) {
            createTitleViewHierarchy(context);
        }
        if (!TextUtils.isEmpty(message) || messageSpannable != null) {
            createMessageViewHierarchy(context);
        }
        if(listAdapter != null) {
            createListViewHierarchy(context);
        }
        createButtonViewHierarchy(context);
    }

    private void createTitleViewHierarchy(Context context) {
        final ViewGroup titlePanelParentView = inflateViewGroup(context, R.layout.wink_title_panel_parent);
        final ViewGroup titlePanelView = inflateViewGroup(context, titlePanelParentView, R.layout.wink_title_panel, titlePanelStyle);
        final Spannable titleSpan = (titleSpannable != null) ? titleSpannable : new SpannableString(title);
        final TextView titleView = inflateTextView(context, titlePanelView, R.layout.wink_title, titleStyle, titleSpan);
        final ImageView titleDividerView = (ImageView) inflateView(context, titlePanelParentView, R.layout.wink_title_divider, titleDividerStyle);

        if(accentColor != 0) {
          titleView.setTextColor(accentColor);
          titleDividerView.setImageDrawable(new ColorDrawable(accentColor));
        }

        if (titleIcon != 0) {
            final ImageView titleIconView = inflateImageView(context, titlePanelView, R.layout.wink_title_icon, titleIconStyle, titleIcon);
            titlePanelView.addView(titleIconView);
        }

        titlePanelView.addView(titleView);
        titlePanelParentView.addView(titlePanelView);
        titlePanelParentView.addView(titleDividerView);
        addView(titlePanelParentView);
    }

    private void createMessageViewHierarchy(Context context) {
        final ViewGroup messagePanelView = inflateViewGroup(context, this, R.layout.wink_message_panel, messagePanelStyle);
        final Spannable messageSpan = (messageSpannable != null) ? messageSpannable : new SpannableString(message);
        final TextView messageView = inflateTextView(context, messagePanelView, R.layout.wink_message, messageStyle, messageSpan);

        messagePanelView.addView(messageView);
        addView(messagePanelView);
    }

    private void createButtonViewHierarchy(Context context) {
        final boolean hasNegativeBtn = !TextUtils.isEmpty(negativeButton);
        final boolean hasNeutralBtn = !TextUtils.isEmpty(neutralButton);
        final boolean hasPositiveBtn = !TextUtils.isEmpty(positiveButton);
        final boolean reverseButtons = hasNegativeBtn && hasPositiveBtn &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;

        if(hasNegativeBtn || hasNeutralBtn || hasPositiveBtn) {
            final ViewGroup buttonPanelParentView = inflateViewGroup(context, R.layout.wink_button_panel_parent);
            final ViewGroup buttonPanelView = inflateViewGroup(context, buttonPanelParentView, R.layout.wink_button_panel, buttonPanelStyle);
            final View buttonTopDividerView = inflateView(context, buttonPanelParentView, R.layout.wink_button_divider, buttonDividerStyle);

            final WinkButton negativeBtn = new WinkButton.Builder(getContext())
                    .setId(reverseButtons ? R.id.positive_button : R.id.negative_button)
                    .setText(reverseButtons ? positiveButton : negativeButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setAccentColor(accentColor)
                    .setOnClickListener(clickListener).build();

            final WinkButton neutralBtn = new WinkButton.Builder(getContext())
                    .setId(R.id.neutral_button)
                    .setText(neutralButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setAccentColor(accentColor)
                    .setOnClickListener(clickListener).build();

            final WinkButton positiveBtn = new WinkButton.Builder(getContext())
                    .setId(reverseButtons ? R.id.negative_button : R.id.positive_button)
                    .setText(reverseButtons ? negativeButton: positiveButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setAccentColor(accentColor)
                    .setOnClickListener(clickListener).build();

            if(hasNegativeBtn) {
                buttonPanelView.addView(negativeBtn);
            }
            if(hasNeutralBtn) {
                if(hasNegativeBtn) {
                    neutralBtn.showDivider();
                }
                buttonPanelView.addView(neutralBtn);
            }
            if(hasPositiveBtn) {
                if(hasNegativeBtn || hasNeutralBtn) {
                   positiveBtn.showDivider();
                }
                buttonPanelView.addView(positiveBtn);
            }

            buttonPanelParentView.addView(buttonTopDividerView);
            buttonPanelParentView.addView(buttonPanelView);
            addView(buttonPanelParentView);
        }
    }

    private void createListViewHierarchy(Context context) {
        final ViewGroup customPanelView = inflateViewGroup(context, R.layout.wink_custom_content_panel);
        final ListView listView = (ListView) inflateView(context, customPanelView, R.layout.wink_list, listStyle);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(listChoiceMode);
        listView.setOnItemClickListener(itemClickListener);

        if(accentColor != 0) {
            listView.setSelector(makeSelector(accentColor));
        }

        customPanelView.addView(listView);
        addView(customPanelView);
    }

    private View inflateView(Context context, ViewGroup parent, int layoutId, int styleId) {
        final LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(context, styleId));
        return inflater.inflate(layoutId, parent, false);
    }

    private ViewGroup inflateViewGroup(Context context, int layoutId) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        return (ViewGroup) inflater.inflate(layoutId, this, false);
    }

    private ViewGroup inflateViewGroup(Context context, ViewGroup parent, int layoutId, int styleId) {
        return (ViewGroup) inflateView(context, parent, layoutId, styleId);
    }

    private TextView inflateTextView(Context context, ViewGroup parent, int layoutId, int styleId, Spannable text) {
        final TextView titleView = (TextView) inflateView(context, parent, layoutId, styleId);
        titleView.setText(text);
        return titleView;
    }

    private ImageView inflateImageView(Context context, ViewGroup parent, int layoutId, int styleId, int iconRes) {
        final ImageView imageView = (ImageView) inflateView(context, parent, layoutId, styleId);
        if (iconRes > 0) {
            imageView.setImageResource(iconRes);
        }
        return imageView;
    }


}
