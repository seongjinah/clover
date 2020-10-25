package com.example.clover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DiaryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    private Context mContext;
    private ArticleAdapter mAdapter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase Database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = Database.getReference();

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

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


        /*일기 리스트*/
        mContext=this;
        final ArrayList<Article> articleList = new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.diary_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);


        Article article = new Article("a","b","c","d");
        articleList.add(article);
        /*mDatabaseReference.child("Diary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Article article = postSnapshot.getValue(Article.class);
                    articleList.add(article);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }

        });*/

        mAdapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_diary:
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(DiaryActivity.this, WisewordActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(DiaryActivity.this, WorryThrowActivity.class);
                startActivity(intent3);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void go_writediary(View v){
        Intent intent = new Intent(DiaryActivity.this, WritediaryActivity.class);
        startActivity(intent);
        finish();
    }

}


