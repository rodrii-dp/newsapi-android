package com.rodrigo.newsapi;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void saveUser(User user);

    @Query("SELECT * FROM users WHERE name = :username LIMIT 1")
    User getUserByName(String username);
}
