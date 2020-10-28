package com.example.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.clover.wiseword.Saying;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PushWiseActivity extends AppCompatActivity {

    EditText edit_saying,edit_author;
    Button btn_saying;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_wise);

        edit_saying=(EditText)findViewById(R.id.edit_saying);
        edit_author=(EditText)findViewById(R.id.edit_saying_author);
        btn_saying=(Button)findViewById(R.id.btn_saying);
        Spinner spinner=(Spinner)findViewById(R.id.spinner_category);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category=(String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_saying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String wise_saying=edit_saying.getText().toString().trim();
                        String author=edit_author.getText().toString().trim();
                        Saying wSaying=new Saying(wise_saying,author/*,category*/);
                        Long now1 = System.currentTimeMillis();
                        mDatabaseReference.child("Wise_Saying").child(category).child(Long.toString(now1)).setValue(wSaying);
                        Log.d("명언","성공");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("명언","실패");
                    }
                });
            }
        });
    }
}
