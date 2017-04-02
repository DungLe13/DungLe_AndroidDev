package com.dungle1310.personalnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

/**
 * Created by Dung on 1/31/2017.
 */

public class GoogleDriveDirectoryNameGetterActivity extends BaseGoogleDriveActivity {
    private final ResultCallback<DriveResource.MetadataResult> mMetadataCallback = new
            ResultCallback<DriveResource.MetadataResult>() {
                @Override
                public void onResult(@NonNull DriveResource.MetadataResult metadataResult) {
                    if (!metadataResult.getStatus().isSuccess()) {
                        showMessage("Problem trying to fetch metadata");
                        return;
                    }
                    Metadata metadata = metadataResult.getMetadata();
                    AppSharedPreferences.storeGoogleDriveUploadFileName(getApplicationContext(), metadata.getTitle());
                    startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, NotesActivity.class));
                    finish();
                }
            };

    private final ResultCallback<DriveApi.DriveIdResult> mIdCallback = new
            ResultCallback<DriveApi.DriveIdResult>() {
                @Override
                public void onResult(@NonNull DriveApi.DriveIdResult driveIdResult) {
                    if (!driveIdResult.getStatus().isSuccess()) {
                        showMessage("Cannot find driveID. Are you authorized to view this file?");
                        return;
                    }
                    DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), driveIdResult.getDriveId());
                    file.getDriveId().encodeToString();
                    file.getMetadata(getGoogleApiClient()).setResultCallback(mMetadataCallback);
                }
            };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            Drive.DriveApi.fetchDriveId(getGoogleApiClient(), AppSharedPreferences.getGoogleDriveResourceId(getApplicationContext()))
                    .setResultCallback(mIdCallback);
            AppSharedPreferences.setPersonalNotesPreference(getApplicationContext(), AppConstant.GOOGLE_DRIVE_SELECTION);
            AppSharedPreferences.isGoogleDriveAuthenticated(getApplicationContext(), true);
            showMessage("Image location set in Google Drive");
        } catch (IllegalArgumentException e) {
            showMessage("An error occured while selecting the folder. Please try again!");
            startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, GoogleDriveSelectionActivity.class));
            finish();
        } catch (IllegalStateException e) {
            showMessage("An error occured while selecting the folder. Please try again!");
            startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, GoogleDriveSelectionActivity.class));
            finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
