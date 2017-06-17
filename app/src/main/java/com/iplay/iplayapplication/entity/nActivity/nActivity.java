package com.iplay.iplayapplication.entity.nActivity;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */

public class nActivity {

    public long authorId;

    public String authorName;

    public String content;

    public long createdAt;

    public boolean hasLiked;

    public long id;

    public int likes;

    public String location;

    public List<String> medisUrls;

    @Override
    public String toString() {
        return "nActivity{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", hasLiked=" + hasLiked +
                ", id=" + id +
                ", likes=" + likes +
                ", location='" + location + '\'' +
                ", medisUrls=" + medisUrls +
                '}';
    }
}
