package com.kkbox.sqa.myapplication.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

public class KKPage {
    protected static final String APP_PACKAGE = "com.skysoft.kkbox.android";
    protected static final int LAUNCH_TIMEOUT = 10000;
    protected static final int TIMEOUT = 5000;
    protected UiDevice device;

    // UI Selector
    public static final BySelector SEARCH_BUTTON = By.res(APP_PACKAGE, "menu_global_search");

    public KKPage(UiDevice device) {
        this.device = device;

        // Wait for the app to appear
        this.device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    public KKSearchPage openSearch() {
        this.device.wait(Until.findObject(SEARCH_BUTTON), TIMEOUT).click();

        return new KKSearchPage(this.device);
    }
}