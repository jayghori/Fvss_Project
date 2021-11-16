package com.example.fvss_project;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.fvss_project.Adapter.ParkingDetailAdapter;
//import com.example.fvss_project.Model.ParkingDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AdminMainActivity extends AppCompatActivity {

    Button btnAddParkingSlot;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
//    List<ParkingDetail> parkingDetailList;
    FirebaseAuth auth;
    String TAG = "TAGER";
    EditText edtSearch;
//    ParkingDetailAdapter parkingDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnAddParkingSlot = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();
//        parkingDetailList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        edtSearch=findViewById(R.id.edtSearch);

//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = s.toString();
//                Log.d("TAGG", "onTextChanged: " + s);
//
//                if (parkingDetailList.size() > 0) {
//                    if (text.equals("")) {
//                        parkingDetailAdapter.setParkingList(parkingDetailList);
//                    } else {
//                        List<ParkingDetail> matchedList = new ArrayList<>();
//                        for (ParkingDetail offer : parkingDetailList) {
//                            if (offer.getTwoWheeler().toLowerCase().contains(text))
//                                matchedList.add(offer);
//                           else if (offer.getPhonenumber().toLowerCase().contains(text))
//                                matchedList.add(offer);
//                        }
//                        parkingDetailAdapter.setParkingList(matchedList);
//                    }
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


//        firebaseFirestore.collection("ParkingOwner").document(auth.getCurrentUser().getUid()).collection("ParkingDetail").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
//
//                        parkingDetailList.add(snapshot.toObject(ParkingDetail.class));
//                    }
//
//                    parkingDetailAdapter = new ParkingDetailAdapter(ParkingOwnerMainActivity.this, parkingDetailList);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(ParkingOwnerMainActivity.this, LinearLayoutManager.VERTICAL, false));
//                    recyclerView.setAdapter(parkingDetailAdapter);
////                    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Log.d(TAG, "onFailure: " + e);
//            }
//        });

//        btnAddParkingSlot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(AdminMainActivity.this, ParkingOwnerAddParkingDetails.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.sign_out) {
            auth.signOut();
            Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }
        if (item.getItemId() == R.id.contactUs) {
            Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
            callIntent.setData(Uri.parse("tel:0800000000"));    //this is the phone number calling
            //check permission
            //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
            //the system asks the user to grant approval.
            if (ActivityCompat.checkSelfPermission(AdminMainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //request permission from user if the app hasn't got the required permission
                ActivityCompat.requestPermissions(AdminMainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                        10);

            } else {     //have got permission
                try {
                    startActivity(callIntent);  //call activity and make phone call
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (item.getItemId() == R.id.profile) {
            Intent intent=new Intent(AdminMainActivity.this,AdminProfile.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("No", null).show();
    }


//    private void Delete() {
//
//        AlertDialog.Builder dailog = new AlertDialog.Builder(ParkingOwnerMainActivity.this);
//        dailog.setTitle("Are You Sure??");
//        dailog.setMessage("Deleting this account will result in completely removing your" +
//                "account from the system and you won't be able to acess the app:");
//
//        dailog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                firebaseFirestore.collection("ParkingOwner").document(auth.getCurrentUser().getUid()).collection("ParkingDetail").document(value1).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(ParkingOwnerMainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(ParkingOwnerMainActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(ParkingOwnerMainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//        dailog.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//            }
//        });
//
//        AlertDialog alertDialog = dailog.create();
//
//    }

//    public ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//            parkingDetailList.remove(viewHolder.getAdapterPosition());
//            parkingDetailAdapter.notifyDataSetChanged();
//
//        }
//    };
}
