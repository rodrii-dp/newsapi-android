package com.rodrigo.newsapi;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class NewsService {
    private static NewsService sUserService;
    private NewsDao mUserDao;

    public NewsService(Context context) {
        Context appContext = context.getApplicationContext();
        NewsDatabase database =  Room.databaseBuilder(appContext, NewsDatabase.class, "news").allowMainThreadQueries().build();
        mUserDao = database.getSavedNewsDao();
    }

    public static NewsService get(Context context) {
        if (sUserService == null) {
            sUserService = new NewsService(context);
        }

        return sUserService;
    }

    public List<News> getUser(String name) {
        return mUserDao.getSavedNews();
    }

    public void saveNews(News news) {
        mUserDao.saveNews(news);
    }
}

