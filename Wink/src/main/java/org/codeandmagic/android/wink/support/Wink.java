package org.codeandmagic.android.wink.support;

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
import android.widget.ListAdapter;
import android.widget.TextView;

import org.codeandmagic.android.wink.AbstractBuilder;
import org.codeandmagic.android.wink.IWink;
import org.codeandmagic.android.wink.Presenter;
import org.codeandmagic.android.wink.R;
import org.codeandmagic.android.wink.WinkButtonCallback;
import org.codeandmagic.android.wink.WinkListCallback;
import org.codeandmagic.android.wink.WinkViewCreatedCallback;

import java.util.ArrayList;

import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE_ARRAY;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_PARCELABLE_ARRAY_LIST;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE_ARRAY;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_SERIALIZABLE_ARRAY_LIST;
import static org.codeandmagic.android.wink.AbstractBuilder.ARG_TARGET_FRAGMENT_TAG;

/**
 * Created by evelyne24.
 */
public class Wink extends DialogFragment implements IWink {

    public static final String FRAGMENT_TAG = Wink.class.getName();

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
        protected Wink build() {
            final Wink wink = (Wink) Wink.instantiate(context, FRAGMENT_TAG, bundle());
            if (titleSpan != null) {
                wink.presenter.setTitleSpan(titleSpan);
            }
            if (messageSpan != null) {
                wink.presenter.setMessageSpan(messageSpan);
            }
            return wink;
        }

        public Wink show(FragmentManager fragmentManager) {
            final Wink  wink = build();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            final Fragment previousWink = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

            if (previousWink != null) {
                fragmentTransaction.remove(previousWink);
            }

            wink.show(fragmentTransaction, FRAGMENT_TAG);
            return wink;
        }
    }

    protected final Presenter presenter = new Presenter(this);
    protected String targetFragmentTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetFragmentTag = getArguments().getString(ARG_TARGET_FRAGMENT_TAG);
        presenter.onCreate(getArguments());

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        setCancelable(presenter.isCancelable());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.WinkDialogStyle);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(presenter.isCancelableOnTouchOutside());
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return presenter.onCreateView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Fragment targetFragment = getActivity().getSupportFragmentManager().findFragmentByTag(targetFragmentTag);
        if (targetFragment instanceof WinkViewCreatedCallback) {
            ((WinkViewCreatedCallback) targetFragment).onDialogViewCreated(view, savedInstanceState, this);
        }

        final Activity activity = getActivity();
        if (activity instanceof WinkViewCreatedCallback) {
            ((WinkViewCreatedCallback) activity).onDialogViewCreated(view, savedInstanceState, this);
        }
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
    public TextView getMessageView() {
        return presenter.getMessageView();
    }

    @Override
    public void setMessageSpan(Spannable messageSpan) {
        presenter.setMessageSpan(messageSpan);
    }

    @Override
    public void setTitleSpan(Spannable titleSpan) {
        presenter.setTitleSpan(titleSpan);
    }

    @Override
    public void setListItems(ListAdapter adapter, int choiceMode) {
        presenter.setListItems(adapter, choiceMode);
    }

    @Override
    public WinkListCallback getListCallback() {
        final Fragment targetFragment = getActivity().getSupportFragmentManager().findFragmentByTag(targetFragmentTag);
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
        final Fragment targetFragment = getActivity().getSupportFragmentManager().findFragmentByTag(targetFragmentTag);
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
