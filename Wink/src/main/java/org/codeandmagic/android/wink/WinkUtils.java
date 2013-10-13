package org.codeandmagic.android.wink;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by evelyne24.
 */
public abstract class WinkUtils {

    private static final int OPACITY_PRESSED = (int) (0.6 * 255); // 60%
    private static final int OPACITY_FOCUSED = (int) (0.3 * 255); // 30%

    public static String hex(long number) {
        return String.format("0x7f%06X", 0xFFFFFF & number);
    }

    public static StateListDrawable makeSelector(int accentColor) {
        final int[] stateNormal = {android.R.attr.state_enabled};
        final int[] statePressed = {android.R.attr.state_pressed};
        final int[] stateFocused = {android.R.attr.state_focused};

        final ColorDrawable normal = new ColorDrawable(android.R.color.transparent);
        final ColorDrawable pressed = new ColorDrawable(argb(OPACITY_PRESSED, accentColor));
        final ColorDrawable focused = new ColorDrawable(argb(OPACITY_FOCUSED, accentColor));

        StateListDrawable background = new StateListDrawable();
        background.addState(statePressed, pressed);
        background.addState(stateFocused, focused);
        background.addState(stateNormal, normal);

        return background;
    }

    public static int argb(int alpha, int color) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
