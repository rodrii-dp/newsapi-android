package com.rodrigo.newsapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FavoritesFragment extends Fragment implements NewsClickListener{
    private NewsService mNewsService;
    private User user;
    public FavoritesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("usuario");

        mNewsService = NewsService.get(getContext());

        List<News> newsList = mNewsService.getNewsByUser(user.getName());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_pf);
        RvAdapter rvAdapter = new RvAdapter(newsList, getContext(), this, user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rvAdapter);



        return view;
    }

    @Override
    public void onNewsClick(News clickedNews, User user) {
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra("news", clickedNews);
        intent.putExtra("usuario", user);
        startActivity(intent);
    }
}