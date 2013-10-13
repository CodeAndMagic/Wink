package org.codeandmagic.android.wink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import org.codeandmagic.android.wink.support.Wink;

/**
 * Created by evelyne24 on 12/10/2013.
 */
public class FragmentListDemo extends FragmentHoloThemeDemo implements WinkListCallback {

    public static final String TAG = FragmentListDemo.class.getName();
    private static final String SAVE_LIST_CHOICE_MODE = "save_list_choice_mode";
    private static final String SAVE_LIST_ITEM_LAYOUT = "save_list_item_layout";

    private int listChoiceMode = ListView.CHOICE_MODE_SINGLE;
    private int listItemLayout = android.R.layout.simple_list_item_single_choice;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            listChoiceMode = savedInstanceState.getInt(SAVE_LIST_CHOICE_MODE);
            listItemLayout = savedInstanceState.getInt(SAVE_LIST_ITEM_LAYOUT);
        }
        listAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets, listItemLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_LIST_CHOICE_MODE, listChoiceMode);
        outState.putInt(SAVE_LIST_ITEM_LAYOUT, listItemLayout);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.list_choice_buttons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(R.id.single_choice_button == checkedId) {
                    listChoiceMode = ListView.CHOICE_MODE_SINGLE;
                    listItemLayout = android.R.layout.simple_list_item_single_choice;
                }
                else {
                    listChoiceMode = ListView.CHOICE_MODE_MULTIPLE;
                    listItemLayout = android.R.layout.simple_list_item_multiple_choice;
                }
                listAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets, listItemLayout);
            }
        });
    }

    @Override
    protected void showDialog() {
        new Wink.Builder(getActivity())
                .setWinkId(DIALOG_SHOW)
                .setTitle(R.string.hello_title)
                .setUseLightTheme(useLightTheme)
                .setAccentColor(colorPicker.getColor())
                .setPositiveButton(R.string.awesome)
                .setCancelable(false)
                .setCancelableOnTouchOutside(false)
                .setTargetFragmentTag(TAG)
                .show(getChildFragmentManager());
    }

    @Override
    public void onDialogViewCreated(View view, Bundle savedInstanceState, IWink wink) {
        super.onDialogViewCreated(view, savedInstanceState, wink);
        wink.setListItems(listAdapter, listChoiceMode);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IWink dialog) {

    }
}
