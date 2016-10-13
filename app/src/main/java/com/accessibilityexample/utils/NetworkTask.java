package com.accessibilityexample.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public abstract class NetworkTask extends AsyncTask<String, Integer, Boolean> {

    protected Context context;
    protected NetworkTaskListener networkTaskListener;
    protected ProgressDialog pd;
    protected String message;
    protected boolean canceled;
    protected boolean isShowProgress;
    protected ProgressBar progressBar;

    public NetworkTask(Context context, String message, NetworkTaskListener listener, boolean isShowProgress) {
        this.context = context;
        this.networkTaskListener = listener;
        this.message = message;
        this.isShowProgress = isShowProgress;
    }

    public NetworkTask(Context context, String message, NetworkTaskListener listener, boolean isShowProgress, ProgressBar progressBar) {
        this(context, message, listener, isShowProgress);
        this.progressBar = progressBar;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Boolean result) {

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        } else if (pd != null)
            try {
                pd.dismiss();
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            }

        if (networkTaskListener != null)
            networkTaskListener.OnTaskCompleted(result, canceled);
        super.onPostExecute(result);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {

        //LogTag.v("onPreExecute " + isShowProgress);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (isShowProgress) {
            pd = new ProgressDialog(context);
            if (message != null && !message.equals(""))
                pd.setMessage(message);
            pd.setCancelable(false);
            pd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    canceled = true;
                    if (networkTaskListener != null)
                        networkTaskListener.OnTaskCompleted(false, canceled);
                    NetworkTask.this.cancel(true);
                    dialog.dismiss();
                }
            });
            pd.setCanceledOnTouchOutside(false);
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    canceled = true;
                    NetworkTask.this.cancel(true);
                }
            });
            try {
                if (message != null && !message.equals(""))
                    pd.show();
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            }

        }


        super.onPreExecute();
    }

    protected void onSuperPreExecute() {
        super.onPreExecute();
    }

    public static interface NetworkTaskListener {
        public void OnTaskCompleted(boolean success, boolean canceled);
    }
}
