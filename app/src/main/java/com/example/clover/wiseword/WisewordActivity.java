package com.example.clover.wiseword;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.clover.DiaryActivity;
import com.example.clover.R;
import com.example.clover.WorryThrowActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class WisewordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    ViewPager2 viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiseword);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_wiseword);
        navigationView = findViewById(R.id.navigationview_wiseword);
        toolbar = findViewById(R.id.toolbar_wiseword);
        viewPager = findViewById(R.id.wiseword_viewpager);
        tabLayout = findViewById(R.id.wiseword_tab);
        WiseWordAdapter wiseWordAdapter = new WiseWordAdapter(this);
        viewPager.setAdapter(wiseWordAdapter);
        viewPager.setSaveEnabled(false);
        final ArrayList<String> tab_index = new ArrayList<String>(Arrays.asList("자신감", "행복", "희망", "사랑"));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tab_index.get(position))).attach();

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
                finish();
                break;

            case R.id.nav_diary:
                Intent intent2 = new Intent(WisewordActivity.this, DiaryActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_wiseword:
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(WisewordActivity.this, WorryThrowActivity.class);
                startActivity(intent3);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
