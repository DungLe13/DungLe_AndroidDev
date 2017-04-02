package com.dungle1310.personalnotes;

import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Dung on 1/31/2017.
 */

public class DropBoxImageUploadAsync extends AsyncTask<Void, Long, Boolean> {
    private DropboxAPI<?> mApi;
    private String mPath;
    private File mFile;
    private String mFileName;

    public DropBoxImageUploadAsync(Context context, DropboxAPI<?> api, File file, String fileName) {
        mApi = api;
        mFile = file;
        mFileName = fileName;
        mPath = AppSharedPreferences.getDropBoxUploadPath(context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String errorMessage;
        try {
            FileInputStream fis = new FileInputStream(mFile);
            String path = mPath + "/" + mFileName;
            DropboxAPI.UploadRequest request = mApi.putFileOverwriteRequest(path, fis, mFile.length(),
                    new ProgressListener() {
                        @Override
                        public long progressInterval() {
                            return 500;
                        }

                        @Override
                        public void onProgress(long bytes, long total) {
                            publishProgress(bytes);
                        }
                    });

            if (request != null) {
                request.upload();
                return true;
            }
        } catch (FileNotFoundException e) {
            errorMessage = "File Not Found exception";
        } catch (DropboxException e) {
            errorMessage = "Dropbox Exception";
        }

        return false;
    }
}
