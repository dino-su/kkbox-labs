package com.kkbox.sqa.myapplication;

import android.support.test.runner.AndroidJUnit4;

import com.kkbox.sqa.myapplication.page.KKPage;
import com.kkbox.sqa.myapplication.page.KKSearchPage;
import com.kkbox.sqa.myapplication.util.TestBuilder;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    private static KKPage kkbox;

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
       kkbox = new TestBuilder().launch();
    }

    @Test
    public void testSearch() {
        kkbox.is(KKPage.class).openSearch()
             .is(KKSearchPage.class).search("linkin park")
             .is(KKSearchPage.class).checkTopResult("Linkin Park (聯合公園)");
    }

    @Test
    public void testSearchArtist() {
        kkbox.is(KKPage.class).openSearch()
             .is(KKSearchPage.class).search("linkin park")
             .is(KKSearchPage.class).checkArtistResult("Linkin Park (聯合公園)");
    }

    @Test
    public void testSearchSong() {
        kkbox.is(KKPage.class).openSearch()
             .is(KKSearchPage.class).search("numb encore")
             .is(KKSearchPage.class).checkSongResult("Numb/Encore");
    }
}