package com.rodrigo.newsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity {

    private Button button;
    private TextView title, author, content;
    private ImageView imageView;
    private NewsService mNewsService;
    private UserService mUserService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("news");
        User user = (User) intent.getSerializableExtra("usuario");

        mNewsService = NewsService.get(this);
        mUserService = UserService.get(this);


        title = findViewById(R.id.titleDetail);
        author = findViewById(R.id.authorDetail);
        content = findViewById(R.id.contentDetail);
        imageView = findViewById(R.id.imageDetail);
        button = findViewById(R.id.btSaveNews);

        title.setText(news.getTitle());
        author.setText(news.getAuthor());
        content.setText(news.getContent());
        Glide.with(this).load(news.getUrlToImage()).into(imageView);

        button.setOnClickListener(v -> saveNews(news, user.getName()));
    }

    private void saveNews(News news, String username){
        mNewsService.saveNews(news, username);
    }
}