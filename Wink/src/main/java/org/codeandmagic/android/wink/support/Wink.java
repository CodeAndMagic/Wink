package org.codeandmagic.android.wink.support;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codeandmagic.android.wink.AbstractBuilder;
import org.codeandmagic.android.wink.IWink;
import org.codeandmagic.android.wink.Presenter;
import org.codeandmagic.android.wink.R;
import org.codeandmagic.android.wink.WinkButtonCallback;
import org.codeandmagic.android.wink.WinkListCallback;

import java.util.ArrayList;

import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE_ARRAY;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE_ARRAY_LIST;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE_ARRAY;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE_ARRAY_LIST;

/**
 * Created by evelyne24.
 */
public class Wink extends DialogFragment implements IWink {

    public static final String FRAGMENT_TAG = Wink.class.getName();

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private Fragment targetFragment;

        public Builder(Context context) {
            super(context);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder setTargetFragment(Fragment targetFragment) {
            this.targetFragment = targetFragment;
            return this;
        }

        @Override
        public Wink build() {
            final Wink wink = (Wink) Wink.instantiate(context, FRAGMENT_TAG, bundle());
            if(titleSpan != null) {
                wink.presenter.setTitleSpan(titleSpan);
            }
            if(messageSpan != null) {
                wink.presenter.setMessageSpan(messageSpan);
            }
            if(listAdapter != null) {
                wink.presenter.setListItems(listAdapter, listChoiceMode);
            }
            if(targetFragment != null) {
                wink.setTargetFragment(targetFragment, 0);
            }
            return wink;
        }

        public Wink show(FragmentManager fragmentManager) {
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            final Fragment previousWink = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

            if (previousWink != null) {
                fragmentTransaction.remove(previousWink);
            }

            final Wink wink = build();
            wink.show(fragmentTransaction, FRAGMENT_TAG);
            return wink;
        }
    }

    protected final Presenter presenter = new Presenter(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(getArguments());

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        setCancelable(presenter.isCancelable());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog;
        if (presenter.useHoloTheme()) {
            dialog = new Dialog(getActivity(), R.style.WinkDialogStyle);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            dialog = super.onCreateDialog(savedInstanceState);
        }
        dialog.setCanceledOnTouchOutside(presenter.isCancelableOnTouchOutside());
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return presenter.onCreateView(inflater, container);
    }

    @Override
    public int getWinkId() {
        return presenter.getWinkId();
    }

    @Override
    public <T> T getSerializable() {
        try {
            return (T) getArguments().getSerializable(ARG_SERIALIZABLE);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public <T> T[] getSerializableArray() {
        try {
            return (T[]) getArguments().getSerializable(ARG_SERIALIZABLE_ARRAY);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public <T> ArrayList<T> getSerializableArrayList() {
        try {
            return (ArrayList<T>) getArguments().getSerializable(ARG_SERIALIZABLE_ARRAY_LIST);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public <T> T getParcelable() {
        try {
            return (T) getArguments().getParcelable(ARG_PARCELABLE);
        } catch (ClassCastException e) {
            return null;
        }
    }


    @Override
    public <T> T[] getParcelableArray() {
        try {
            return (T[]) getArguments().getParcelableArray(ARG_PARCELABLE_ARRAY);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public <T> ArrayList<T> getParcelableArrayList() {
        try {
            return (ArrayList<T>) getArguments().getParcelableArrayList(ARG_PARCELABLE_ARRAY_LIST);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public WinkListCallback getListCallback() {
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof WinkListCallback) {
            return (WinkListCallback) targetFragment;
        }

        final Activity activity = getActivity();
        if (activity instanceof WinkButtonCallback) {
            return (WinkListCallback) activity;
        }
        return null;
    }

    @Override
    public WinkButtonCallback getButtonCallback() {
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof WinkButtonCallback) {
            return (WinkButtonCallback) targetFragment;
        }

        final Activity activity = getActivity();
        if (activity instanceof WinkButtonCallback) {
            return (WinkButtonCallback) activity;
        }
        return null;
    }
}
