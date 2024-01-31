package com.rodrigo.newsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "5d25a6ea55ae4ff6b36235e3cdab69e7";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<List<News>> news = newsAPI.getAllNews("bitcoin", API_KEY);
        news.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                handleFailure(t);
            }
        });
    }

    private <T> void handleResponse(Response<T> response) {
        if (!response.isSuccessful()) {
            Log.d(TAG, "Problemas: código http: " + response.code());
        }

        /*T body = response.body();
        text.setText(body.toString());*/
    }

    private void handleFailure(Throwable t) {
        Log.d(TAG, "Problemas Graves: " + t.getLocalizedMessage());
    }
}
