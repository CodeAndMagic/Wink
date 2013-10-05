package org.codeandmagic.android.wink;

import android.content.Context;
import android.os.Bundle;

import static org.codeandmagic.android.wink.IWink.ARG_CANCELABLE;
import static org.codeandmagic.android.wink.IWink.ARG_CANCELABLE_ON_TOUCH_OUTSIDE;
import static org.codeandmagic.android.wink.IWink.ARG_LAYOUT_ID;
import static org.codeandmagic.android.wink.IWink.ARG_LEFT_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_LEFT_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_MESSAGE;
import static org.codeandmagic.android.wink.IWink.ARG_MIDDLE_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_MIDDLE_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_RIGHT_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_RIGHT_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_THEME_ID;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE_ICON;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE_ICON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_USE_HOLO_THEME;
import static org.codeandmagic.android.wink.IWink.ARG_USE_LIGHT_THEME;
import static org.codeandmagic.android.wink.IWink.ARG_WINK_ID;

/**
 * Using the Abstract AbstractBuilder Patter to have a common ground for the Android DialogFragment class hierarchy.
 * Created by evelyne24.
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {

    protected abstract T self();

    protected final Context context;

    protected int id;
    protected int themeId;
    protected int layoutId;

    protected int titleIconId;
    protected int leftButtonId;
    protected int middleButtonId;
    protected int rightButtonId;

    protected int titleIconRes;
    protected String title;
    protected String message;

    protected String leftButton;
    protected String middleButton;
    protected String rightButton;

    protected boolean cancelable = true;
    protected boolean cancelableOnTouchOutside = true;

    protected boolean useHoloTheme = true;
    protected boolean useLightTheme = false;

    public AbstractBuilder(Context context) {
        this.context = context;
    }

    public T setId(int id) {
        this.id = id;
        return self();
    }

    public T setThemeId(int themeId) {
        this.themeId = themeId;
        return self();
    }

    public T setLayoutId(int layoutRes) {
        this.layoutId = layoutRes;
        return self();
    }

    public T setTitleIconId(int titleIconId) {
        this.titleIconId = titleIconId;
        return self();
    }

    public T setTitleIcon(int titleIconRes) {
        this.titleIconRes = titleIconRes;
        return self();
    }

    public T setTitle(String title) {
        this.title = title;
        return self();
    }

    public T setTitle(int titleRes) {
        return setTitle(context.getString(titleRes));
    }

    public T setMessage(String message) {
        this.message = message;
        return self();
    }

    public T setMessage(int messageRes) {
        return setMessage(context.getString(messageRes));
    }

    public T setLeftButtonId(int leftButtonId) {
        this.leftButtonId = leftButtonId;
        return self();
    }

    public T setLeftButton(String leftButton) {
        this.leftButton = leftButton;
        return self();
    }

    public T setLeftButton(int leftButtonRes) {
        return setLeftButton(context.getString(leftButtonRes));
    }

    public T setMiddleButtonId(int middleButtonId) {
        this.middleButtonId = middleButtonId;
        return self();
    }

    public T setMiddleButton(String middleButton) {
        this.middleButton = middleButton;
        return self();
    }

    public T setMiddleButton(int middleButtonRes) {
        return setMiddleButton(context.getString(middleButtonRes));
    }

    public T setRightButtonId(int rightButtonId) {
        this.rightButtonId = rightButtonId;
        return self();
    }

    public T setRightButton(String rightButton) {
        this.rightButton = rightButton;
        return self();
    }

    public T setRightButton(int rightButtonRes) {
        return setRightButton(context.getString(rightButtonRes));
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

    protected Bundle bundle() {
        final Bundle args = new Bundle();
        args.putInt(ARG_WINK_ID, id);
        args.putInt(ARG_THEME_ID, themeId);
        args.putInt(ARG_LAYOUT_ID, layoutId);
        args.putInt(ARG_TITLE_ICON_ID, titleIconId);
        args.putInt(ARG_LEFT_BUTTON_ID, leftButtonId);
        args.putInt(ARG_MIDDLE_BUTTON_ID, middleButtonId);
        args.putInt(ARG_RIGHT_BUTTON_ID, rightButtonId);
        args.putInt(ARG_TITLE_ICON, titleIconRes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_LEFT_BUTTON, leftButton);
        args.putString(ARG_MIDDLE_BUTTON, middleButton);
        args.putString(ARG_RIGHT_BUTTON, rightButton);
        args.putBoolean(ARG_CANCELABLE, cancelable);
        args.putBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE, cancelableOnTouchOutside);
        args.putBoolean(ARG_USE_HOLO_THEME, useHoloTheme);
        args.putBoolean(ARG_USE_LIGHT_THEME, useLightTheme);
        return args;
    }

    public abstract IWink build();
}
