package com.dungle1310.personalnotes;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Dung on 1/29/2017.
 */

public class HelpFeedActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_feedback_layout);
        mToolBar = activateToolbar();
        setUpNavigationDrawer();
    }
}
