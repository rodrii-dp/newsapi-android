package com.rodrigo.newsappapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
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
    private TextView tvResult;
    private  TextView tvResult2;

    private NewsService mNewsService;
    private UserService mUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        tvResult2 = findViewById(R.id.tv_result2);
        mNewsService = NewsService.get(this);
        mUserService = UserService.get(this);

        User user = new User("rodrigo", "micontrasena");
        mUserService.insertUser(user);

        News news1 = new News("Autor1", "Título1", "Descripción1", "Contenido1", "https://imagen1.com");
        News news2 = new News("Autor2", "Título2", "Descripción2", "Contenido2", "https://imagen2.com");

        mNewsService.saveNews(news1, "rodrigo");
        mNewsService.saveNews(news2, "rodrigo");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> newsCall = newsAPI.getAllNews("bitcoin", API_KEY);
        // mNewsService.saveNews(new News("a", "b", "c"));

        newsCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                try {
                    handleResponse(response);
                    displaySavedNewsForUser("rodrigo");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                handleFailure(t);
            }
        });
    }

    private void handleResponse(Response<NewsResponse> response) throws IOException {
        if (response.isSuccessful()) {
            NewsResponse newsResponse = response.body();
            if (newsResponse != null) {
                List<News> newsList = newsResponse.getArticles();
                if (newsList != null && !newsList.isEmpty()) {
                    StringBuilder displayText = new StringBuilder();
                    displayText.append(newsList.get(0).getTitle());
                    for (News news : newsList) {
                        displayText.append(news.getTitle()).append("\n");
                    }
                    //tvResult.setText(displayText.toString());
                }
            }
        } else {
            Log.d(TAG, "Error en la respuesta: " + response.errorBody().string());
        }
    }

    private void handleFailure(Throwable t) {
        Log.d(TAG, "Problemas Graves: " + t.getLocalizedMessage());
    }

    private void displaySavedNewsForUser(String userName) {
        List<News> savedNews = mNewsService.getNewsByUser(userName);
        if (savedNews != null && !savedNews.isEmpty()) {
            StringBuilder displayText = new StringBuilder("Noticias Guardadas:\n");
            for (News news : savedNews) {
                displayText.append(news.getTitle()).append("\n");
            }
            tvResult2.setText(displayText.toString());
        } else {
            tvResult2.setText("No hay noticias guardadas para este usuario.");
        }
    }
}
