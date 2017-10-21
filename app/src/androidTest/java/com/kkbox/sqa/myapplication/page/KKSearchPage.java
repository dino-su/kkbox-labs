package com.kkbox.sqa.myapplication.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import static org.junit.Assert.assertTrue;

public class KKSearchPage extends KKPage {
    public static final BySelector SEARCH_FIELD = By.res(APP_PACKAGE, "search_src_text");
    public static final BySelector SEARCH_ARTIST = By.text("ARTIST");
    private static final BySelector SEARCH_SONG = By.text("SONG");

    public KKSearchPage(UiDevice device) {
        super(device);
    }

    public KKSearchPage search(String keyword) {
        this.device.wait(Until.findObject(SEARCH_FIELD), TIMEOUT).setText(keyword);
        this.device.pressEnter();

        return this;
    }

    public KKSearchPage checkTopResult(String expectedText) {
        BySelector selector = By.text(expectedText);

        assertTrue(this.device.wait(Until.hasObject(selector), TIMEOUT));

        return this;
    }

    public KKSearchPage checkArtistResult(String expectedText) {
        this.device.wait(Until.findObject(SEARCH_ARTIST), TIMEOUT).click();

        BySelector selector = By.text(expectedText);
        assertTrue(this.device.wait(Until.hasObject(selector), TIMEOUT));

        return this;
    }

    public KKSearchPage checkSongResult(String expectedText) {
        this.device.wait(Until.findObject(SEARCH_SONG), TIMEOUT).click();

        BySelector selector = By.text(expectedText);
        assertTrue(this.device.wait(Until.hasObject(selector), TIMEOUT));

        return this;
    }
}