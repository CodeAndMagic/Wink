package org.codeandmagic.android.wink;

import android.util.Log;

import java.text.MessageFormat;
import static java.lang.String.format;

/**
 * Created by evelyne24.
 */
public class L {

    public static final String NL = System.getProperty("line.separator");

    private static final String TAG = "Wink";
    private static final boolean DEBUG = true;

    private static final char HCH = '-';
    private static final char VCH = '|';
    private static final int LINE_LENGTH = 120;
    private static final String LINE_FORMAT = " %-" + (LINE_LENGTH - 4) + "s ";
    private static final String SEP = " -> ";

    private static final String LOL_CAT =
    dotify( "          (\\_/)      ") +
    dotify( "       ( =(^Y^)=      ")  +
    paws  ( "--------\\(m---m)-------");


    private static String paws(String meow) {
        return format("%-" + LINE_LENGTH + "s\n", meow).replace(' ', '-');
    }

    private static String dotify(String cat) {
       return format("%-" + LINE_LENGTH + "s\n", cat).replace(' ', '.');
    }

    private static final String METHOD_LINE = "{0}():{1}";
    private static final String LOG = "[{0}#{1}] \n{2}\n";


    public static L getLogger(Class<?> clazz) {
        return new L(clazz);
    }

    private String className;
    private Class<?> clazz;

    private L(Class<?> clazz) {
        this.clazz = clazz;
        this.className = clazz.getSimpleName();
    }

    private String getCallingMethodLineNumber() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements != null) {
            for (int i = 0; i < stackTraceElements.length; i++) {
                StackTraceElement element = stackTraceElements[i];
                if (element != null && element.getClassName().equals(clazz.getName())) {
                    return MessageFormat.format(METHOD_LINE, element.getMethodName(), element.getLineNumber());
                }
            }
        }
        return "";
    }


    public void d(String msg, Object arg) {
        if (DEBUG) {
            Log.d(TAG, log(msg, arg, NL));
        }
    }

    public void d(String msg, Object...args) {
        if(DEBUG) {
            Log.d(TAG, log(msg, args));
        }
    }


    private StringBuilder header(StringBuilder b, String title) {
        hr(b);
        line(b, title.toUpperCase());
        return hr(b);
    }

    private StringBuilder catHead(StringBuilder b, String title) {
        b.append(LOL_CAT);
        line(b, title.toUpperCase());
        return hr(b);
    }

    private StringBuilder hr(StringBuilder b) {
        return hr(b, LINE_LENGTH, HCH);
    }

    private StringBuilder hr(StringBuilder b, int length, char ch) {
        for (int i = 0; i < length; i++) {
            b.append(ch);
        }
        return b.append(NL);
    }

    private StringBuilder line(StringBuilder b, String message) {
        return b.append(VCH).append(String.format(LINE_FORMAT, message)).append(VCH).append(NL);
    }

    private StringBuilder body(StringBuilder b, Object... args) {
        for (int i = 0; i < args.length; i += 2) {
            if(args[i + 1] == NL) {
                line(b, String.valueOf(args[i]));
            }
            else {
                line(b, args[i] + SEP + args[i + 1]);
            }
        }
        return b;
    }

    private String log(String title, Object... args) {
        final StringBuilder b = new StringBuilder();
        catHead(b, title);
        body(b, args);
        return MessageFormat.format(LOG, className, getCallingMethodLineNumber(), hr(b).toString());
    }
}
