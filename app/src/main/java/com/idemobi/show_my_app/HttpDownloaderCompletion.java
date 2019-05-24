package com.idemobi.show_my_app;

public interface HttpDownloaderCompletion {
    void completionPostExecute(Boolean result);
    void completionProgressUpdate(Integer progress);
    //void completionCancelExecute();
}
