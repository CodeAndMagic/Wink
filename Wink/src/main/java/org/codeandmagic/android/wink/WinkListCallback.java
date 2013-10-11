package org.codeandmagic.android.wink;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by evelyne24 on 11/10/2013.
 */
public interface WinkListCallback {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IWink dialog);
}
