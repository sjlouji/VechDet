package com.example.vechdet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VechileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    TextView plate,entry,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechile);
        plate = findViewById(R.id.number_plate);
        entry = findViewById(R.id.date_entry);
        exit = findViewById(R.id.date_exit);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Vehicle details");
        Bundle extras = getIntent().getExtras();
        String newString= extras.getString("user_id");
        databaseReference.child(newString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("exit_date").exists()) {
                    String plate_number = dataSnapshot.child("number_plate").getValue().toString();
                    String date_entry_1 = dataSnapshot.child("entry_date").getValue().toString();
                    String date_exit_1 = dataSnapshot.child("exit_date").getValue().toString();
                    plate.setText(plate_number);
                    entry.setText(date_entry_1);
                    exit.setText(date_exit_1);
                }else{
                    String plate_number = dataSnapshot.child("number_plate").getValue().toString();
                    String date_entry_1 = dataSnapshot.child("entry_date").getValue().toString();
                    plate.setText(plate_number);
                    entry.setText(date_entry_1);
                    exit.setText("Not Found");                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
