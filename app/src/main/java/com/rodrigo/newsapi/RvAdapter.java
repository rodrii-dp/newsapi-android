package com.rodrigo.newsapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context context;
    private NewsClickListener newsClickListener;
    private User currentUser;


    public RvAdapter(List<News> newsList, Context context, NewsClickListener newsClickListener, User user) {
        this.newsList = newsList;
        this.context = context;
        this.newsClickListener = newsClickListener;
        this.currentUser = user;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.setTitle(news.getTitle());
        holder.setAuthor(news.getAuthor());
        holder.setImage(news.getUrlToImage());
        holder.itemView.setOnClickListener(v -> {
            if (newsClickListener != null) {
                newsClickListener.onNewsClick(news, currentUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        TextView title;

        TextView author;

        TextView description;

        public void setImage(String imageUrl) {
            Glide.with(context).load(imageUrl).into(imagen);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }


        public void setAuthor(String author) {this.author.setText(author);
        }


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            title = itemView.findViewById(R.id.titulo);
            author = itemView.findViewById(R.id.autor);


        }
    }
}

