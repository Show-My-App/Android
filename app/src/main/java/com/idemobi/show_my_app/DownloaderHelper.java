package com.idemobi.show_my_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class DownloaderHelper {

    private static final String TAG = "DownloaderHelper";
    static String _TinyLink = "";
    static Boolean _IsTinyLink = false;

    static void doDownloadQRCode(final View tRootView, final String sFileName, final String sService, final String sParams, final boolean isTiny) {
        String tURL = Consts.URL + sService;
        tURL = tURL.concat(sParams);

        // Get QRCode image
        HttpDownloader tTask = new HttpDownloader(tURL, sFileName, new HttpDownloaderCompletion() {
            @Override
            public void completionPostExecute(Boolean result) {
                if (result) {
                    File directory = Environment.getExternalStorageDirectory();
                    File file = new File(directory, sFileName);

                    if (file.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        ImageView myImage = tRootView.findViewById(R.id.imageQRCode);
                        if (isTiny) {
                            myImage = tRootView.findViewById(R.id.imageTinyQRCode);
                        }
                        myImage.setImageBitmap(myBitmap);
                    }
                }
            }

            @Override
            public void completionProgressUpdate(Integer progress) {

            }
        });
        tTask.execute();
    }

    static void doDownloadLinkTiny(final View tRootView, final String sFileName, final String sService, final String sParams) {
        String tURL = Consts.URL + sService;
        tURL = tURL.concat(sParams);

        // Get QRCode image
        HttpDownloader tTask = new HttpDownloader(tURL, sFileName, new HttpDownloaderCompletion() {
            @Override
            public void completionPostExecute(Boolean result) {
                if (result) {
                    File directory = Environment.getExternalStorageDirectory();
                    File file = new File(directory, sFileName);

                    if (file.exists()) {
                        StringBuilder text = new StringBuilder();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }

                        TextView tTextURLTinyQRCode = tRootView.findViewById(R.id.textURLTinyQRCode);
                        if (tTextURLTinyQRCode != null) {
                            tTextURLTinyQRCode.setText(text);
                        }

                        _TinyLink = text.toString();
                    }
                }
            }

            @Override
            public void completionProgressUpdate(Integer progress) {

            }
        });
        tTask.execute();
    }

    static String setParams(Bundle sBundle, Boolean sRemoveTinyLink) {
        List<String> tParams = new ArrayList<>();
        if (sBundle != null) {
            String tAppName = sBundle.getString(Consts.APP_NAME, "");
            String tIOSAppID = sBundle.getString(Consts.IOS_APP_ID, "");
            String tIPadAppID = sBundle.getString(Consts.IPAD_APP_ID, "");
            String tMacAppID = sBundle.getString(Consts.MAC_APP_ID, "");
            String tAndroidAppID = sBundle.getString(Consts.ANDROID_APP_ID, "");
            int tStyleLink = sBundle.getInt(Consts.STYLE_LINK, 0);
            _IsTinyLink = sBundle.getBoolean(Consts.TINY_LINK, false);

            if (!tAppName.isEmpty()) {
                tParams.add(Consts.TAG_APP_NAME.concat(tAppName));
            }
            if (!tIOSAppID.isEmpty()) {
                tParams.add(Consts.TAG_IOS_APP_ID.concat(tIOSAppID));
            }
            if (!tIPadAppID.isEmpty()) {
                tParams.add(Consts.TAG_IPAD_APP_ID.concat(tIPadAppID));
            }
            if (!tMacAppID.isEmpty()) {
                tParams.add(Consts.TAG_MAC_APP_ID.concat(tMacAppID));
            }
            if (!tAndroidAppID.isEmpty()) {
                tParams.add(Consts.TAG_ANDROID_APP_ID.concat(tAndroidAppID));
            }
            tParams.add(Consts.TAG_STYLE_LINK.concat("" + tStyleLink));
            if (_IsTinyLink && !sRemoveTinyLink) {
                tParams.add(Consts.TAG_TINY_LINK);
            }
        }
        return TextUtils.join("&", tParams);
    }
}
