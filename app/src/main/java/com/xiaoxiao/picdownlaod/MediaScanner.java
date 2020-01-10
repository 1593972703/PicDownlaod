package com.xiaoxiao.picdownlaod;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * 系统相册/图库更新
 */
public class MediaScanner {
    private static final String TAG = MediaScanner.class.getSimpleName();
    private MediaScannerConnection mConn = null;
    private SannerClient mClient;
    private File mFile = null;
    private String mMimeType = null;

    public MediaScanner(Context context) {
        if (mClient == null) {
            mClient = new SannerClient();
        }
        if (mConn == null) {
            mConn = new MediaScannerConnection(context, (MediaScannerConnection.MediaScannerConnectionClient) mClient);
        }
    }

    class SannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
        public void onMediaScannerConnected() {
            if (mFile == null) {
                return;
            }
            scan(mFile, mMimeType);
        }

        public void onScanCompleted(String path, Uri uri) {
            mConn.disconnect();
        }

        private void scan(File file, String type) {
            if (file.isFile()) {
                mConn.scanFile(file.getAbsolutePath(), null);
                return;
            }
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : file.listFiles()) {
                scan(f, type);
            }
        }
    }



    public void scanFile(File file, String mimeType) {
        mFile = file;
        mMimeType = mimeType;
        mConn.connect();
    }
}


