package com.example.android.theguardian;

import com.squareup.picasso.Picasso;

/**
 * Created by moham on 4/22/2019.
 */

public class News {
    private String title,authorName,date,section,thumbnail,url;
    public News(String title,String authorName,String date,String section,String thumbnail,String url){
        this.title=title;
        this.authorName=authorName;
        this.date=date;
        this.section=section;
        this.thumbnail=thumbnail;
        this.url=url;

    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
