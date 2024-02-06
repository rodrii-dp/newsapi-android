package com.rodrigo.newsapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("v2/everything")
    Call<NewsResponse> getAllNews(@Query("q") String query, @Query("apiKey") String apiKey, @Query("pageSize") int pageSize);
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String query, @Query("apiKey") String apiKey);
}
