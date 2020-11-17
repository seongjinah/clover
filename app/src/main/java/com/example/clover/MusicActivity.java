package com.example.clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MusicActivity extends AppCompatActivity {

    Button Playbtn,Pausebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Playbtn=(Button)findViewById(R.id.play_button);
        Pausebtn=(Button)findViewById(R.id.pause_button);

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
