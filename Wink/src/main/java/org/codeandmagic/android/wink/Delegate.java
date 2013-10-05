package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import static org.codeandmagic.android.wink.IWink.ARG_CANCELABLE;
import static org.codeandmagic.android.wink.IWink.ARG_CANCELABLE_ON_TOUCH_OUTSIDE;
import static org.codeandmagic.android.wink.IWink.ARG_LAYOUT_ID;
import static org.codeandmagic.android.wink.IWink.ARG_LEFT_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_LEFT_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_MESSAGE;
import static org.codeandmagic.android.wink.IWink.ARG_MESSAGE_ID;
import static org.codeandmagic.android.wink.IWink.ARG_MIDDLE_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_MIDDLE_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_RIGHT_BUTTON;
import static org.codeandmagic.android.wink.IWink.ARG_RIGHT_BUTTON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_THEME_ID;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE_ICON;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE_ICON_ID;
import static org.codeandmagic.android.wink.IWink.ARG_TITLE_ID;
import static org.codeandmagic.android.wink.IWink.ARG_USE_HOLO_THEME;
import static org.codeandmagic.android.wink.IWink.ARG_USE_LIGHT_THEME;
import static org.codeandmagic.android.wink.IWink.ARG_WINK_ID;

/**
 * Created by evelyne24.
 */
public class Delegate implements View.OnClickListener {

    protected int winkId;
    protected int themeId;
    protected int titleId;
    protected int titleIconId;
    protected int messageId;
    protected int leftButtonId;
    protected int middleButtonId;
    protected int rightButtonId;
    protected int layoutId;

    protected int titleIcon;
    protected String title;
    protected String message;
    protected Spannable titleSpannable;
    protected Spannable messageSpannable;

    protected String leftButton;
    protected String middleButton;
    protected String rightButton;

    protected boolean cancelable;
    protected boolean cancelableOnTouchOutside;
    protected boolean useDefaultLayout;
    protected boolean useHoloTheme;
    protected boolean useLightTheme;

    protected ListAdapter listAdapter;
    protected AdapterView.OnItemClickListener itemClickListener;
    protected int listChoiceMode;

    protected final IWink wink;

    public Delegate(IWink wink) {
        this.wink = wink;
    }

    public void onCreate(Bundle args) {
        initArguments(args);
    }
    
    protected void initArguments(Bundle args) {
        winkId = args.getInt(ARG_WINK_ID);
        themeId = args.getInt(ARG_THEME_ID);
        layoutId = args.getInt(ARG_LAYOUT_ID);
        titleIconId = args.getInt(ARG_TITLE_ICON_ID);
        titleId = args.getInt(ARG_TITLE_ID);
        messageId = args.getInt(ARG_MESSAGE_ID);
        leftButtonId = args.getInt(ARG_LEFT_BUTTON_ID);
        middleButtonId = args.getInt(ARG_MIDDLE_BUTTON_ID);
        rightButtonId = args.getInt(ARG_RIGHT_BUTTON_ID);

        titleIcon = args.getInt(ARG_TITLE_ICON);
        title = args.getString(ARG_TITLE);
        message = args.getString(ARG_MESSAGE);
        leftButton = args.getString(ARG_LEFT_BUTTON);
        middleButton = args.getString(ARG_MIDDLE_BUTTON);
        rightButton = args.getString(ARG_RIGHT_BUTTON);

        cancelable = args.getBoolean(ARG_CANCELABLE);
        cancelableOnTouchOutside = args.getBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE);
        useHoloTheme = args.getBoolean(ARG_USE_HOLO_THEME);
        useLightTheme = args.getBoolean(ARG_USE_LIGHT_THEME);

