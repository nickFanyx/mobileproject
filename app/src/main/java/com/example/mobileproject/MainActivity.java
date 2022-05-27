package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobileproject.fragment.fragment_about;
import com.example.mobileproject.fragment.fragment_home;
import com.example.mobileproject.fragment.fragment_help;
import com.example.mobileproject.fragment.fragment_order_history;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private String selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            selected = "Category";
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();

                break;
            case R.id.nav_shopping_cart:

                startActivity(new Intent(MainActivity.this, ShoppingCart.class));
                break;
            case R.id.nav_order_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_order_history()).commit();
                break;
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
            case R.id.nav_help:
                //startActivity(new Intent(Home.this,popup.class ));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_help()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }
}