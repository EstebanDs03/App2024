package com.co.esteban;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Usuario extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userPhoneTextView;
    private TextView userAddressTextView;
    private TextView userAgeTextView;
    private TextView userGenderTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
        userPhoneTextView = findViewById(R.id.user_phone);
        userAddressTextView = findViewById(R.id.user_address);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());

        if (currentUser != null) {
            loadUserProfile(currentUser);
        }
    }

    private void loadUserProfile(FirebaseUser user) {
        userEmailTextView.setText(user.getEmail());

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("nombre").getValue(String.class);
                    String phone = snapshot.child("telefono").getValue(String.class);
                    String address = snapshot.child("correo").getValue(String.class);


                    userNameTextView.setText(name);
                    userPhoneTextView.setText(phone);
                    userAddressTextView.setText(address);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}