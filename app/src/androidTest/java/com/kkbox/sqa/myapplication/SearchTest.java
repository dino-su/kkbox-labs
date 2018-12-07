package com.kkbox.sqa.myapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.kkbox.sqa.myapplication.page.KKPage;
import com.kkbox.sqa.myapplication.util.TestBuilder;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SearchTest {
    private static KKPage kkbox;

    private static UiDevice mDevice;

    @BeforeClass
    public static void login() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // TODO: Uncomment below to using TestBuilder
        kkbox = new TestBuilder()
                .loginViaEmail("demo171018@gmail.com", "1234")
                .disableTrialMessage()
                .disableTutorial()
                .launch();

    }

    @Before
    public void setup() {
        // TODO Uncomment below to using TestBuilder
        kkbox = new TestBuilder().launch();

    }

    @Test
    public void testSearch() {
        kkbox.openSearch()
                .search("linkin park")
                .checkTopResult("Linkin Park (聯合公園)");
    }

    @Test
    public void testSearchArtist() {
        kkbox.openSearch()
                .search("linkin park")
                .clickTopBarElement("歌手")
                .checkTopResult("Linkin Park (聯合公園)");
    }

    @Test
    public void testSearchSong() {
        kkbox.openSearch()
                .search("numb encore")
                .clickTopBarElement("歌曲")
                .checkTopResult("Numb/Encore");
    }
}