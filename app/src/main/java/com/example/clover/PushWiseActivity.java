package com.example.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PushWiseActivity extends AppCompatActivity {

    EditText edit_saying;
    Button btn_saying;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_wise);

        edit_saying=(EditText)findViewById(R.id.edit_saying);
        btn_saying=(Button)findViewById(R.id.btn_saying);

        btn_saying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String saying=edit_saying.getText().toString().trim();
                        Long now1 = System.currentTimeMillis();
                        mDatabaseReference.child("Wise_Saying").child(Long.toString(now1)).setValue(saying);
                        Log.d("명언","성공"+saying);
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
