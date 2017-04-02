package com.dungle1310.personalnotes;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

/**
 * Created by Dung on 1/31/2017.
 */

public class GoogleDriveSelectionActivity extends BaseGoogleDriveActivity {
    public static final int REQUEST_CODE_OPENER = 1;
    private static DriveId mDriveId;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        super.onConnected(bundle);
        IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder()
                .setMimeType(new String[] {DriveFolder.MIME_TYPE})
                .setActivityTitle("Choose image storage")
                .build(getGoogleApiClient());
        try {
            startIntentSenderForResult(intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            // Error processing
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPENER:
                if (data != null && resultCode == RESULT_OK) {
                    mDriveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    if (mDriveId != null) {
                        AppSharedPreferences.storeGoogleDriveResourceId(getApplicationContext(), mDriveId.getResourceId());
                        BaseActivity.actAsNote();
                        startActivity(new Intent(GoogleDriveSelectionActivity.this, GoogleDriveDirectoryNameGetterActivity.class));
                        finish();
                        return;
                    } else {
                        startActivity(new Intent(GoogleDriveSelectionActivity.this, GoogleDriveSelectionActivity.class));
                        return;
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}
