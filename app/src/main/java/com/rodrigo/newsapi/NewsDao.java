package com.rodrigo.newsapi;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getSavedNews();

    @Insert
    void saveNews(News news);
}
