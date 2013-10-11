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

import static org.codeandmagic.android.wink.AbstractBuilder.*;

/**
 * Created by evelyne24.
 */
public class Presenter implements View.OnClickListener, AdapterView.OnItemClickListener {

    protected int accentColor;
    protected int winkId;
    protected int themeId;
    protected int titleId;
    protected int titleIconId;
    protected int messageId;
    protected int negativeButtonId;
    protected int neutralButtonId;
    protected int positiveButtonId;
    protected int layoutId;

    protected int titleIcon;
    protected String title;
    protected String message;
    protected Spannable titleSpan;
    protected Spannable messageSpan;

    protected String negativeButton;
    protected String neutralButton;
    protected String positiveButton;

    protected boolean cancelable;
    protected boolean cancelableOnTouchOutside;
    protected boolean useDefaultLayout;
    protected boolean useHoloTheme;
    protected boolean useLightTheme;

    protected ListAdapter listAdapter;
    protected int listChoiceMode;

    protected final IWink wink;

    public Presenter(IWink wink) {
        this.wink = wink;
    }

    public void onCreate(Bundle args) {
        initArguments(args);
    }
    
    protected void initArguments(Bundle args) {
        winkId = args.getInt(ARG_WINK_ID);
        themeId = args.getInt(ARG_THEME_ID);
        layoutId = args.getInt(ARG_LAYOUT_ID);
        useHoloTheme = args.getBoolean(ARG_USE_HOLO_THEME);
        useLightTheme = args.getBoolean(ARG_USE_LIGHT_THEME);
        accentColor = args.getInt(ARG_ACCENT_COLOR);
        useDefaultLayout = (layoutId == 0);

        titleIconId = args.getInt(ARG_TITLE_ICON_ID);
        titleIcon = args.getInt(ARG_TITLE_ICON);
        titleId = args.getInt(ARG_TITLE_ID);
        title = args.getString(ARG_TITLE);

        messageId = args.getInt(ARG_MESSAGE_ID);
        message = args.getString(ARG_MESSAGE);

        negativeButtonId = args.getInt(ARG_NEGATIVE_BUTTON_ID);
        neutralButtonId = args.getInt(ARG_NEUTRAL_BUTTON_ID);
        positiveButtonId = args.getInt(ARG_POSITIVE_BUTTON_ID);

        negativeButton = args.getString(ARG_NEGATIVE_BUTTON);
        neutralButton = args.getString(ARG_NEUTRAL_BUTTON);
        positiveButton = args.getString(ARG_POSITIVE_BUTTON);

        cancelable = args.getBoolean(ARG_CANCELABLE);
        cancelableOnTouchOutside = args.getBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE);
    }

    public int getWinkId() {
        return winkId;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public boolean isCancelableOnTouchOutside() {
        return cancelableOnTouchOutside;
    }

    public boolean useHoloTheme() {
        return useHoloTheme;
    }

    public void setTitleSpan(Spannable titleSpan) {
        this.titleSpan = titleSpan;
    }

    public void setMessageSpan(Spannable messageSpan) {
        this.messageSpan = messageSpan;
    }

    public void setListItems(ListAdapter adapter, int choiceMode) {
       listAdapter = adapter;
       listChoiceMode = choiceMode;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        if (useDefaultLayout) {
            return new WinkPanel.Builder(wink.getActivity())
                    .setAccentColor(accentColor)
                    .setThemeId(themeId)
                    .setUseHoloTheme(useHoloTheme)
                    .setUseLightTheme(useLightTheme)
                    .setTitle(title)
                    .setTitleSpannable(titleSpan)
                    .setTitleIcon(titleIcon)
                    .setMessage(message)
                    .setMessageSpannable(messageSpan)
                    .setNegativeButton(negativeButton)
                    .setNeutralButton(neutralButton)
                    .setPositiveButton(positiveButton)
                    .setListItems(listAdapter, listChoiceMode)
                    .setOnClickListener(this)
                    .setOnItemClickListener(this)
                    .build();
        } else {
            return setupUserLayout(inflater, container);
        }
    }

    @Override
    public void onClick(View v) {
        final WinkButtonCallback callback = wink.getButtonCallback();
        if (callback != null) {
            callback.onButtonClicked(v.getId(), wink);
        } else {
            wink.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final WinkListCallback callback = wink.getListCallback();
        if(callback != null) {
           callback.onItemClick(parent, view, position, id, wink);
        }
        else {
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
        setupTextView(view, negativeButtonId, negativeButton, true);
        setupTextView(view, neutralButtonId, neutralButton, true);
        setupTextView(view, positiveButtonId, positiveButton, true);
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
