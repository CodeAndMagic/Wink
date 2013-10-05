package org.codeandmagic.android.wink;

import android.app.Activity;
import android.text.Spannable;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by evelyne24.
 */
public interface IWink {

    String ARG_WINK_ID = "wink_id";
    String ARG_THEME_ID = "theme_id";
    String ARG_LAYOUT_ID = "layout_id";
    String ARG_TITLE_ICON_ID = "title_icon_id";
    String ARG_TITLE_ID = "title_id";
    String ARG_MESSAGE_ID = "message_id";
    String ARG_LEFT_BUTTON_ID = "left_button_id";
    String ARG_MIDDLE_BUTTON_ID = "middle_button__id";
    String ARG_RIGHT_BUTTON_ID = "right_button__id";

    String ARG_TITLE_ICON = "title_icon";
    String ARG_TITLE = "title";
    String ARG_MESSAGE = "message";
    String ARG_LEFT_BUTTON = "left_button";
    String ARG_MIDDLE_BUTTON = "middle_button";
    String ARG_RIGHT_BUTTON = "right_button";

    String ARG_CANCELABLE = "cancelable";
    String ARG_CANCELABLE_ON_TOUCH_OUTSIDE = "cancelable_on_touch_outside";
    String ARG_USE_HOLO_THEME = "use_light_theme";
    String ARG_USE_LIGHT_THEME = "overwrite_xml_theme";

    void dismiss();

    int getWinkId();

    Activity getActivity();

    WinkCallback getCallback();

    void setTitleSpannable(Spannable titleSpannable);

    void setMessageSpannable(Spannable messageSpannable);

    void setListItems(ListAdapter adapter, AdapterView.OnItemClickListener listener, int choiceMode);
}
