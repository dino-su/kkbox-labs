package com.kkbox.sqa.myapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import com.kkbox.sqa.myapplication.page.KKPage;
import com.kkbox.sqa.myapplication.util.TestBuilder;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    private static final String APP_PACKAGE = "com.skysoft.kkbox.android";
    private static final int TIMEOUT = 5000;
    private static KKPage kkbox;

    private static UiDevice mDevice;

    @BeforeClass
    public static void login() {
        kkbox = new TestBuilder()
                .loginViaEmail("demo171018@gmail.com", "1234")
                .disableTrialMessage()
                .disableTutorial()
                .launch();
    }

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

       kkbox = new TestBuilder().launch();
    }

    @Test
    public void testSearch() {
        // TODO
        // kkbox.openSearch().search("linkin park").checkTopResult("Linkin Park (聯合公園)");

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
        // TODO
        // kkbox.openSearch().search("linkin park").checkArtistResult("Linkin Park (聯合公園)");

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
        // TODO
        // kkbox.openSearch().search("numb encore").checkSongResult("Numb/Encore");

        // open search
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "menu_global_search")), TIMEOUT).click();

        // search artist "linkin park"
        mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT).setText("numb encore");
        mDevice.pressEnter();
        mDevice.wait(Until.findObject(By.text("SONG")), TIMEOUT).click();

        // check search result
        assertTrue(mDevice.wait(Until.hasObject(By.text("Numb/Encore")), TIMEOUT));
    }
}