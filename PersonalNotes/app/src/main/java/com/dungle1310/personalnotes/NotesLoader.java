package com.dungle1310.personalnotes;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 1/23/2017.
 */

public class NotesLoader extends AsyncTaskLoader<List<Note>> {
    private List<Note> mNotes;
    private ContentResolver mContentResolver;
    private Cursor mCursor;
    private int mType;   // reminder or note

    public NotesLoader(Context context, ContentResolver contentResolver, int type) {
        super(context);
        mContentResolver = contentResolver;
        mType = type;
    }

    @Override
    public List<Note> loadInBackground() {
        List<Note> entries = new ArrayList<>();
        String[] projection = {
                BaseColumns._ID,
                NotesContract.NoteColumns.NOTES_TITLE,
                NotesContract.NoteColumns.NOTES_DESCRIPTION,
                NotesContract.NoteColumns.NOTES_TYPE,
                NotesContract.NoteColumns.NOTES_DATE,
                NotesContract.NoteColumns.NOTES_TIME,
                NotesContract.NoteColumns.NOTES_IMAGE,
                NotesContract.NoteColumns.NOTES_IMAGE_STORAGE_SELECTION };

        Uri uri = NotesContract.URI_TABLE;
        mCursor = mContentResolver.query(uri, projection, null, null, BaseColumns._ID + " DESC");

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String date = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_DATE));
                    String time = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_TIME));
                    String type = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_TYPE));
                    String title = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_TITLE));
                    String description = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_DESCRIPTION));
                    String imagePath = mCursor.getString(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_IMAGE));
                    int imageSelection = mCursor.getInt(mCursor.getColumnIndex(NotesContract.NoteColumns.NOTES_DATE));
                    int _id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));

                    if (mType == BaseActivity.NOTES) {
                        if (time.equals(AppConstant.NO_TIME)) {
                            time = "";
                            Note note = new Note(title, description, date, time, _id, imageSelection, type);
                            if (!imagePath.equals(AppConstant.NO_IMAGE)) {
                                if (imageSelection == AppConstant.DEVICE_SELECTION) {
                                    note.setBitmap(imagePath);
                                } else {
                                    // Is a Google Drive or Dropbox image
                                    note.setImagePath(imagePath);
                                }
                            } else {
                                note.setImagePath(AppConstant.NO_IMAGE);
                            }
                            entries.add(note);
                        }
                    } else if (mType == BaseActivity.REMINDERS) {
                        if (!time.equals(AppConstant.NO_TIME)) {
                            Note note = new Note(title, description, date, time, _id, imageSelection, type);
                            if (!imagePath.equals(AppConstant.NO_IMAGE)) {
                                if (imageSelection == AppConstant.DEVICE_SELECTION) {
                                    note.setBitmap(imagePath);
                                } else {
                                    // Is a Google Drive or Dropbox image
                                    note.setImagePath(imagePath);
                                }
                            } else {
                                note.setImagePath(AppConstant.NO_IMAGE);
                            }
                            entries.add(note);
                        }

                    } else {
                        throw new IllegalArgumentException("Invalid type = " + mType);
                    }
                } while (mCursor.moveToNext());
            }
        }
        return entries;
    }

    @Override
    public void deliverResult(List<Note> notes) {
        if (isReset()) {
            if (notes != null) {
                releaseResources();
                return;
            }
        }

        List<Note> oldNotes = mNotes;
        mNotes = notes;

        if (isStarted()) {
            super.deliverResult(notes);
        }
        if (oldNotes != null && oldNotes != notes) {
            releaseResources();
        }
    }

    @Override
    protected void onStartLoading() {
        if (mNotes != null) {
            deliverResult(mNotes);
        }
        if (takeContentChanged()) {
            forceLoad();
        } else if (mNotes == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mNotes != null) {
            releaseResources();
            mNotes = null;
        }
    }

    @Override
    public void onCanceled(List<Note> notes) {
        super.onCanceled(notes);
        releaseResources();
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    public void releaseResources() {
        mCursor.close();
    }
}
