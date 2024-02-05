package com.rodrigo.newsappapi;

import android.content.Context;

import androidx.room.Room;

import java.util.Collections;
import java.util.List;

public class NewsService {
    private static NewsService sNewsService;
    private NewsDao mNewsDao;
    private UserDao mUserDao;

    public NewsService(Context context) {
        Context appContext = context.getApplicationContext();
        NewsDatabase database =  Room.databaseBuilder(appContext, NewsDatabase.class, "news")
                .allowMainThreadQueries()
                .build();
        mNewsDao = database.getSavedNewsDao();
        mUserDao = database.getUserDao();
    }

    public static NewsService get(Context context) {
        if (sNewsService == null) {
            sNewsService = new NewsService(context);
        }

        return sNewsService;
    }

    public List<News> getNewsByUser(String userName) {
        User user = mUserDao.getUserByName(userName);

        if (user != null) {
            return mNewsDao.getNewsByUserId(user.getId());
        } else {
            return Collections.emptyList();
        }
    }

    public void saveNews(News news, String userName) {
        User user = mUserDao.getUserByName(userName);

        news.setUserId(user.getId());

        mNewsDao.saveNews(news);
    }
}
