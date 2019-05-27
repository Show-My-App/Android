package com.idemobi.show_my_app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class FragmentShare extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentTest";
    private String _Params = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tRootView = inflater.inflate(R.layout.fragment_share, container, false);

        _Params = DownloaderHelper.setParams(this.getArguments(), false);
        DownloaderHelper.doDownloadQRCode(tRootView, Consts.QRCODE_FILE, Consts.QRCODE, _Params, false);
        if (DownloaderHelper._IsTinyLink) {
            DownloaderHelper.doDownloadLinkTiny(tRootView, Consts.QRCODE_TINY_LINK_FILE, Consts.LINK_TINY, _Params);
        }

        Button tButtonShare = tRootView.findViewById(R.id.buttonShare);
        tButtonShare.setOnClickListener(this);

        return tRootView;
    }

    @Override
    public void onClick(View v) {
        String tURL = Consts.URL + Consts.LINK;
        tURL = tURL.concat(_Params);
        if (DownloaderHelper._IsTinyLink)
        {
            tURL = DownloaderHelper._TinyLink;
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, tURL);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
