package com.example.clover.wiseword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clover.DiaryActivity;
import com.example.clover.R;

import java.util.Random;

public class WiseWord_Click extends AppCompatActivity {

    ImageView background;
    TextView tv_author;
    TextView tv_saying;
    String str_saying;
    String str_author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_word__click);

        tv_saying = findViewById(R.id.wisewordclick_saying);
        tv_author = findViewById(R.id.wisewordclick_author);

        Intent it = getIntent();
        str_saying = it.getStringExtra("it_saying");
        str_author = it.getStringExtra(("it_author"));
        String blank = "\n";
        tv_author.setText(str_author);
        tv_saying.setText(blank+str_saying);

        background = findViewById(R.id.wisewordclick_background);
        double random = Math.random();
        int random_background = (int)(random*100);
        int switch_number = random_background%13;

        switch (switch_number){
            case 0: background.setImageResource(R.drawable.background1);
                break;
            case 1: background.setImageResource(R.drawable.background2);
                break;
            case 2: background.setImageResource(R.drawable.background3);
                break;
            case 3: background.setImageResource(R.drawable.background4);
                break;
            case 4: background.setImageResource(R.drawable.background5);
                break;
            case 5: background.setImageResource(R.drawable.background6);
                break;
            case 6: background.setImageResource(R.drawable.background7);
                break;
            case 7: background.setImageResource(R.drawable.background8);
                break;
            case 8: background.setImageResource(R.drawable.background9);
                break;
            case 9: background.setImageResource(R.drawable.background10);
                break;
            case 10: background.setImageResource(R.drawable.background11);
                break;
            case 11: background.setImageResource(R.drawable.background12);
                break;
            case 12: background.setImageResource(R.drawable.background13);
                break;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //손가락으로 화면을 누르기 시작했을 때 할 일
                break;
            case MotionEvent.ACTION_MOVE:
                //터치 후 손가락을 움직일 때 할 일
                break;
            case MotionEvent.ACTION_UP:
                Intent intent = new Intent(this, WisewordActivity.class);
                startActivity(intent);
                finish();
                break;
            case MotionEvent.ACTION_CANCEL:
                // 터치가 취소될 때 할 일
                break;
            default:
                break;
        }
        return true;
    }
}