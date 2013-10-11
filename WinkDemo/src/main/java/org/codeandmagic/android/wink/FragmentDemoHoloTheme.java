package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.codeandmagic.android.wink.support.Wink;

import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.OpacityBar;
import com.larswerkman.colorpicker.SVBar;

/**
 * Created by evelyne24 on 09/10/2013.
 */
public class FragmentDemoHoloTheme extends Fragment implements WinkButtonCallback {

    private ColorPicker colorPicker;
    private boolean useLightTheme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holo_theme, container, false);
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
                new Wink.Builder(getActivity())
                        .setTitle(R.string.hello_title)
                        .setMessage(R.string.hello_message)
                        .setUseLightTheme(useLightTheme)
                        .setAccentColor(colorPicker.getColor())
                        .setPositiveButton(R.string.awesome)
                        .setNeutralButton(R.string.hmm)
                        .setNegativeButton(R.string.no)
                        .setTargetFragment(FragmentDemoHoloTheme.this)
                        .show(getChildFragmentManager());
            }
        });
    }

    @Override
    public void onButtonClicked(int buttonId, IWink wink) {
        if(R.id.negative_button == buttonId) {
            wink.dismiss();
            new Wink.Builder(getActivity())
                    .setTitle(R.string.reconsider_title)
                    .setMessage(R.string.reconsider_message)
                    .setUseLightTheme(useLightTheme)
                    .setAccentColor(colorPicker.getColor())
                    .setPositiveButton(R.string.ok)
                    .show(getChildFragmentManager());
        }
    }
}
