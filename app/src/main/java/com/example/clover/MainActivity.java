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
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.clover.text_animation.Typewriter;
import com.example.clover.wiseword.Saying;
import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String userEmail;
    Button button;
    Typewriter random_wiseword;
    String str_wiseword;
    int random1;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();
    ArrayList<String> wiseword_category = new ArrayList<String>(Arrays.asList("자신감", "행복", "희망", "사랑"));

    SharedPreferences autologin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar_main);

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

        /*Button*/
        button = findViewById(R.id.button_main);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WisewordActivity.class);
                intent.putExtra("userEmail",userEmail);
                startActivity(intent);
            }
        });

        random_wiseword = (Typewriter)findViewById(R.id.random_wiseword);
        random_wiseword.setClickable(false);
        set_text();

        random_wiseword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random_wiseword.setClickable(false);
                set_text();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                break;

            case R.id.nav_diary:
                Intent intent1 = new Intent(MainActivity.this, DiaryActivity.class);
                intent1.putExtra("userEmail",userEmail);
                startActivity(intent1);
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(MainActivity.this, WisewordActivity.class);
                intent2.putExtra("userEmail",userEmail);
                startActivity(intent2);
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(MainActivity.this, WorryThrowActivity.class);
                intent3.putExtra("userEmail",userEmail);
                startActivity(intent3);
                break;

            case R.id.nav_logout:
                autologin =getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autologin_editor=autologin.edit();
                autologin_editor.clear();
                autologin_editor.commit();
                mAuth.signOut();
                Intent music = new Intent(this,MusicService.class);
                stopService(music);
                Intent intent4 = new Intent(MainActivity.this,LoginActivity.class);
                intent4.putExtra("userEmail",userEmail);
                startActivity(intent4);
                finish();
                break;

            case R.id.nav_music:
                Intent intent5 = new Intent(MainActivity.this,MusicActivity.class);
                intent5.putExtra("userEmail",userEmail);
                startActivity(intent5);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void set_text(){
        int random = (int)(Math.random()*4);
        String str_category = wiseword_category.get(random);
        mDatabaseReference.child("Wise_Saying").child(str_category).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long size = snapshot.getChildrenCount();
                random1 = (int)(Math.random()*size);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        mDatabaseReference.child("Wise_Saying").child(str_category).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long size = snapshot.getChildrenCount();
                int random1 = (int)(Math.random()*size);
                Saying tmp = snapshot.child(String.valueOf(random1)).getValue(Saying.class);
                str_wiseword = tmp.getSaying();
                random_wiseword.setCharacterDelay(150);
                random_wiseword.animateText(str_wiseword);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        random_wiseword.setClickable(true);
                    }
                }, 150*str_wiseword.length()+1000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }
}