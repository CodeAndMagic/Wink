package org.codeandmagic.android.wink;

import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static org.codeandmagic.android.wink.WinkUtils.makeSelector;

/**
 * Created by evelyne24.
 */
public class WinkButton extends RelativeLayout {

    public static class Builder {
        private int id;
        private int themeId;
        private int accentColor;
        private String text;
        private OnClickListener clickListener;
        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setThemeId(int themeId) {
            this.themeId = themeId;
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

    private final View dividerView;

    public WinkButton(Builder builder) {
        super(builder.context);
        setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1));

        View.inflate(new ContextThemeWrapper(builder.context, builder.themeId), R.layout.wink_button, this);
        dividerView = findViewById(R.id.wink_button_divider);

        final TextView buttonView = (TextView) findViewById(R.id.wink_button);
        buttonView.setText(builder.text);
        buttonView.setId(builder.id);
        buttonView.setOnClickListener(builder.clickListener);

        if (builder.accentColor != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttonView.setBackground(makeSelector(builder.accentColor));
            } else {
                buttonView.setBackgroundDrawable(makeSelector(builder.accentColor));
            }
        }
    }

    public void showDivider() {
        dividerView.setVisibility(View.VISIBLE);
    }
}
