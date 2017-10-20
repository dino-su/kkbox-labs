package com.kkbox.sqa.myapplication.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.kkbox.sqa.myapplication.page.KKLoginPage;
import com.kkbox.sqa.myapplication.page.KKPage;

import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class TestBuilder  {
    private final String APP_PACKAGE = "com.skysoft.kkbox.android";
    private final int LAUNCH_TIMEOUT = 10000;
    private final int TIMEOUT = 5000;

    private UiDevice mDevice;
    private String mUsername;
    private String mPassword;
    private Boolean mRequireLogin;
    private Boolean mHasLogin;

    public TestBuilder() {
        // init
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mRequireLogin = false;
        mHasLogin = false;
    }

    public TestBuilder loginViaEmail(String username, String password) {
        mUsername = username;
        mPassword = password;
        mRequireLogin = true;

        return this;
    }

    public TestBuilder disableTrialMessage() {
        // Trial Message Handler
        registerTrialWatchers(mDevice);

        return this;
    }

    public TestBuilder disableTutorial() {
        // Open Search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), LAUNCH_TIMEOUT).click();

        // Dismiss Search Tutorial
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_music_recognition")), TIMEOUT).click();

        return this;
    }

    public KKPage launch() {
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // check login status
        mHasLogin = !mDevice.wait(Until.hasObject(KKLoginPage.LOGIN_BUTTON), TIMEOUT);

        if(mRequireLogin == true && mHasLogin == false) {
            new KKLoginPage(mDevice).loginViaEmail(mUsername, mPassword);
        }

        return new KKPage(mDevice);
    }

    /**
     *
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    private void registerTrialWatchers(UiDevice device){
        device.registerWatcher("Trial", new UiWatcher() {
            @Override
            public boolean checkForCondition() {

                UiObject window = new UiObject(new UiSelector().packageName(APP_PACKAGE)
                        .resourceId("android:id/message"));

                if (window.exists()) {
                    UiObject buttonOK = new UiObject(new UiSelector().packageName(APP_PACKAGE)
                            .resourceId("android:id/button1").enabled(true));

                    // sometimes it takes a while for the OK button to become enabled
                    buttonOK.waitForExists(5000);

                    try {
                        buttonOK.click();
                    } catch (UiObjectNotFoundException e) {
                        Assert.fail(e.getMessage());
                    }

                    return true;
                }

                return false;
            }
        });
    }
}
