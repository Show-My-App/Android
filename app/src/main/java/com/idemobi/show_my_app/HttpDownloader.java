package com.idemobi.show_my_app;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader extends AsyncTask<Void, Integer, Boolean> {

    private HttpDownloaderCompletion mListener;
    private String mURL;
    private String mFileName;

    private static final String TAG = "HttpDownloader";
    private static final int BUFFER_SIZE = 4096;

    HttpDownloader(String sURL, String sFileName, HttpDownloaderCompletion sListener) {
        mListener  = sListener;
        mURL = sURL;
        mFileName = sFileName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean rReturn = false;
        try {
            URL url = new URL(mURL);
            HttpURLConnection tHttpConn = (HttpURLConnection) url.openConnection();
            int responseCode = tHttpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String disposition = tHttpConn.getHeaderField("Content-Disposition");
                String contentType = tHttpConn.getContentType();
                float tContentLength = tHttpConn.getContentLength();

                Log.d(TAG, "Content-Type = " + contentType);
                Log.d(TAG, "Content-Disposition = " + disposition);
                Log.d(TAG, "Content-Length = " + tContentLength);
                Log.d(TAG, "fileName = " + mFileName);

                String tRoot = Environment.getExternalStorageDirectory().toString();
                String tSaveFilePath = tRoot + File.separator + mFileName;
                Log.d(TAG, "doInBackground: " + tSaveFilePath);

                //doDownload(tHttpConn, tSaveFilePath, tContentLength);
                doDownload2(url, tSaveFilePath, tContentLength);

                rReturn = true;
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }

            tHttpConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return rReturn;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mListener != null) {
            mListener.completionProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mListener != null) {
            mListener.completionPostExecute(result);
        }
    }

    private void doDownload(HttpURLConnection sHttpConn, String sSaveFilePath, float sContentLength) {
        try {
            // opens input stream from the HTTP connection
            InputStream i = sHttpConn.getInputStream();

            // opens an output stream to save into file
            FileOutputStream o = new FileOutputStream(sSaveFilePath);

            float totlaBytesRead = 0;
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = i.read(buffer)) != -1) {
                // writing data to file
                o.write(buffer, 0, bytesRead);
                totlaBytesRead += bytesRead;
                publishProgress((int)((totlaBytesRead / sContentLength)*100));
            }

            // flushing output
            o.flush();

            // closing streams
            o.close();
            i.close();

            System.out.println("File downloaded");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void doDownload2(URL sURL, String sSaveFilePath, float sContentLength) {
        try {
            // input stream to read file - with 8k buffer
            InputStream i = new BufferedInputStream(sURL.openStream(), 8192);

            // Output stream to write file
            OutputStream o = new FileOutputStream(sSaveFilePath);

            float totlaBytesRead = 0;
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = i.read(buffer)) != -1) {
                // writing data to file
                o.write(buffer, 0, bytesRead);
                totlaBytesRead += bytesRead;
                publishProgress((int)((totlaBytesRead / sContentLength)*100));
            }

            // flushing output
            o.flush();

            // closing streams
            o.close();
            i.close();

            System.out.println("File downloaded");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}