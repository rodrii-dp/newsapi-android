package com.rodrigo.newsapi;

import android.content.Context;

import androidx.room.Room;

public class UserService {
    private static UserService sUserService;
    private UserDao mUserDao;

    private UserService(Context context) {
        Context appContext = context.getApplicationContext();
        NewsDatabase database = Room.databaseBuilder(appContext, NewsDatabase.class, "news").allowMainThreadQueries().build();
        mUserDao = database.getUserDao();
    }

    public static UserService get(Context context) {
        if (sUserService == null) {
            sUserService = new UserService(context);
        }
        return sUserService;
    }

    public void saveUser(User user) {
        mUserDao.saveUser(user);
    }

    public void updatePassword(String username, String newPassword) {
        mUserDao.updatePassword(username, newPassword);
    }
    public User getUserbyName(String username){
        return mUserDao.getUserByName(username);
    }
}
