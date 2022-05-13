package com.example.foodex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodex.R;
import com.example.foodex.ui.fragments.FavoritesFragment;
import com.example.foodex.ui.fragments.HomeFragment;
import com.example.foodex.ui.fragments.PantryFragment;
import com.example.foodex.ui.fragments.ProfileFragment;
import com.example.foodex.ui.fragments.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    private final HomeFragment homeFragment = new HomeFragment();
    private final PantryFragment pantryFragment = new PantryFragment();
    private final FavoritesFragment favoritesFragment = new FavoritesFragment();
    private final ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = homeFragment;

        switch (item.getItemId())
        {
            case R.id.nav_pantry:
                selectedFragment = pantryFragment;
                break;

            case R.id.nav_home:
                selectedFragment = homeFragment;
                break;

            case R.id.nav_favorites:
                selectedFragment = favoritesFragment;
                break;

            case R.id.nav_shopping_list:
                selectedFragment = shoppingListFragment;
                break;

            case R.id.nav_profile:
                selectedFragment = profileFragment;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };


}
