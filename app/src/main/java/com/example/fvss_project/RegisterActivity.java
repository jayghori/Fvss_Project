package com.example.fvss_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fvss_project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class
RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    String userId;
    LinearLayout tvLinearLayout;
    EditText edtEmail,edtPassword,edtRePassword,edtPhoneNumber,edtName,edtAddress;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnRegister=findViewById(R.id.btnRegister);
        edtName=findViewById(R.id.edtName);
        edtPhoneNumber=findViewById(R.id.edtPhoneNumber);
        edtAddress=findViewById(R.id.edtAddress);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        edtRePassword=findViewById(R.id.edtReEnterPassword);
        auth=FirebaseAuth.getInstance();
        tvLinearLayout=findViewById(R.id.tvLinearLayout);
        firebaseFirestore=FirebaseFirestore.getInstance();

        tvLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=edtEmail.getText().toString();
                final String pass=edtPassword.getText().toString();
                String cpass=edtRePassword.getText().toString();
                final String address=edtAddress.getText().toString();
                final String phonenumber=edtPhoneNumber.getText().toString();
                final String name=edtName.getText().toString();

                if(!TextUtils.isEmpty(name)  &&  !TextUtils.isEmpty(pass)  &&  !TextUtils.isEmpty(cpass))
                {
                    if(pass.equals(cpass))
                    {
                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    userId = auth.getCurrentUser().getUid();

                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful())
                                            {
                                                User user=new User(name,email,phonenumber,address,userId);
                                                firebaseFirestore.collection("User").document(userId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        auth.signOut();
                                                        Toast.makeText(RegisterActivity.this, "please check ur Email", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                            else
                                            {

                                                Toast.makeText(RegisterActivity.this, "Kindly enter valid Email  or link verify ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "Somthing Is Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Please enter right password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Please enter Data!!", Toast.LENGTH_SHORT).show();
                }
            }


        }
        );

    }
}