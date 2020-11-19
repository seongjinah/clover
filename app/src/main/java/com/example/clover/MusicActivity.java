package com.example.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MusicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String userEmail;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_music);
        navigationView = findViewById(R.id.navigationview_music);
        toolbar = findViewById(R.id.toolbar_music);

        Intent it = getIntent();
        userEmail = it.getStringExtra("userEmail");

        /*Navigation Drawer Menu*/
        //Hide or show item
        Menu menu = navigationView.getMenu();

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
                Intent intent3 = new Intent(MusicActivity.this, DiaryActivity.class);
                intent3.putExtra("userEmail",userEmail);
                startActivity(intent3);
                finish();
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(MusicActivity.this, WisewordActivity.class);
                intent2.putExtra("userEmail",userEmail);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_wrongthrow:
                break;

            case R.id.nav_logout:
                SharedPreferences autologin =getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autologin_editor=autologin.edit();
                autologin_editor.clear();
                autologin_editor.commit();
                Intent music = new Intent(this,MusicService.class);
                stopService(music);
                mAuth.signOut();
                Intent intent4 = new Intent(MusicActivity.this,LoginActivity.class);
                intent4.putExtra("userEmail",userEmail);
                startActivity(intent4);
                finish();
                break;

            case R.id.nav_music:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void MusicStart(View view){
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
    }
    public void MusicStop(View view) {
        Intent intent = new Intent(this,MusicService.class);
        stopService(intent);
    }
}
