package com.idemobi.show_my_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentTest extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentTest";
    private String _Params = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tRootView = inflater.inflate(R.layout.fragment_test, container, false);

        _Params = DownloaderHelper.setParams(this.getArguments(), true);
        DownloaderHelper.doDownloadQRCode(tRootView, Consts.QRCODE_FILE, Consts.QRCODE, _Params, false);
        DownloaderHelper.doDownloadQRCode(tRootView, Consts.QRCODE_TINY_FILE, Consts.QRCODE_TINY, _Params, true);
        DownloaderHelper.doDownloadLinkTiny(tRootView, Consts.QRCODE_TINY_LINK_FILE, Consts.LINK_TINY, _Params);

        TextView tTextURLQRCode = tRootView.findViewById(R.id.textURLQRCode);
        String tURL = Consts.URL + Consts.LINK;
        tURL = tURL.concat(_Params);
        tTextURLQRCode.setText(tURL);

        Button tButtonShareQRCode = tRootView.findViewById(R.id.buttonShareQRCode);
        tButtonShareQRCode.setOnClickListener(this);

        Button tButtonShareLink = tRootView.findViewById(R.id.buttonShareLink);
        tButtonShareLink.setOnClickListener(this);

        Button tButtonShareTinyQRCode = tRootView.findViewById(R.id.buttonShareTinyQRCode);
        tButtonShareTinyQRCode.setOnClickListener(this);

        Button tButtonSharetinyLink = tRootView.findViewById(R.id.buttonShareTinyLink);
        tButtonSharetinyLink.setOnClickListener(this);

        Button tButtonBrowser = tRootView.findViewById(R.id.buttonShare);
        tButtonBrowser.setOnClickListener(this);

        return tRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonShare:
                {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse(DownloaderHelper._TinyLink));
                    startActivity(sendIntent);
                }
                break;
            case R.id.buttonShareLink:
                {
                    String tURL = Consts.URL + Consts.LINK;
                    tURL = tURL.concat(_Params);

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, tURL);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
            case R.id.buttonShareTinyLink:
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, DownloaderHelper._TinyLink);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
            case R.id.buttonShareQRCode:
                {
                    File directory = Environment.getExternalStorageDirectory();
                    File file = new File(directory, Consts.QRCODE_FILE);
                    Uri tURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setDataAndType(tURI, "image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                }
                break;
            case R.id.buttonShareTinyQRCode:
                {
                    File directory = Environment.getExternalStorageDirectory();
                    File file = new File(directory, Consts.QRCODE_TINY_FILE);
                    Uri tURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setDataAndType(tURI, "image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                }
                break;
        }
    }
}
