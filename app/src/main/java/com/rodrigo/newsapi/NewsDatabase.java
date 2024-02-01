package com.rodrigo.newsapi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao getSavedNewsDao();
}
