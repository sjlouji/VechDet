package com.example.vechdet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.annotations.Nullable;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private DatabaseReference mUserRef;
    Toolbar mtoolnar;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getCurrentUser().getUid());
            Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_LONG).show();
        }
        mtoolnar=findViewById(R.id.alluserstoolbar);
        setSupportActionBar(mtoolnar);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Vehicle details");
        getSupportActionBar().setTitle("VechDet");
        mtoolnar.setTitleTextColor(getResources().getColor(android.R.color.white));
        user=FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Toast.makeText(MainActivity.this, databaseReference.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendToStart();
        } else {
            mUserRef.child("online").setValue("true");
        }

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(databaseReference, Users.class)
                .build();
        Toast.makeText(MainActivity.this, "Works Fin", Toast.LENGTH_LONG).show();

        FirebaseRecyclerAdapter<Users,UserViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vechilelist, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder viewHolder,final int position, @NonNull Users model) {
                Toast.makeText(MainActivity.this, "IN Work", Toast.LENGTH_LONG).show();
                viewHolder.setPlate(model.getNumber_plate());
                viewHolder.setEntryDate(model.getEntry_date());
                viewHolder.setExitDate(model.getExit_date());
                viewHolder.setExitDate(model.getExit_date());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user_id=getRef(position).getKey();
                        Intent i=new Intent(MainActivity.this,VechileActivity.class);
                        i.putExtra("user_id",user_id);
                        startActivity(i);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public UserViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setPlate(String Name){
            TextView username=mview.findViewById(R.id.vechileNumber);
            username.setText(Name);
        }
        public void setEntryDate(String statis){
            TextView status=mview.findViewById(R.id.entrydate);
            status.setText(statis);
        }
        public void setExitDate(String statis){
            TextView status=mview.findViewById(R.id.exitdate);
            status.setText(statis);
        }
    }
}
