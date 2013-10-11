package org.codeandmagic.android.wink;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by evelyne24.
 */
public interface IWink {

    void dismiss();

    int getWinkId();

    <T> T getSerializable();

    <T> ArrayList<T> getSerializableArrayList();

    <T> T[] getSerializableArray();

    <T> T getParcelable();

    <T> ArrayList<T> getParcelableArrayList();

    <T> T[] getParcelableArray();

    Activity getActivity();

    WinkButtonCallback getButtonCallback();

    WinkListCallback getListCallback();
}
