package com.kkbox.sqa.myapplication.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

public class KKLoginPage extends KKPage {
    public static final BySelector LOGIN_BUTTON = By.res(APP_PACKAGE, "button_login");

    public KKLoginPage(UiDevice device) {
        super(device);

        this.device.wait(Until.hasObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT);
    }

    public KKPage loginViaEmail(String username, String password) {
        // click Email login
        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();
        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "button_login_with_email")), TIMEOUT).click();

        // Submit login form
        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "text_uid")), TIMEOUT).setText(username);
        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "text_password")), TIMEOUT).setText(password);
        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();

        return new KKPage(this.device);
    }
}