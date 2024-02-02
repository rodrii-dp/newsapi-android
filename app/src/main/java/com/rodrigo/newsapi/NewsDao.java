package com.rodrigo.newsapi;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getSavedNews();

    @Query("SELECT * FROM news WHERE title = :title")
    List<News> getNewsByTitle(String title);

    @Insert
    void saveNews(News news);

    @Delete
    void deleteNews(News news);
}
