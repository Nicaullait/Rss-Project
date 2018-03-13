package com.project.rss.rssproject.tool;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by nico_ on 13/03/2018.
 * database class required with dbFlow library
 */

@Database(name = RssDatabase.NAME, version = RssDatabase.VERSION)
public class RssDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}
