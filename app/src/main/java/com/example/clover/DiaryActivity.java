package com.example.clover;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clover.wiseword.WisewordActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    public User user;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        /*Hook*/
        drawerLayout = findViewById(R.id.drawerlayout_diary);
        navigationView = findViewById(R.id.navigationview_diary);
        toolbar = findViewById(R.id.toolbar_diary);

        /*Intent it = getIntent();
        userEmail = it.getStringExtra("userEmail");*/

        String sfName="save";
        SharedPreferences sf =getSharedPreferences(sfName, MODE_PRIVATE);
        userEmail = sf.getString("email", "");


        /*Navigation Drawer Menu*/
        //Hide or show item
        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigations_drawer_open, R.string.navigations_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        /*일기 리스트*/
        mContext = this;
        final ArrayList<Article> articleList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.diary_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        mDatabaseReference.child("Diary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Article article = postSnapshot.getValue(Article.class);
                    if(article.getuserEmail().equals(userEmail)) {
                        articleList.add(article);
                    }
                }
                mAdapter = new ArticleAdapter(articleList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }

        });

        mAdapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(mAdapter);
        Toast.makeText(DiaryActivity.this, userEmail, Toast.LENGTH_SHORT).show();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            public void onClick(View view, int position) {
                Article article = articleList.get(position);
                Intent intent;
                intent = new Intent(DiaryActivity.this, WritediaryActivity.class);
                intent.putExtra("it_id",article.getid());
                intent.putExtra("userEmail",userEmail);
                startActivity(intent);
                finish();
                return;
            }

            @Override
            public void onLongClick(View view, int position) {
                return;
            }
        }));

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private DiaryActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DiaryActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                finish();
                break;

            case R.id.nav_diary:
                break;

            case R.id.nav_wiseword:
                Intent intent2 = new Intent(DiaryActivity.this, WisewordActivity.class);
                intent2.putExtra("userEmail",userEmail);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_wrongthrow:
                Intent intent3 = new Intent(DiaryActivity.this, WorryThrowActivity.class);
                intent3.putExtra("userEmail",userEmail);
                startActivity(intent3);
                finish();
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                Intent intent4 = new Intent(DiaryActivity.this,LoginActivity.class);
                intent4.putExtra("userEmail",userEmail);
                startActivity(intent4);
                finish();
                break;

            case R.id.nav_music:
                Intent intent5 = new Intent(DiaryActivity.this,MusicActivity.class);
                intent5.putExtra("userEmail",userEmail);
                startActivity(intent5);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void go_writediary(View v){
        Intent intent = new Intent(DiaryActivity.this, WritediaryActivity.class);
        intent.putExtra("it_id","null");
        intent.putExtra("userEmail",userEmail);
        startActivity(intent);
        finish();
    }


}


