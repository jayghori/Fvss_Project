package com.example.fvss_project;

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

public class ResetpasswordActivity extends AppCompatActivity {


    Button btnResetPassword;
    EditText edtEmail;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        btnResetPassword=findViewById(R.id.btnResetPassword);
        edtEmail=findViewById(R.id.edtEmail);
        auth=FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass=edtEmail.getText().toString();
                auth.sendPasswordResetEmail(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ResetpasswordActivity.this, "please check your email ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ResetpasswordActivity.this, "somthing is wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}