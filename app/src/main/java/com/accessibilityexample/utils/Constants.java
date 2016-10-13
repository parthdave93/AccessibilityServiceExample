package com.accessibilityexample.utils;

import android.os.Environment;

import java.io.File;

public class Constants {

    //UMApp.getApp().getFilesDir()//Environment.getExternalStorageDirectory()
    private static final String workFolderDirectory = Environment.getExternalStorageDirectory()+"/.TingTong/";
    private static final String sharedFolderDirectory = getWorkFolderDirectory()+"shared/";
    private static final String imageFolderDirectory = getWorkFolderDirectory()+"image/";
    private static final String recordedFolderDirectory = getWorkFolderDirectory()+"recorded/";
    private static final String downloadedFolderDirectory = getWorkFolderDirectory()+"downloaded/";
    private static final String downloadedVideosFolderDirectory = getWorkFolderDirectory()+"downloadedvideos/";

    private static String getWorkFolderDirectory() {
        if(!new File(workFolderDirectory).exists())
            new File(workFolderDirectory).mkdir();
        return workFolderDirectory;
    }

    public static String getSharedFolderDirectory() {
        if(!new File(sharedFolderDirectory).exists())
            new File(sharedFolderDirectory).mkdir();
        return sharedFolderDirectory;
    }

    public static String getImageFolderDirectory() {
        if(!new File(imageFolderDirectory).exists())
            new File(imageFolderDirectory).mkdir();
        return imageFolderDirectory;
    }

    public static String getRecordedFolderDirectory() {
        if(!new File(recordedFolderDirectory).exists())
            new File(recordedFolderDirectory).mkdir();
        return recordedFolderDirectory;
    }

    public static String getDownloadedFolderDirectory() {
        if(!new File(downloadedFolderDirectory).exists())
            new File(downloadedFolderDirectory).mkdir();
        return downloadedFolderDirectory;
    }

    public static String getDownloadedVideoFolderDirectory() {
        if(!new File(downloadedVideosFolderDirectory).exists())
            new File(downloadedVideosFolderDirectory).mkdir();
        return downloadedVideosFolderDirectory;
    }
}
