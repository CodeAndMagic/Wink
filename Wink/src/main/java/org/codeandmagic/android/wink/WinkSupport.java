package org.codeandmagic.android.wink;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by evelyne24.
 */
public class WinkSupport extends DialogFragment implements IWink {

    public static final String FRAGMENT_TAG = WinkSupport.class.getName();

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        public Builder(Context context) {
            super(context);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public WinkSupport build() {
            return (WinkSupport) WinkSupport.instantiate(context, FRAGMENT_TAG, bundle());
        }

        public WinkSupport show(FragmentManager fragmentManager) {
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            final Fragment previousWink = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

            if (previousWink != null) {
                fragmentTransaction.remove(previousWink);
            }

            final WinkSupport wink = build();
            wink.show(fragmentTransaction, FRAGMENT_TAG);
            return wink;
        }
    }

    protected final Delegate delegate = new Delegate(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(getArguments());

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        setCancelable(delegate.isCancelable());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog;
        if (delegate.useHoloTheme()) {
            dialog = new Dialog(getActivity(), R.style.WinkDialogStyle);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            dialog = super.onCreateDialog(savedInstanceState);
        }
        dialog.setCanceledOnTouchOutside(delegate.isCancelableOnTouchOutside());
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return delegate.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getWinkId() {
        return delegate.getWinkId();
    }

    @Override
    public WinkCallback getCallback() {
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof WinkCallback) {
            return (WinkCallback) targetFragment;
        }

        final Activity activity = getActivity();
        if (activity instanceof WinkCallback) {
            return (WinkCallback) activity;
        }
        return null;
    }

    @Override
    public void setTitleSpannable(Spannable titleSpannable) {
        delegate.setTitleSpannable(titleSpannable);
    }

    @Override
    public void setMessageSpannable(Spannable messageSpannable) {
        delegate.setMessageSpannable(messageSpannable);
    }

    @Override
    public void setListItems(ListAdapter adapter, AdapterView.OnItemClickListener listener, int choiceMode) {
        delegate.setListItems(adapter, listener, choiceMode);
    }
}
