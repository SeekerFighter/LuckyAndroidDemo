package com.seeker.lucky.entiy;

import android.content.Intent;

/**
 * @author Seeker
 * @date 2019/1/9/009  15:32
 */
public class DemoPager {

    private String demoTitle;

    private Intent intent;

    public DemoPager(String demoTitle, Intent intent) {
        this.demoTitle = demoTitle;
        this.intent = intent;
    }

    public String getDemoTitle() {
        return demoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        this.demoTitle = demoTitle;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
