package com.idemobi.show_my_app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

public class FragmentSetting extends Fragment {

    public EditText _AppName;
    public EditText _IOSAppID;
    public EditText _IPadAppID;
    public EditText _MacAppID;
    public EditText _AndroidAppID;
    public RadioGroup _StyleLink;
    public Switch _TinyLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tRootView = inflater.inflate(R.layout.fragment_setting, container, false);

        _AppName = tRootView.findViewById(R.id.editTextAppName);
        _IOSAppID = tRootView.findViewById(R.id.editTextIOSAppID);
        _IPadAppID = tRootView.findViewById(R.id.editTextIPadAppID);
        _MacAppID = tRootView.findViewById(R.id.editTextMacAppID);
        _AndroidAppID = tRootView.findViewById(R.id.editTextAndroidAppID);
        _StyleLink = tRootView.findViewById(R.id.radioStyle);
        _TinyLink = tRootView.findViewById(R.id.switchTinyLink);

        Button tButtonFill = tRootView.findViewById(R.id.buttonFill);
        tButtonFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _AppName.setText("Google");
                _IOSAppID.setText("id284815942");
                _IPadAppID.setText("");
                _MacAppID.setText("");
                _AndroidAppID.setText("com.google.android.googlequicksearchbox");
            }
        });

        Button tButtonTest = tRootView.findViewById(R.id.buttonTest);
        tButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tFragment = new FragmentTest();
                tFragment.setArguments(setBundle());

                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, tFragment)
                        .addToBackStack("test")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        Button tButtonShare = tRootView.findViewById(R.id.buttonShare);
        tButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tFragment = new FragmentShare();
                tFragment.setArguments(setBundle());

                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, tFragment)
                        .addToBackStack("share")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return tRootView;
    }

    private Bundle setBundle() {
        Bundle rBundle = new Bundle();
        rBundle.putString(Consts.APP_NAME, _AppName.getText().toString());
        rBundle.putString(Consts.IOS_APP_ID, _IOSAppID.getText().toString());
        rBundle.putString(Consts.IPAD_APP_ID, _IPadAppID.getText().toString());
        rBundle.putString(Consts.MAC_APP_ID, _MacAppID.getText().toString());
        rBundle.putString(Consts.ANDROID_APP_ID, _AndroidAppID.getText().toString());
        rBundle.putBoolean(Consts.TINY_LINK, _TinyLink.isChecked());

        int tID =_StyleLink.getCheckedRadioButtonId();
        View tRadioButton = _StyleLink.findViewById(tID);
        rBundle.putInt(Consts.STYLE_LINK, _StyleLink.indexOfChild(tRadioButton));

        return rBundle;
    }
}