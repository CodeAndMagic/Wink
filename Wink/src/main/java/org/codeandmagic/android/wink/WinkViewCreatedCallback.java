package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.view.View;

/**
 * Created by evelyne24 on 12/10/2013.
 */
public interface WinkViewCreatedCallback {

    void onDialogViewCreated(View view, Bundle savedInstanceState, IWink wink);
}
