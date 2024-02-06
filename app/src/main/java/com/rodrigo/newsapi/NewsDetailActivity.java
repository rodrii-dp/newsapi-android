package com.rodrigo.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView title, author, content;
    private ImageView imageView, button;
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

        button.setOnClickListener(v -> saveNews(news, user));
    }

    private void saveNews(News news, User user){
        boolean isRepeated = mNewsService.countNewsByUserIdAndTitle(user.getId(), news.getTitle()) >= 1;
        if (isRepeated){
            mNewsService.deleteNews(news);
            Toast.makeText(this, "Noticia eliminada de favoritos.", Toast.LENGTH_SHORT).show();
            button.setColorFilter(ContextCompat.getColor(this, R.color.darker_gray));
        } else {
            mNewsService.saveNews(news, user.getName());
            Toast.makeText(this, "¡Noticia guardada!", Toast.LENGTH_SHORT).show();
            button.setColorFilter(ContextCompat.getColor(this, R.color.yellow));
        }
    }
}