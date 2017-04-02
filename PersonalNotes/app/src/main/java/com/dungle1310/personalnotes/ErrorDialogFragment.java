package com.dungle1310.personalnotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Dung on 1/24/2017.
 */

public class ErrorDialogFragment extends DialogFragment {
    public ErrorDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int errorCode = getArguments().getInt(AppConstant.DIALOG_ERROR);
        return GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(), errorCode);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        getActivity().finish();
    }
}
