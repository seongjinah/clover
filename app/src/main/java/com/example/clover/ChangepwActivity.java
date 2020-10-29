package com.example.clover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangepwActivity extends AppCompatActivity {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Button btn_send;
    EditText edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);

        btn_send=(Button)findViewById(R.id.btn_resend);
        edit_email=(EditText)findViewById(R.id.edit_re_email);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edit_email.getText().toString().trim();
                if(email.equals("")){
                    Toast.makeText(ChangepwActivity.this,"이메일을 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    //회원인지 확인하기

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ChangepwActivity.this,"이메일 전송 성공.",Toast.LENGTH_SHORT).show();
                                /*Intent intent=new Intent(ChangepwActivity.this,MainActivity.class);
                                startActivity(intent);*/
                                finish();
                            }
                            else{
                                Toast.makeText(ChangepwActivity.this,"이메일 전송 실패",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}