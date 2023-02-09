package com.example.firebasecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText edtUser, edtPasswd, edtCnfPasswd;
    private Button btnRegister;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;
    private TextView logintV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtUser = findViewById(R.id.idEdtUsername);
        edtPasswd = findViewById(R.id.idEdtPassword);
        edtCnfPasswd = findViewById(R.id.idEdtCnfPassword);
        btnRegister = findViewById(R.id.registerbtn);
        loadingPB = findViewById(R.id.pbloading);
        logintV = findViewById(R.id.registertv);
        mAuth = FirebaseAuth.getInstance();
        logintV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String username = edtUser.getText().toString();
                String password = edtPasswd.getText().toString();
                String cnfpasswd = edtCnfPasswd.getText().toString();
                if(!password.equals(cnfpasswd)){
                    Toast.makeText(RegistrationActivity.this, "Please Check the Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cnfpasswd)   ){
                    Toast.makeText(RegistrationActivity.this, "Please add all credentials", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Failed to Register..!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });




    }
}