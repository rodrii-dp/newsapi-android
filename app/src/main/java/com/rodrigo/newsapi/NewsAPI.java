package com.rodrigo.newsapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("v2/everything")
    Call<List<News>> getAllNews(@Query("q") String query, @Query("apiKey") String apiKey);
}