        useDefaultLayout = (layoutId == 0);
    }

    public int getWinkId() {
        return winkId;
    }

    public void setWinkId(int winkId) {
        this.winkId = winkId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getTitleIconId() {
        return titleIconId;
    }

    public void setTitleIconId(int titleIconId) {
        this.titleIconId = titleIconId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getLeftButtonId() {
        return leftButtonId;
    }

    public void setLeftButtonId(int leftButtonId) {
        this.leftButtonId = leftButtonId;
    }

    public int getMiddleButtonId() {
        return middleButtonId;
    }

    public void setMiddleButtonId(int middleButtonId) {
        this.middleButtonId = middleButtonId;
    }

    public int getRightButtonId() {
        return rightButtonId;
    }

    public void setRightButtonId(int rightButtonId) {
        this.rightButtonId = rightButtonId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(int titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeftButton() {
        return leftButton;
    }

    public void setLeftButton(String leftButton) {
        this.leftButton = leftButton;
    }

    public String getMiddleButton() {
        return middleButton;
    }

    public void setMiddleButton(String middleButton) {
        this.middleButton = middleButton;
    }

    public String getRightButton() {
        return rightButton;
    }

    public void setRightButton(String rightButton) {
        this.rightButton = rightButton;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isCancelableOnTouchOutside() {
        return cancelableOnTouchOutside;
    }

    public void setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
    }

    public boolean useDefaultLayout() {
        return useDefaultLayout;
    }

    public void setUseDefaultLayout(boolean useDefaultLayout) {
        this.useDefaultLayout = useDefaultLayout;
    }

    public boolean useHoloTheme() {
        return useHoloTheme;
    }

    public void setUseHoloTheme(boolean useHoloTheme) {
        this.useHoloTheme = useHoloTheme;
    }

    public boolean useLightTheme() {
        return useLightTheme;
    }

    public void setUseLightTheme(boolean useLightTheme) {
        this.useLightTheme = useLightTheme;
    }

    public Spannable getTitleSpannable() {
        return titleSpannable;
    }

    public void setTitleSpannable(Spannable titleSpannable) {
        this.titleSpannable = titleSpannable;
    }

    public Spannable getMessageSpannable() {
        return messageSpannable;
    }

    public void setMessageSpannable(Spannable messageSpannable) {
        this.messageSpannable = messageSpannable;
    }

    public void setListItems(ListAdapter adapter, AdapterView.OnItemClickListener listener, int choiceMode) {
       listAdapter = adapter;
       itemClickListener = listener;
       listChoiceMode = choiceMode;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (useDefaultLayout) {
            return new WinkPanel.Builder(wink.getActivity())
                    .setThemeId(themeId)
                    .setTitle(title)
                    .setTitleSpannable(titleSpannable)
                    .setTitleIcon(titleIcon)
                    .setMessage(message)
                    .setMessageSpannable(messageSpannable)
                    .setLeftButton(leftButton)
                    .setMiddleButton(middleButton)
                    .setRightButton(rightButton)
                    .setUseHoloTheme(useHoloTheme)
                    .setUseLightTheme(useLightTheme)
                    .setListItems(listAdapter, itemClickListener, listChoiceMode)
                    .setOnClickListener(this)
                    .build();
        } else {
            return setupUserLayout(inflater, container);
        }
    }

    @Override
    public void onClick(View v) {
        final WinkCallback callback = wink.getCallback();
        if (callback != null) {
            callback.onButtonClicked(v.getId(), wink);
        } else {
            wink.dismiss();
        }
    }
    private View setupUserLayout(LayoutInflater inflater, ViewGroup container) {
        final View view = inflater.inflate(layoutId, container, false);
        final ImageView titleIconView = (ImageView) view.findViewById(titleIconId);
        if (titleIconView != null && titleIcon > 0) {
            titleIconView.setImageResource(titleIcon);
        }
        setupTextView(view, titleId, title, false);
        setupTextView(view, messageId, message, false);
        setupTextView(view, leftButtonId, leftButton, true);
        setupTextView(view, middleButtonId, middleButton, true);
        setupTextView(view, rightButtonId, rightButton, true);
        return view;
    }

    private void setupTextView(View view, int viewId, String text, boolean clickable) {
        final TextView textView = (TextView) view.findViewById(viewId);
        if (textView != null) {
            if(!TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
            if (clickable) {
                textView.setOnClickListener(this);
            }
        }
    }
}
