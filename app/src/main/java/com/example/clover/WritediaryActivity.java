package com.example.clover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritediaryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    String str_date;
    String str_title;
    String str_write;

    EditText et_title;
    EditText et_date;
    EditText et_write;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writediary);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_diary);
        navigationView = findViewById(R.id.navigationview_diary);
        toolbar = findViewById(R.id.toolbar_diary);

        /*Tool Bar*/
        setSupportActionBar(toolbar);

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

        /*Edit text*/
        et_title = (EditText)findViewById(R.id.edittext_title);
        et_date = (EditText)findViewById(R.id.edittext_date);
        et_write = (EditText)findViewById(R.id.edittext_write);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        str_date = simpleDate.format(mDate);
        et_date.setText(str_date);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(WritediaryActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_diary:
                Intent intent1 = new Intent(WritediaryActivity.this, DiaryActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(WritediaryActivity.this, WisewordActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(WritediaryActivity.this, WorryThrowActivity.class);
                startActivity(intent3);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public class diary_data{
        public String id;
        public String title;
        public String date;
        public String write;

        public diary_data(String id, String title,String date,String write){
            this.id = id;
            this.title = title;
            this.date = date;
            this.write = write;
        }
    }

    public void register(View v){

        final String str_title = et_title.getText().toString().trim();
        final String str_date = et_date.getText().toString().trim();
        final String str_write = et_write.getText().toString().trim();

        if(str_title.equals("")){
            Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_date.equals("")){
            Toast.makeText(this, "날짜를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_write.equals("")){
            Toast.makeText(this, "글을 남겨주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long now1 = System.currentTimeMillis();
                String now = now1.toString();
                diary_data data = new diary_data(now,str_title,str_date,str_write);
                mDatabaseReference.child("Diary").child(Long.toString(now1)).setValue(data);
                Log.d("일기","성공"+str_title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("일기","실패");
            }
        });

    }

}


