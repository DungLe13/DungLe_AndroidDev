package com.dungle1310.personalnotes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 1/29/2017.
 */

public class DropBoxDirectoryListenerAsync extends AsyncTask<Void, Long, Boolean> {

    private Context mContext;
    private DropboxAPI<?> mAPI;
    private List<String> mDirectories = new ArrayList<>();
    private String mErrorMessage;
    private String mCurrentDirectory;
    private OnLoadFinished mListener;

    public DropBoxDirectoryListenerAsync(Context context, DropboxAPI<?> API, String currentDirectory, OnLoadFinished listener) {
        mContext = context;
        mAPI = API;
        mCurrentDirectory = currentDirectory;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            mErrorMessage = null;
            DropboxAPI.Entry directoryEntry = mAPI.metadata(mCurrentDirectory, 1000, null, true, null);
            if (!directoryEntry.isDir || directoryEntry.contents == null) {
                mErrorMessage = "File or Empty Directory";
                return false;
            }

            for (DropboxAPI.Entry entry : directoryEntry.contents) {
                if (entry.isDir) {
                    mDirectories.add(entry.fileName());
                }
            }
        } catch (DropboxUnlinkedException e) {
            mErrorMessage = "Authentication dropbox error";
        } catch (DropboxPartialFileException e) {
            mErrorMessage = "DownloadCancelled";
        } catch (DropboxServerException e) {
            mErrorMessage = "Network Error, try again!";
        } catch (DropboxParseException e) {
            mErrorMessage = "Dropbox Parse Exception, try again";
        } catch (DropboxException e) {
            mErrorMessage = "Unknown Error";
        }

        if (mErrorMessage != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            mListener.onLoadFinished(mDirectories);
        } else {
            showToast(mErrorMessage);
        }
    }

    public void showToast(String message) {
        Toast error = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        error.show();
    }

    public interface OnLoadFinished {
        void onLoadFinished(List<String> values);
    }
}
