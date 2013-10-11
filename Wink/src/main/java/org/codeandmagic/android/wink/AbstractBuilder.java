package org.codeandmagic.android.wink;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Using the Abstract AbstractBuilder Patter to have a common ground for the Android DialogFragment class hierarchy.
 * Created by evelyne24.
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {

    public static final String ARG_WINK_ID = "wink_id";
    public static final String ARG_THEME_ID = "theme_id";
    public static final String ARG_LAYOUT_ID = "layout_id";
    public static final String ARG_TITLE_ICON_ID = "title_icon_id";
    public static final String ARG_TITLE_ID = "title_id";
    public static final String ARG_MESSAGE_ID = "message_id";
    public static final String ARG_NEGATIVE_BUTTON_ID = "left_button_id";
    public static final String ARG_NEUTRAL_BUTTON_ID = "middle_button__id";
    public static final String ARG_POSITIVE_BUTTON_ID = "right_button__id";

    public static final String ARG_TITLE_ICON = "title_icon";
    public static final String ARG_TITLE = "title";
    public static final String ARG_MESSAGE = "message";
    public static final String ARG_POSITIVE_BUTTON = "positive_button";
    public static final String ARG_NEUTRAL_BUTTON = "neutral_button";
    public static final String ARG_NEGATIVE_BUTTON = "negative_button";

    public static final String ARG_CANCELABLE = "cancelable";
    public static final String ARG_CANCELABLE_ON_TOUCH_OUTSIDE = "cancelable_on_touch_outside";
    public static final String ARG_USE_HOLO_THEME = "use_light_theme";
    public static final String ARG_USE_LIGHT_THEME = "overwrite_xml_theme";
    public static final String ARG_ACCENT_COLOR = "accent_color";

    public static final String ARG_SERIALIZABLE = "serializable";
    public static final String ARG_SERIALIZABLE_ARRAY = "serializable_array";
    public static final String ARG_SERIALIZABLE_ARRAY_LIST = "serializable_array_list";

    public static final String ARG_PARCELABLE = "parcelable";
    public static final String ARG_PARCELABLE_ARRAY = "parcelable_array";
    public static final String ARG_PARCELABLE_ARRAY_LIST = "parcelable_array_list";

    protected abstract T self();

    protected final Context context;

    protected int accentColor;
    protected int winkId;
    protected int themeId;
    protected boolean useHoloTheme = true;
    protected boolean useLightTheme = false;

    protected int layoutId;
    protected int titleIconId;

    protected int positiveButtonId;
    protected int neutralButtonId;
    protected int negativeButtonId;

    protected int titleIcon;
    protected String title;
    protected String message;

    protected Spannable titleSpan;
    protected Spannable messageSpan;

    protected String positiveButton;
    protected String neutralButton;
    protected String negativeButton;

    protected boolean cancelable = true;
    protected boolean cancelableOnTouchOutside = true;

    protected ListAdapter listAdapter;
    protected int listChoiceMode;

    protected Serializable serializable;
    protected Serializable[] serializableArray;
    protected ArrayList<Serializable> serializableArrayList;

    protected Parcelable parcelable;
    protected Parcelable[] parcelableArray;
    protected ArrayList<Parcelable> parcelableArrayList;

    public AbstractBuilder(Context context) {
        this.context = context;
    }

    public T setWinkId(int winkId) {
        this.winkId = winkId;
        return self();
    }

    public T setThemeId(int themeId) {
        this.themeId = themeId;
        return self();
    }

    public T setLayoutId(int layout) {
        this.layoutId = layout;
        return self();
    }

    public T setTitleIconId(int iconId) {
        this.titleIconId = iconId;
        return self();
    }

    public T setTitleIcon(int titleIcon) {
        this.titleIcon = titleIcon;
        return self();
    }

    public T setTitle(String title) {
        this.title = title;
        return self();
    }

    public T setTitle(int title) {
        return setTitle(context.getString(title));
    }

    public T setTitle(Spannable title) {
        this.titleSpan = title;
        return self();
    }

    public T setMessage(String message) {
        this.message = message;
        return self();
    }

    public T setMessage(int message) {
        return setMessage(context.getString(message));
    }

    public T setMessage(Spannable message) {
        this.messageSpan = message;
        return self();
    }

    public T setPositiveButtonId(int positiveButton) {
        this.positiveButtonId = positiveButton;
        return self();
    }

    public T setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
        return self();
    }

    public T setPositiveButton(int positiveButton) {
        return setPositiveButton(context.getString(positiveButton));
    }

    public T setNeutralButtonId(int neutralButton) {
        this.neutralButtonId = neutralButton;
        return self();
    }

    public T setNeutralButton(String neutralButton) {
        this.neutralButton = neutralButton;
        return self();
    }

    public T setNeutralButton(int neutralButton) {
        return setNeutralButton(context.getString(neutralButton));
    }

    public T setNegativeButtonId(int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
        return self();
    }

    public T setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
        return self();
    }

    public T setNegativeButton(int negativeButton) {
        return setNegativeButton(context.getString(negativeButton));
    }

    public T setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return self();
    }

    public T setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        return self();
    }

    public T setUseHoloTheme(boolean useHoloTheme) {
        this.useHoloTheme = useHoloTheme;
        return self();
    }

    public T setUseLightTheme(boolean useLightTheme) {
        this.useLightTheme = useLightTheme;
        return self();
    }

    public T setAccentColor(int accentColor) {
        this.accentColor = accentColor;
        return self();
    }

    public T setListItems(ListAdapter adapter) {
        return setListItems(adapter, ListView.CHOICE_MODE_SINGLE);
    }

    public T setListItems(ListAdapter adapter, int choiceMode) {
        this.listAdapter = adapter;
        this.listChoiceMode = choiceMode;
        return self();
    }

    protected Bundle bundle() {
        final Bundle args = new Bundle();

        args.putInt(ARG_WINK_ID, winkId);
        args.putInt(ARG_THEME_ID, themeId);
        args.putInt(ARG_LAYOUT_ID, layoutId);
        args.putBoolean(ARG_USE_HOLO_THEME, useHoloTheme);
        args.putBoolean(ARG_USE_LIGHT_THEME, useLightTheme);
        args.putInt(ARG_ACCENT_COLOR, accentColor);

        args.putInt(ARG_TITLE_ICON_ID, titleIconId);
        args.putInt(ARG_TITLE_ICON, titleIcon);
        args.putString(ARG_TITLE, title);

        args.putInt(ARG_NEGATIVE_BUTTON_ID, negativeButtonId);
        args.putInt(ARG_NEUTRAL_BUTTON_ID, neutralButtonId);
        args.putInt(ARG_POSITIVE_BUTTON_ID, positiveButtonId);

        args.putString(ARG_MESSAGE, message);

        args.putString(ARG_NEGATIVE_BUTTON, negativeButton);
        args.putString(ARG_NEUTRAL_BUTTON, neutralButton);
        args.putString(ARG_POSITIVE_BUTTON, positiveButton);

        args.putBoolean(ARG_CANCELABLE, cancelable);
        args.putBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE, cancelableOnTouchOutside);

        args.putSerializable(ARG_SERIALIZABLE, serializable);
        args.putSerializable(ARG_SERIALIZABLE_ARRAY, serializableArray);
        args.putSerializable(ARG_SERIALIZABLE_ARRAY_LIST, serializableArrayList);

        args.putParcelable(ARG_PARCELABLE, parcelable);
        args.putParcelableArray(ARG_PARCELABLE_ARRAY, parcelableArray);
        args.putParcelableArrayList(ARG_PARCELABLE_ARRAY_LIST, parcelableArrayList);

        return args;
    }

    public abstract IWink build();
}
