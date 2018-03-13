package com.project.rss.rssproject.model;

import com.project.rss.rssproject.tool.RssDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by nico_ on 06/03/2018.
 */

@Table(database = RssDatabase.class)
public class Deal extends BaseModel implements Serializable {

    @PrimaryKey
    private String title;

    @Column
    private String categorie;

    @Column
    private String imageUrl;

    @Column
    private String description;

    @Column
    private long timeStamp;

    public Deal(){
        title = "";
        categorie = "";
        imageUrl = "";
        description = "";
    }

    public Deal(String title, String categorie, String imageUrl, String description, long timeStamp) {
        this.title = title;
        this.categorie = categorie;
        this.imageUrl = imageUrl;
        this.description = description;
        this.timeStamp = timeStamp;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
