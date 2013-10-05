package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DemoActivity extends ActionBarActivity implements WinkCallback {

    private CustomCallback customCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        if(savedInstanceState == null) {
            customCallback = new CustomCallback();
            getSupportFragmentManager().beginTransaction().add(customCallback, "callback").commit();
        }
        else {
            customCallback = (CustomCallback) getSupportFragmentManager().findFragmentByTag("callback");
        }
    }

    public static  class CustomCallback extends Fragment implements WinkCallback {
        @Override
        public void onButtonClicked(int buttonId, IWink wink) {
            Toast.makeText(getActivity(), "Custom callback called!", Toast.LENGTH_SHORT).show();
            wink.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showWinkHoloLight(View view) {
        WinkSupport.Builder(this)
                .setId(R.id.wink_holo_light)
                .setTitle(R.string.custom_title)
                .setMessage(R.string.custom_message)
                .setUseLightTheme(true)
                .setLeftButton(android.R.string.ok)
                .setMiddleButton(android.R.string.cancel)
                .setRightButton("No")
                .show(getSupportFragmentManager());
    }

    public void showWinkHoloDark(View view) {
        WinkSupport.Builder(this)
                .setId(R.id.wink_holo_dark)
                .setTitle(R.string.custom_title)
                .setMessage(R.string.custom_message)
                .setTitleIcon(R.drawable.wink_ic_warning_dark)
                .setLeftButton(android.R.string.ok)
                .setRightButton(android.R.string.cancel)
                .show(getSupportFragmentManager());
    }

    public void showWinkCustomTheme(View view) {
        WinkSupport.Builder(this)
                .setId(R.id.wink_custom_theme)
                .setTitle(R.string.custom_title)
                .setMessage(R.string.custom_theme)
                .setUseHoloTheme(true)
                .setThemeId(R.style.MyWinkTheme)
                .setLeftButton(android.R.string.ok)
                .setRightButton(android.R.string.cancel)
                .show(getSupportFragmentManager());
    }

    public void showWinkCustomLayout(View view) {
        final WinkSupport wink =  WinkSupport.Builder(this)
                .setId(R.id.wink_custom_layout)
                .setUseHoloTheme(true)
                .setLayoutId(R.layout.wink_custom_layout)
                .setLeftButtonId(R.id.wink_left_button)
                .setRightButtonId(R.id.wink_right_button)
                .build();
        wink.setTargetFragment(customCallback, 0);
        wink.show(getSupportFragmentManager(), WinkSupport.FRAGMENT_TAG);
    }

    public void showWinkListSingleChoice(View view) {
        final WinkSupport wink = WinkSupport.Builder(this)
                .setId(R.id.wink_list_single)
                .setUseLightTheme(true)
                .setTitle(R.string.planets)
                .setRightButton(android.R.string.ok)
                .build();


        ListAdapter adapter = ArrayAdapter.createFromResource(this, R.array.planets, android.R.layout.simple_list_item_single_choice);
        wink.setListItems(adapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DemoActivity.this, "Clicked position " + position, Toast.LENGTH_SHORT).show();
            }
        }, ListView.CHOICE_MODE_SINGLE);

        wink.show(getSupportFragmentManager(), WinkSupport.FRAGMENT_TAG);
    }

    public void showWinkListMultipleChoice(View view) {
        final WinkSupport wink = WinkSupport.Builder(this)
                .setId(R.id.wink_list_single)
                .setUseLightTheme(true)
                .setTitle(R.string.planets)
                .setRightButton(android.R.string.ok)
                .build();


        ListAdapter adapter = ArrayAdapter.createFromResource(this, R.array.planets, android.R.layout.simple_list_item_multiple_choice);
        wink.setListItems(adapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DemoActivity.this, "Clicked position " + position, Toast.LENGTH_SHORT).show();
            }
        }, ListView.CHOICE_MODE_MULTIPLE);

        wink.show(getSupportFragmentManager(), WinkSupport.FRAGMENT_TAG);
    }

    @Override
    public void onButtonClicked(int buttonId, IWink wink) {
        wink.dismiss();
    }
}
