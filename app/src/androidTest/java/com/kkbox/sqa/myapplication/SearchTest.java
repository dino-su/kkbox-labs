package com.kkbox.sqa.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.kkbox.sqa.myapplication.page.KKPage;
import com.kkbox.sqa.myapplication.util.TestBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    private static final String APP_PACKAGE = "com.skysoft.kkbox.android";
    private static final int LAUNCH_TIMEOUT = 10000;
    private static final int TIMEOUT = 5000;
    private static KKPage kkbox;

    private static UiDevice mDevice;

    @BeforeClass
    public static void login() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // TODO: Uncomment below to using TestBuilder
        // kkbox = new TestBuilder()
        //        .loginViaEmail("demo171018@gmail.com", "1234")
        //        .disableTrialMessage()
        //        .disableTutorial()
        //        .launch();

        // FIXME: Refactor following to TestBuilder
        // Trial Message Handler
        registerTrialWatchers(mDevice);

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

        // Auto login
        if(mDevice.wait(Until.hasObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT)) {
            // Login via Email
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login_with_email")), TIMEOUT).click();

            // Submit login form
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "text_uid")), TIMEOUT).setText("demo171018@gmail.com");
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "text_password")), TIMEOUT).setText("1234");
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "button_login")), TIMEOUT).click();

            // Dismiss Drawer
            mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), LAUNCH_TIMEOUT).click();
        }

        // Open Search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), LAUNCH_TIMEOUT).click();

        // Dismiss Tutorial
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_music_recognition")), TIMEOUT).click();
    }

    @Before
    public void setup() {
        // TODO Uncomment below to using TestBuilder
        // kkbox = new TestBuilder().launch();

        // FIXME: Refactor following to TestBuilder
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
    public void testSearch() {
        // open search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), TIMEOUT).click();

        // search "linkin park"
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT).setText("linkin park");
        mDevice.pressEnter();

        // check Top result
        assertTrue(mDevice.wait(Until.hasObject(By.text("Linkin Park (聯合公園)")), TIMEOUT));
    }

    @Test
    public void testSearchArtist() {
        // open search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), TIMEOUT).click();

        // search artist "linkin park"
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT).setText("linkin park");
        mDevice.pressEnter();
        mDevice.wait(Until.findObject(By.text("ARTIST")), TIMEOUT).click();

        // check search result
        assertTrue(mDevice.wait(Until.hasObject(By.text("Linkin Park (聯合公園)")), TIMEOUT));
    }

    @Test
    public void testSearchSong() {
        // open search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), TIMEOUT).click();

        // search artist "linkin park"
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT).setText("numb encore");
        mDevice.pressEnter();
        mDevice.wait(Until.findObject(By.text("SONG")), TIMEOUT).click();

        // check search result
        assertTrue(mDevice.wait(Until.hasObject(By.text("Numb/Encore")), TIMEOUT));
    }

    /**
     *
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private static String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    private static void registerTrialWatchers(UiDevice device){
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
                        // do nothing
                    }

                    return true;
                }

                return false;
            }
        });
    }
}