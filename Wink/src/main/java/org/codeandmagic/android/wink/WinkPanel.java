package org.codeandmagic.android.wink;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
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

import static org.codeandmagic.android.wink.WinkUtils.hex;

/**
 * Created by evelyne24.
 */
public class WinkPanel extends LinearLayout {

    public static class Builder {
        private final Context context;
        private OnClickListener clickListener;

        private int themeId;

        private int titleIcon;
        private String title;
        private String message;
        private Spannable titleSpannable;
        private Spannable messageSpannable;

        private String leftButton;
        private String middleButton;
        private String rightButton;

        private boolean useHoloTheme;
        private boolean useLightTheme;

        private ListAdapter listAdapter;
        private AdapterView.OnItemClickListener itemClickListener;
        private int listChoiceMode;

        public Builder(Context context) {
            this.context = context;
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

        public Builder setLeftButton(String leftButton) {
            this.leftButton = leftButton;
            return this;
        }

        public Builder setMiddleButton(String middleButton) {
            this.middleButton = middleButton;
            return this;
        }

        public Builder setRightButton(String rightButton) {
            this.rightButton = rightButton;
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

        public Builder setListItems(ListAdapter adapter, AdapterView.OnItemClickListener listener, int choiceMode) {
            this.listAdapter = adapter;
            this.itemClickListener = listener;
            this.listChoiceMode = choiceMode;
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

    private int themeId;
    private int titleIcon;
    private String title;
    private String message;
    private Spannable titleSpannable;
    private Spannable messageSpannable;

    private String leftButton;
    private String middleButton;
    private String rightButton;

    private boolean useHoloTheme;
    private boolean useLightTheme;

    private ListAdapter listAdapter;
    private AdapterView.OnItemClickListener itemClickListener;
    private int listChoiceMode;

    private Context context;
    private Resources resources;
    private OnClickListener clickListener;

    public WinkPanel(Builder builder) {
        super(builder.context);
        context = getContext();
        resources = getResources();
        clickListener = builder.clickListener;

        themeId = builder.themeId;
        title = builder.title;
        titleIcon = builder.titleIcon;
        message = builder.message;
        titleSpannable = builder.titleSpannable;
        messageSpannable = builder.messageSpannable;

        leftButton = builder.leftButton;
        middleButton = builder.middleButton;
        rightButton = builder.rightButton;

        useHoloTheme = builder.useHoloTheme;
        useLightTheme = builder.useLightTheme;

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

        //log.d("Wink Title Panel Style", "hex", hex(titlePanelStyle), "name", getResourceName(titlePanelStyle));
        log.d("Wink Title Style", "hex", hex(titleStyle), "name", getResourceName(titleStyle));
        //log.d("Wink Title Icon Style", "hex", hex(titleIconStyle), "name", getResourceName(titleIconStyle));
        //log.d("Wink Title Divider Style", "hex", hex(titleDividerStyle), "name", getResourceName(titleDividerStyle));
        //log.d("Wink Message Panel Style", "hex", hex(messagePanelStyle), "name", getResourceName(messagePanelStyle));
        log.d("Wink Message Style", "hex", hex(messageStyle), "name", getResourceName(messageStyle));
        //log.d("Wink Button Panel Style", "hex", hex(buttonPanelStyle), "name", getResourceName(buttonPanelStyle));
        //log.d("Wink Button Divider Style", "hex", hex(buttonDividerStyle), "name", getResourceName(buttonDividerStyle));
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
        final View titleDividerView = inflateView(context, titlePanelParentView, R.layout.wink_title_divider, titleDividerStyle);

        if (titleIcon > 0) {
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
        final boolean hasLeftButton = !TextUtils.isEmpty(leftButton);
        final boolean hasMiddleButton = !TextUtils.isEmpty(middleButton);
        final boolean hasRightButton = !TextUtils.isEmpty(rightButton);

        if(hasLeftButton || hasMiddleButton || hasRightButton) {
            final ViewGroup buttonPanelParentView = inflateViewGroup(context, R.layout.wink_button_panel_parent);
            final ViewGroup buttonPanelView = inflateViewGroup(context, buttonPanelParentView, R.layout.wink_button_panel, buttonPanelStyle);
            final View buttonTopDividerView = inflateView(context, buttonPanelParentView, R.layout.wink_button_divider, buttonDividerStyle);

            final WinkButton leftButtonView = new WinkButton.Builder(getContext())
                    .setId(R.id.left_button)
                    .setText(leftButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setOnClickListener(clickListener).build();

            final WinkButton middleButtonView = new WinkButton.Builder(getContext())
                    .setId(R.id.middle_button)
                    .setText(middleButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setOnClickListener(clickListener).build();

            final WinkButton rightButtonView = new WinkButton.Builder(getContext())
                    .setId(R.id.right_button)
                    .setText(rightButton)
                    .setButtonStyle(buttonStyle)
                    .setDividerStyle(buttonDividerStyle)
                    .setOnClickListener(clickListener).build();

            if(hasLeftButton) {
                buttonPanelView.addView(leftButtonView);
            }
            if(hasMiddleButton) {
                if(hasLeftButton) {
                    middleButtonView.showDivider();
                }
                buttonPanelView.addView(middleButtonView);
            }
            if(hasRightButton) {
                if(hasLeftButton || hasMiddleButton) {
                   rightButtonView.showDivider();
                }
                buttonPanelView.addView(rightButtonView);
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
