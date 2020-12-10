package com.example.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.clover.MusicService.MyBinder;

public class MusicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String userEmail;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView musicName;
    MusicService ms;
    boolean isService = false;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    DatabaseReference mDbRef = mDb.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        musicName=(TextView)findViewById(R.id.music_text);
        musicName.setSelected(true);

        Music_connect();

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

        CheckMusic cm = new CheckMusic();
        cm.start();
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

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBinder mb = (MyBinder) iBinder;
            ms = mb.getService();
            isService=true;
            Log.d("service connection","complete");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isService = false;
        }
    };

    public void Music_connect(){
        Intent music_intent = new Intent(MusicActivity.this,MusicService.class);
        bindService(music_intent, conn, Context.BIND_AUTO_CREATE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Music_name_update();
            }
        }, 1000);
    }

    public void Music_name_update(){
        if(!isService){
            Toast.makeText(getApplicationContext(),
                    "서비스중이 아닙니다, 데이터받을수 없음",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else {
            int num = ms.getNum();
            Log.d("Number", "" + num);
            mDbRef.child("Music").child(Integer.toString(num + 1)).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String music_name = (String) snapshot.getValue();
                    Log.d("Music name", music_name);
                    musicName.setText(music_name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void MusicStart(View view){
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);

        Music_connect();
    }
    public void MusicStop(View view) {
        if(isService) {
            unbindService(conn);
        }
        isService =  false;
        Intent intent = new Intent(this,MusicService.class);
        stopService(intent);
    }

    class CheckMusic extends Thread{
        public void run() {
            while (true) {
                try{
                    Thread.sleep(1000);
                    if (ms.isChangeindex()) {
                        Music_name_update();
                    }
                }
                catch (InterruptedException e){

                }
            }
        }
    }

    public void Music1(View view){
        ms.setNum(0);
        Log.d("musiclist1","gg");
    }
    public void Music2(View view){
        ms.setNum(1);
    }
    public void Music3(View view){
        ms.setNum(2);
    }
}
