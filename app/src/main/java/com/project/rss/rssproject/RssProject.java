package com.project.rss.rssproject;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by nico_ on 13/03/2018.
 */

public class RssProject extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
