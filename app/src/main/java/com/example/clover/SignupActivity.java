package com.example.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    Button btn_signup;
    TextInputEditText editId,editPw, editRePw;
    TextInputLayout layout_id,layout_pw;
    boolean isValid=true;
    ImageView check;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_signup=(Button)findViewById(R.id.button_signup_signup);
        editId=(TextInputEditText) findViewById(R.id.signup_id);
        editPw=(TextInputEditText) findViewById(R.id.signup_pw);
        layout_id=(TextInputLayout)findViewById(R.id.signup_id_layout);
        layout_pw=(TextInputLayout)findViewById(R.id.signup_pw_layout);
        editRePw=(TextInputEditText) findViewById(R.id.signup_repw);
        check = (ImageView) findViewById(R.id.signup_check);

        layout_pw.setPasswordVisibilityToggleEnabled(true);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid=true;
                final String id=editId.getText().toString().trim();
                String pw=editPw.getText().toString().trim();
                checkid(id);
                checkpw(pw);
                if(isValid) {
                    signupUser(id, pw);
                }
            }
        });

        editRePw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editPw.getText().toString().equals(editRePw.getText().toString())) check.setImageResource(R.drawable.check);
                else check.setImageResource(R.drawable.x);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editPw.getText().toString().equals(editRePw.getText().toString())) check.setImageResource(R.drawable.check);
                else check.setImageResource(R.drawable.x);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void checkid(String id){
        if(id.length()==0){
            layout_id.setError("이메일을 입력하세요");
            isValid=false;
        }
        else{
            layout_id.setError(null);
        }
    }

    private void checkpw(String pw){
        if(pw.length()<8){
            layout_pw.setError("비밀번호는 8자리 이상이어야 합니다");
            isValid=false;
        }
        else{
            layout_pw.setError(null);
        }
    }

    private void signupUser(final String id, String pw){
        mAuth.createUserWithEmailAndPassword(id,pw).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User userData=new User(id);
                    mDatabase.getReference().child("Users").push().setValue(userData);
                    FirebaseUser user=mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"이메일 전송 성공",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignupActivity.this,"이메일 전송 실패",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mAuth.signOut();
                    if(user!=null) {
                        boolean emailVerified = user.isEmailVerified();
                        if (!emailVerified) {
                            Toast.makeText(SignupActivity.this,"이메일 인증 후 사용해주세요",Toast.LENGTH_SHORT).show();
                        }
                        /*Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);*/
                        finish();
                    }
                }
                else{
                    Toast.makeText(SignupActivity.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
