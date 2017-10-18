package com.kkbox.sqa.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PlayMusicTest {
    private static final String APP_PACKAGE = "com.skysoft.kkbox.android";

    private static final int LAUNCH_TIMEOUT = 10000;
    private static final int TIMEOUT = 5000;

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

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

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testPlayMusicFromDiscovery() {
        // wait for app ready
        mDevice.wait(Until.hasObject(By.res(APP_PACKAGE, "button_login")), LAUNCH_TIMEOUT);

        // Login via Email
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login_with_email")), TIMEOUT).click();

        // Submit login form
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "text_uid")), TIMEOUT).setText("demo171018@gmail.com");
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "text_password")), TIMEOUT).setText("1234");
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();

        assertTrue(true);
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
}

