package com.example.clover;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;

public class DiarywriteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarywrite);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_diary);
        navigationView = findViewById(R.id.navigationview_diary);
        toolbar = findViewById(R.id.toolbar_diary);

        /*Navigation Drawer Menu*/
        //Hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigations_drawer_open, R.string.navigations_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(DiarywriteActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_diary:
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(DiarywriteActivity.this, WisewordActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(DiarywriteActivity.this, WorryThrowActivity.class);
                startActivity(intent3);
                break;

            case R.id.nav_music:
                Intent intent5 = new Intent(DiarywriteActivity.this,MusicActivity.class);
                //intent5.putExtra("userEmail",userEmail);
                startActivity(intent5);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}


