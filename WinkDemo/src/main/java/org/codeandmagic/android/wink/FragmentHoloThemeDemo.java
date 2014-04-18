package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.codeandmagic.android.wink.support.Wink;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

/**
 * Created by evelyne24 on 09/10/2013.
 */
public class FragmentHoloThemeDemo extends Fragment implements WinkButtonCallback, WinkViewCreatedCallback {

    public static final String TAG = FragmentHoloThemeDemo.class.getName();
    protected static final int DIALOG_SHOW = 0;
    protected static final int DIALOG_RECONSIDER = 1;

    protected ColorPicker colorPicker;
    protected boolean useLightTheme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holo_theme_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        colorPicker = (ColorPicker) view.findViewById(R.id.color_picker);
        colorPicker.setColor(getResources().getColor(R.color.wink_accent_dark));
        colorPicker.addSVBar((SVBar) view.findViewById(R.id.color_picker_saturation));
        colorPicker.addOpacityBar((OpacityBar) view.findViewById(R.id.color_picker_opacity));
        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                colorPicker.setOldCenterColor(color);
            }
        });

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.holo_theme_buttons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                useLightTheme = (R.id.holo_light_button == checkedId);
                colorPicker.setColor(getResources().getColor(useLightTheme ?
                        R.color.wink_accent_light : R.color.wink_accent_dark));
            }
        });

        final View showButton = view.findViewById(R.id.show_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });
    }

    protected void showDialog() {
        new Wink.Builder(getActivity())
                .setWinkId(DIALOG_SHOW)
                .setTitle(R.string.hello_title)
                .setMessage(R.string.hello_message)
                .setUseLightTheme(useLightTheme)
                .setAccentColor(colorPicker.getColor())
                .setPositiveButton(R.string.awesome)
                .setNeutralButton(R.string.hmm)
                .setNegativeButton(R.string.no)
                .setTargetFragmentTag(TAG)
                .show(getChildFragmentManager());
    }

    @Override
    public void onButtonClicked(int buttonId, IWink wink) {
        wink.dismiss();

        if(DIALOG_SHOW == wink.getWinkId()) {
            if(R.id.negative_button == buttonId) {
                new Wink.Builder(getActivity())
                        .setWinkId(DIALOG_RECONSIDER)
                        .setTitle(R.string.reconsider_title)
                        .setMessage(R.string.reconsider_message)
                        .setUseLightTheme(useLightTheme)
                        .setAccentColor(colorPicker.getColor())
                        .setPositiveButton(R.string.ok)
                        .setTargetFragmentTag(TAG)
                        .show(getChildFragmentManager());
            }
        }
    }

    @Override
    public void onDialogViewCreated(View view, Bundle savedInstanceState, IWink wink) {
        if(DIALOG_RECONSIDER == wink.getWinkId()) {
            Linkify.addLinks(wink.getMessageView(), Linkify.WEB_URLS);
        }
    }
}
