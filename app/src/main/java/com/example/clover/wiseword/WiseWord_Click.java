package com.example.clover.wiseword;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.clover.R;

import java.util.Random;

public class WiseWord_Click extends AppCompatActivity {

    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_word__click);

        background = findViewById(R.id.wisewordclick_background);
        int random_background = (int)(Math.random()*12);
        switch (random_background){
            case 0: background.setImageResource(R.drawable.background1);
            case 1: background.setImageResource(R.drawable.background2);
            case 2: background.setImageResource(R.drawable.background3);
            case 3: background.setImageResource(R.drawable.background4);
            case 4: background.setImageResource(R.drawable.background5);
            case 5: background.setImageResource(R.drawable.background6);
            case 6: background.setImageResource(R.drawable.background7);
            case 7: background.setImageResource(R.drawable.background8);
            case 8: background.setImageResource(R.drawable.background9);
            case 9: background.setImageResource(R.drawable.background10);
            case 10: background.setImageResource(R.drawable.background11);
            case 11: background.setImageResource(R.drawable.background12);
            case 12: background.setImageResource(R.drawable.background13);
        }

    }
}