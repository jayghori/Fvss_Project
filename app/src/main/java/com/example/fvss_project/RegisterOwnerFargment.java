package com.example.fvss_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fvss_project.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterOwnerFargment extends Fragment {

    View view;
    Button btnRegister;
    LinearLayout tvLinearLayout;
    EditText edtEmail, edtPassword, edtRePassword, edtName, edtPhoneNumber, edtAddress;
    FirebaseAuth auth;
    String adminId;
    FirebaseFirestore firebaseFirestore;

    public RegisterOwnerFargment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_owner_register, container, false);

        btnRegister = view.findViewById(R.id.btnRegister);
        edtName = view.findViewById(R.id.edtName);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtReEnterPassword);
        auth = FirebaseAuth.getInstance();
        tvLinearLayout = view.findViewById(R.id.tvLinearLayout);
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        tvLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               final String email = edtEmail.getText().toString();
                                               final String pass = edtPassword.getText().toString();
                                               final String cpass = edtRePassword.getText().toString();
                                               final String address = edtAddress.getText().toString();
                                               final String phonenumber = edtPhoneNumber.getText().toString();
                                               final String name = edtName.getText().toString();

                                               if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass)) {
                                                   if (pass.equals(cpass)) {
                                                       auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                                           @Override
                                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                                               if (task.isSuccessful()) {
                                                                   auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                                       @Override
                                                                       public void onSuccess(AuthResult authResult) {
                                                                           adminId = auth.getCurrentUser().getUid();

                                                                           auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                                   if (task.isSuccessful()) {

                                                                                       Admin parkingOwner = new Admin(email, name, address, phonenumber,adminId );
                                                                                       firebaseFirestore.collection("ParkingOwner").document(adminId).set(parkingOwner).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                           @Override
                                                                                           public void onSuccess(Void aVoid) {


                                                                                               auth.signOut();
                                                                                               Toast.makeText(getContext(), "please check ur Email", Toast.LENGTH_SHORT).show();
                                                                                               Intent intent = new Intent(getContext(), AdminLoginActivity.class);
                                                                                               startActivity(intent);
                                                                                           }
                                                                                       });
                                                                                   } else {

                                                                                       Toast.makeText(getContext(), "Kindly enter valid Email  or link verify ", Toast.LENGTH_SHORT).show();
                                                                                   }
                                                                               }
                                                                           });
                                                                       }
                                                                   }).addOnFailureListener(new OnFailureListener() {
                                                                       @Override
                                                                       public void onFailure(@NonNull Exception e) {

                                                                       }
                                                                   });

                                                               } else {
                                                                   Toast.makeText(getContext(), "Somthing Is Wrong", Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       });
                                                   } else {
                                                       Toast.makeText(getContext(), "Please enter right password", Toast.LENGTH_SHORT).show();
                                                   }
                                               } else {
                                                   Toast.makeText(getContext(), "Please enter Data!!", Toast.LENGTH_SHORT).show();
                                               }
                                           }


                                       }

        );
        return view;
    }
}

