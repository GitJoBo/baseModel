/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kinbong.base.ui.view.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.kinbong.base.utils.AppManager;
import com.kinbong.base.utils.UIUtils;
import com.kinbong.model.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDownloader extends AsyncTask<Void, Integer, Long> {

    private static final int TIME_OUT = 30000;
    private static final int BUFFER_SIZE = 1024 * 100;

    private final static int EVENT_START = 1;
    private final static int EVENT_PROGRESS = 2;
    private final static int EVENT_COMPLETE = 3;
    private final ProgressDialog mDialog;

    private Context mContext;

    private String mUrl;
    private File mTemp;

    private long mBytesLoaded = 0;
    private long mBytesTotal = 0;
    private long mBytesTemp = 0;
    private long mTimeBegin = 0;
    private long mTimeUsed = 1;
    private long mTimeLast = 0;
    private long mSpeed = 0;

    private HttpURLConnection mConnection;

    public UpdateDownloader( Context context, String url, File file) {
        super();
        mContext = context;

        mUrl = url;
        mTemp = file;
        if (mTemp.exists()) {
            mBytesTemp = mTemp.length();
        }
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMessage("下载中...");
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public long getBytesLoaded() {
        return mBytesLoaded + mBytesTemp;
    }


    @Override
    protected Long doInBackground(Void... params) {
        mTimeBegin = System.currentTimeMillis();
        try {
           download();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (mConnection != null) {
                mConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        switch (progress[0]) {
        case EVENT_START:
           mDialog.setProgress(0);
            break;
        case EVENT_PROGRESS:
            long now = System.currentTimeMillis();
            if (now - mTimeLast < 900) {
                break;
            }
            mTimeLast = now;
            mTimeUsed = now - mTimeBegin;
            mSpeed = mBytesLoaded * 1000 / mTimeUsed;
            mDialog.setProgress((int) (this.getBytesLoaded() * 100 / mBytesTotal));
            break;
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        mDialog.setProgress(100);
        UIUtils.install(mContext, mTemp);
        if(mDialog.isShowing())mDialog.dismiss();
        AppManager.getAppManager().appExit();
    }


    private HttpURLConnection create(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/*");
        connection.setConnectTimeout(10000);
        return connection;
    }

    private long download() throws IOException {


        mConnection = create(new URL(mUrl));
        mConnection.connect();


        mBytesTotal = mConnection.getContentLength();



        if (mBytesTemp == mBytesTotal) {
            publishProgress(EVENT_START);
            return 0;
        }
        if (mBytesTemp > 0) {

            mConnection.disconnect();
            mConnection = create(mConnection.getURL());
            mConnection.addRequestProperty("Range", "bytes=" + mBytesTemp + "-");
            mConnection.connect();


        }

        publishProgress(EVENT_START);

        int bytesCopied = copy(mConnection.getInputStream(), new LoadingRandomAccessFile(mTemp));

        if (isCancelled()) {
        } else if ((mBytesTemp + bytesCopied) != mBytesTotal && mBytesTotal != -1) {
            LogUtil.i("download incomplete(" + mBytesTemp + " + " + bytesCopied + " != " + mBytesTotal + ")");

        }

        return bytesCopied;

    }

    private int copy(InputStream in, RandomAccessFile out) throws IOException{

        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(in, BUFFER_SIZE);
        try {

            out.seek(out.length());

            int bytes = 0;
            long previousBlockTime = -1;

            while (!isCancelled()) {
                int n = bis.read(buffer, 0, BUFFER_SIZE);
                if (n == -1) {
                    break;
                }
                out.write(buffer, 0, n);
                bytes += n;


                if (mSpeed != 0) {
                    previousBlockTime = -1;
                } else if (previousBlockTime == -1) {
                    previousBlockTime = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - previousBlockTime) > TIME_OUT) {

                }
            }
            return bytes;
        } finally {
            out.close();
            bis.close();
            in.close();
        }
    }

    private final class LoadingRandomAccessFile extends RandomAccessFile {

        public LoadingRandomAccessFile(File file) throws FileNotFoundException {
            super(file, "rw");
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {

            super.write(buffer, offset, count);
            mBytesLoaded += count;
            publishProgress(EVENT_PROGRESS);
        }
    }


}