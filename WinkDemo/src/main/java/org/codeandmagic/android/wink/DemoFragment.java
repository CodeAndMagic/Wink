package org.codeandmagic.android.wink;

import android.support.v4.app.Fragment;

/**
 * Created by evelyne24.
 */
public class DemoFragment extends Fragment implements WinkCallback {

    @Override
    public void onButtonClicked(int buttonId, IWink wink) {
        if(R.id.wink_custom_layout == wink.getWinkId()) {
            wink.dismiss();
        }
    }
}
