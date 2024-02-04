package com.rodrigo.newsappapi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao getSavedNewsDao();
    public abstract UserDao getUserDao();
}
