package com.example.fvss_project;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    Button btnLogIn;
    EditText edtEmail, edtPassword;
    TextView tvForgetPassword;
    FirebaseAuth auth;
    LinearLayout linearLayoutSignUp;
    private boolean progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogIn = findViewById(R.id.btnLogIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        linearLayoutSignUp = findViewById(R.id.linearLayoutSignUp);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        auth = FirebaseAuth.getInstance();


//        if(auth.getCurrentUser()!=null){
//            Intent intent = new Intent(LoginActivity.this, ParkingOwnerMainActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
        progressDialog.setMax(100);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = edtEmail.getText().toString();
                String cpass = edtPassword.getText().toString();


                if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass)) {

                    auth.signInWithEmailAndPassword(pass, cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                if (auth.getCurrentUser().isEmailVerified()) {

                                    FirebaseFirestore.getInstance().collection("User").whereEqualTo("email",edtEmail.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot querySnapshot) {
                                            if(querySnapshot.getDocuments().size()==0){
                                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                startActivity(intent);

                                                finish();
                                            }
                                            else {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });


                                } else {
                                    Toast.makeText(LoginActivity.this, "Please Verify link via Email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "somthing is wrong", Toast.LENGTH_SHORT).show();
                            }




                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "please enter  Data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterFragment.class);
                startActivity(intent);

            }
        });

        SpannableString spannableString = new SpannableString("forget password?");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        tvForgetPassword.setText(spannableString);

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ResetpasswordActivity.class);
                startActivity(intent);

            }
        });

    }

}