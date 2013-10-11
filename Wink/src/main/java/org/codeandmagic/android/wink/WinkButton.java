package org.codeandmagic.android.wink;

import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static org.codeandmagic.android.wink.WinkUtils.makeSelector;

/**
 * Created by evelyne24.
 */
public class WinkButton extends LinearLayout {

    public static class Builder {
        private final Context context;
        private int id;
        private int buttonStyle;
        private int dividerStyle;
        private int accentColor;
        private String text;
        private OnClickListener clickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setButtonStyle(int buttonStyle) {
            this.buttonStyle = buttonStyle;
            return this;
        }

        public Builder setDividerStyle(int dividerStyle) {
            this.dividerStyle = dividerStyle;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setOnClickListener(OnClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setAccentColor(int accentColor) {
            this.accentColor = accentColor;
            return this;
        }

        public WinkButton build() {
            return new WinkButton(this);
        }
    }

    private final int dividerStyle;

    public WinkButton(Builder builder) {
        super(builder.context);
        this.dividerStyle = builder.dividerStyle;

        final TextView textView = new TextView(new ContextThemeWrapper(getContext(), builder.buttonStyle), null, builder.buttonStyle);
        textView.setId(builder.id);
        textView.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT, 1));
        textView.setText(builder.text);
        textView.setOnClickListener(builder.clickListener);

        if (builder.accentColor != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(makeSelector(builder.accentColor));
            } else {
                textView.setBackgroundDrawable(makeSelector(builder.accentColor));
            }
        }

        setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT, 1));
        addView(textView);
    }

    public void showDivider() {
        final View dividerView = View.inflate(new ContextThemeWrapper(getContext(), dividerStyle), R.layout.wink_button_divider, null);
        dividerView.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
        addView(dividerView, 0);
    }
}
