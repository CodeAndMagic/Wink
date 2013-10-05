package org.codeandmagic.android.wink;

/**
 * Created by evelyne24.
 */
public abstract class WinkUtils {

    public static String hex(long number) {
        return String.format("0x7f%06X", 0xFFFFFF & number);
    }
}
