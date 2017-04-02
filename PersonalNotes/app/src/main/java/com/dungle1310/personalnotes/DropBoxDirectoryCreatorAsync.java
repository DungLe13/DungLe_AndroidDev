package com.dungle1310.personalnotes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

/**
 * Created by Dung on 1/29/2017.
 */

public class DropBoxDirectoryCreatorAsync extends AsyncTask<Void, Long, Boolean> {
    private DropboxAPI<?> mAPI;
    private String mPath;
    private Context mContext;
    private OnDirectoryCreateFinished mListener;
    private String mName;
    private String mMessage;

    public DropBoxDirectoryCreatorAsync(Context context, DropboxAPI<?> API, String name, String path, OnDirectoryCreateFinished listener) {
        mContext = context;
        mAPI = API;
        mName = name;
        mPath = path;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            mAPI.createFolder(mPath);
            mMessage = AppConstant.FOLDER_CREATED;
        } catch (DropboxException e) {
            mMessage = AppConstant.FOLDER_CREATE_ERROR;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            mListener.onDirectoryCreateFinished(mName);
            Toast.makeText(mContext.getApplicationContext(), mMessage, Toast.LENGTH_LONG).show();
        }
    }

    public interface OnDirectoryCreateFinished {
        void onDirectoryCreateFinished(String dirName);
    }
}
