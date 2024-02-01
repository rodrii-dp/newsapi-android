package com.rodrigo.newsapi;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {
    @SerializedName("articles")
    private List<News> articles;

    public List<News> getArticles() {
        return articles;
    }
}
