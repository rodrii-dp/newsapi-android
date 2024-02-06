package com.rodrigo.newsapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements NewsClickListener{

    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "5d25a6ea55ae4ff6b36235e3cdab69e7";
    private static final String TAG = "NEWS API";
    private User user;
    private EditText buscador, limite;
    private Button btFiltro;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("usuario");

        limite = view.findViewById(R.id.limite);
        buscador = view.findViewById(R.id.buscador);
        btFiltro = view.findViewById(R.id.btFiltro);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        btFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBuscador = buscador.getText().toString();
                int intLimit = Integer.parseInt(limite.getText().toString());
                Call<NewsResponse> newsResponseCall = newsAPI.getAllNews(strBuscador, API_KEY, intLimit);
                newsResponseCall.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            NewsResponse newsResponse = response.body();
                            if (newsResponse != null) {
                                List<News> newsList = newsResponse.getArticles();
                                if (newsList != null && !newsList.isEmpty()) {
                                    RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(new RvAdapter(newsList, requireContext(), HomeFragment.this, user));
                                }
                            }
                        } else {
                            Log.i(TAG, "onResponse: Errores graves" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        Log.i(TAG, "onFailure: Errores graves");
                    }
                });
            }
        });
        Call<NewsResponse> newsResponseCall = newsAPI.getTopHeadlines("us", API_KEY);

        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    if (newsResponse != null) {
                        List<News> newsList = newsResponse.getArticles();
                        if (newsList != null && !newsList.isEmpty()) {
                            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(new RvAdapter(newsList, requireContext(), HomeFragment.this, user));
                        }
                    }
                } else {
                    Log.i(TAG, "onResponse: Errores graves" + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: Errores graves");
            }
        });
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