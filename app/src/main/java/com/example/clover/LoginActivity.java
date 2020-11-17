package com.example.clover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button button_signin,button_signup,button_chgpw;
    EditText edit_id,edit_pw;
    ImageView Logo;

    FirebaseDatabase Database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = Database.getReference();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    SharedPreferences autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_id=(EditText)findViewById(R.id.edit_id);
        edit_pw=(EditText)findViewById(R.id.edit_pw);
        button_signin=(Button)findViewById(R.id.button_signin);
        button_signup=(Button)findViewById(R.id.button_signup);
        button_chgpw=(Button)findViewById(R.id.button_chgpw);
        Logo=(ImageView)findViewById(R.id.Login_img);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        Logo.startAnimation(animation);

        //자동로그인
        autologin = getSharedPreferences("autologin",MODE_PRIVATE);
        String autoID = autologin.getString("inputID",null);
        String autoPW = autologin.getString("inputPW",null);

        if(mAuth.getCurrentUser()!=null){
            Log.d("autologin111","start");
            FirebaseUser user = mAuth.getCurrentUser();
            Intent music = new Intent(this,MusicService.class);
            startService(music);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (autoID != null && autoPW != null) {
            Log.d("autologin222","start");
            Login(autoID, autoPW);
        }

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        button_chgpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ChangepwActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=edit_id.getText().toString().trim();
                String pw=edit_pw.getText().toString().trim();
                if(id.equals("")){
                    Toast.makeText(LoginActivity.this,"이메일을 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else if(pw.equals("")){
                    Toast.makeText(LoginActivity.this,"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    Login(id,pw);
                }
            }
        });

    }

    private void Login(final String id, final String pw){
        mAuth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor autologin_editor = autologin.edit();
                            autologin_editor.putString("inputId", id);
                            autologin_editor.putString("inputPW", pw);
                            autologin_editor.apply();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Emailcheck(user);
                        } else {
                            Toast.makeText(LoginActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Emailcheck(FirebaseUser user){
        if(user!=null){
            boolean emailVerified=user.isEmailVerified();
            if(emailVerified){
                Log.d("useremail : ",user.getEmail());
                Intent music = new Intent(this,MusicService.class);
                startService(music);
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("userEmail",user.getEmail());
                startActivity(intent);
                finish();
            }
            else{
                mAuth.signOut();
                Toast.makeText(LoginActivity.this,"이메일 인증 후 사용하십시오",Toast.LENGTH_SHORT).show();
            }
        }
    }
}