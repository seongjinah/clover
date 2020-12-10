package com.example.clover;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class WorryThrowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String userEmail;

    ImageView trashcan;
    EditText et_worry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worrythrow);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_worrythrow);
        navigationView = findViewById(R.id.navigationview_worrythrow);
        toolbar = findViewById(R.id.toolbar_worrythrow);

        et_worry = (EditText)findViewById(R.id.edittext_worrythrow);
        trashcan = (ImageView)findViewById(R.id.trashcan);

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
                Intent intent3 = new Intent(WorryThrowActivity.this, DiaryActivity.class);
                intent3.putExtra("userEmail",userEmail);
                startActivity(intent3);
                finish();
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(WorryThrowActivity.this, WisewordActivity.class);
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
                Intent intent4 = new Intent(WorryThrowActivity.this,LoginActivity.class);
                intent4.putExtra("userEmail",userEmail);
                startActivity(intent4);
                finish();
                break;

            case R.id.nav_music:
                Intent intent5 = new Intent(WorryThrowActivity.this,MusicActivity.class);
                intent5.putExtra("userEmail",userEmail);
                startActivity(intent5);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void delete_worry(View v)
    {
        trashcan.setClickable(false);
        Animation ani= AnimationUtils.loadAnimation(this,R.anim.delete_text);
        et_worry.startAnimation(ani);

        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                et_worry.setText("");
                Animation ani2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.delete_text2);
                et_worry.startAnimation(ani2);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trashcan.setClickable(true);
                    }
                }, 8500);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
