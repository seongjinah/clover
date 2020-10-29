package com.example.clover;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

        /*Tool Bar*/
        setSupportActionBar(toolbar);

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
                mAuth.signOut();
                Intent intent4 = new Intent(WorryThrowActivity.this,LoginActivity.class);
                intent4.putExtra("userEmail",userEmail);
                startActivity(intent4);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void delete_worry(View v){
        et_worry.setText("");
    }
}
