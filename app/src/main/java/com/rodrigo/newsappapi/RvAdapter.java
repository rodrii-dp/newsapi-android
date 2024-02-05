package com.rodrigo.newsappapi;

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

    public RvAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
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
        holder.setDescription(news.getDescription());
        holder.setImage(news.getImageUrl());
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

        public void setImage(String imageUrl ) {
            Glide.with(context).load(imageUrl).into(imagen);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }


        public void setAuthor(String author) {this.author.setText(author);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            title = itemView.findViewById(R.id.titulo);
            author = itemView.findViewById(R.id.autor);
            description = itemView.findViewById(R.id.descripcion);


        }
    }
}
