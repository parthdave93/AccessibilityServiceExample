package com.accessibilityexample.networktask;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.accessibilityexample.utils.Constants;
import com.accessibilityexample.models.InnerSounds;
import com.accessibilityexample.utils.NetworkTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends NetworkTask {

    private InnerSounds innerSound;
    private String url;
    private String mediaPath;
    private String filePath;
    HttpURLConnection connection = null;
    private boolean downloadVideo = false;
    String downloadFileFromUrl = null;


    InputStream input = null;
    OutputStream output = null;

    public DownloadTask(Context context, String message, NetworkTaskListener listener, boolean isShowProgress) {
        super(context, message, listener, isShowProgress);
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        mediaPath = downloadVideo ? Constants.getDownloadedVideoFolderDirectory() : Constants.getDownloadedFolderDirectory();
        // External sdcard location
        File mediaStorageDir = new File(mediaPath);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        File file = null;
        if (!downloadFileFromUrl.contains("http"))
            return false;
        if (!TextUtils.isEmpty(downloadFileFromUrl))
            file = new File(mediaPath + downloadFileFromUrl.substring(downloadFileFromUrl.lastIndexOf("/")));
        else
            return false;

        if (file.exists()) {
            filePath = file.getPath();
            return true;
        }



        downloadFile(downloadFileFromUrl);

        return true;
    }


    private boolean downloadFile(String serverDownloadurl) {

        try {
            URL url = new URL(serverDownloadurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("Demo", "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(mediaPath + serverDownloadurl.substring(serverDownloadurl.lastIndexOf("/")) + "");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return false;
                }
                total += count;
                output.write(data, 0, count);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (new File(filePath).exists()) {
                new File(filePath).delete();
            }
            return false;
        } finally

        {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                return false;
            }

            if (connection != null)
                connection.disconnect();

            filePath = mediaPath + serverDownloadurl.substring(serverDownloadurl.lastIndexOf("/")) + "";

        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public InnerSounds getInnerSound() {
        return innerSound;
    }

    public void setInnerSound(InnerSounds innerSound) {
        this.innerSound = innerSound;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDownloadVideo() {
        return downloadVideo;
    }

    public void setDownloadVideo(boolean downloadVideo) {
        this.downloadVideo = downloadVideo;
    }

    public String getDownloadFileFromUrl() {
        return downloadFileFromUrl;
    }

    public void setDownloadFileFromUrl(String downloadFileFromUrl) {
        this.downloadFileFromUrl = downloadFileFromUrl;
    }
}
