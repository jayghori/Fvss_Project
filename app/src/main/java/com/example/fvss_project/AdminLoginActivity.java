package com.example.fvss_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    Button btnLogIn;
    EditText edtEmail,edtPassword;
    TextView tvForgetPassword;
    FirebaseAuth auth;
    LinearLayout linearLayoutSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        btnLogIn=findViewById(R.id.btnLogIn);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        linearLayoutSignUp=findViewById(R.id.linearLayoutSignUp);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        auth=FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences = getSharedPreferences("Demo", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        if(auth.getCurrentUser()!=null){
//            Intent intent = new Intent(ParkingOwnerLoginActivity.this, ParkingOwnerMainActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass=edtEmail.getText().toString();
                String cpass=edtPassword.getText().toString();

                if(!TextUtils.isEmpty(pass)  && !TextUtils.isEmpty(cpass))
                {

                    auth.signInWithEmailAndPassword(pass,cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                if(auth.getCurrentUser().isEmailVerified())
                                {
                                    editor.putString("str", auth.getCurrentUser().getUid());
                                    editor.apply();

                                    Intent intent=new Intent(AdminLoginActivity.this,AdminMainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(AdminLoginActivity.this, "Please Verify link via Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(AdminLoginActivity.this, "somthing is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(AdminLoginActivity.this, "please enter  Data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        SpannableString spannableString=new SpannableString("forget password?");
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
        tvForgetPassword.setText(spannableString);

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminLoginActivity.this,ResetpasswordActivity.class);
                startActivity(intent);

            }
        });
        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLoginActivity.this,AdminRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}