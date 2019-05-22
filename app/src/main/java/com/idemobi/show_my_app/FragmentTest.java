package com.idemobi.show_my_app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTest extends Fragment implements View.OnClickListener {

    private TextView _label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tRootView = inflater.inflate(R.layout.fragment_test, container, false);

        /*Button tButton = tRootView.findViewById(R.id.button);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });*/

        Button _buttonListener = tRootView.findViewById(R.id.buttonListener);
        _buttonListener.setOnClickListener(this);

        _label = tRootView.findViewById(R.id.textView);
        Button tButton = tRootView.findViewById(R.id.button);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _label.setText("Click Me from 'setOnClickListener'");
            }
        });

        return tRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        //((Button)v).getText();
        _label.setText("Click Me from 'implements View.OnClickListener'");
    }
}
