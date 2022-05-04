package com.example.foodex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.foodex.fragments.FavoritesFragment;
import com.example.foodex.fragments.HomeFragment;
import com.example.foodex.fragments.PantryFragment;
import com.example.foodex.fragments.ProfileFragment;
import com.example.foodex.fragments.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity{

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.nav_pantry:
                    selectedFragment = new PantryFragment();
                    break;

                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_favorites:
                    selectedFragment = new FavoritesFragment();
                    break;

                case R.id.nav_shopping_list:
                    selectedFragment = new ShoppingListFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


//    @Override
//    public void onClick(View view) {
//        Fragment selectedFragment = null;
//        switch (view.getId()) {
//            case R.id.nav_pantry:
//                selectedFragment = new PantryFragment();
//                break;
//
//            case R.id.nav_home:
//                selectedFragment = new HomeFragment();
//                break;
//
//            case R.id.nav_favorites:
//                selectedFragment = new FavoritesFragment();
//                break;
//
//            case R.id.nav_shopping_list:
//                selectedFragment = new ShoppingListFragment();
//                break;
//        }
//        if (selectedFragment != null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
//        }
//    }

}