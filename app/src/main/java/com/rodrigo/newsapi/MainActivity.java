package com.rodrigo.newsapi;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private int home;
    private int saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("usuario");
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", user);
        replaceHomeFragment(bundle);

        home = R.id.action_dashboard;
        saved = R.id.action_favorites;

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == home) {
                replaceHomeFragment(bundle);
                return true;
            } else if (itemId == saved) {
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                favoritesFragment.setArguments(bundle);
                replaceFragment(favoritesFragment);
                return true;
            } else {
                return false;
            }
        });

    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void replaceHomeFragment(Bundle bundle) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        replaceFragment(homeFragment);
    }
}
