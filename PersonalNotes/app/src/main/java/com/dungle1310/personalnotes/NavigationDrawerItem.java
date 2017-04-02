package com.dungle1310.personalnotes;

/**
 * Created by Dung on 1/16/2017.
 */

public class NavigationDrawerItem {
    private int iconId;
    private String title;

    public NavigationDrawerItem(int iconId, String title) {
        this.title = title;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public String getTitle() {
        return title;
    }
}
