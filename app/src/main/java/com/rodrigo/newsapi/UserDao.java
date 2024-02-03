package com.rodrigo.newsapi;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import retrofit2.http.PUT;

@Dao
public interface UserDao {

    @Insert
    void saveUser(User user);

    @Query("SELECT * FROM users WHERE name = :username LIMIT 1")
    User getUserByName(String username);

    @Query("UPDATE users SET password = :newPassword WHERE name = :username")
    void updatePassword(String username, String newPassword);

    void updatePassword(String password);
}
