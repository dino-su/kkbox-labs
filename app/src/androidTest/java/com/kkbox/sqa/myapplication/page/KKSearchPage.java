package com.kkbox.sqa.myapplication.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import static org.junit.Assert.assertTrue;

public class KKSearchPage extends KKPage{

    public KKSearchPage(UiDevice device) {
        super(device);

        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT);
    }

    public KKSearchPage search(String query){

        this.device.wait(Until.findObject(By.res(APP_PACKAGE, "search_src_text")), TIMEOUT).setText(query);
        this.device.pressEnter();

        return this;
    }

    public void checkTopResult(String expected){
        assertTrue(this.device.wait(Until.hasObject(By.text(expected)), TIMEOUT));
    }

    public KKSearchPage clickTopBarElement(String string) {
        this.device.wait(Until.findObject(By.text(string)), TIMEOUT).click();
        return this;
    }
}
