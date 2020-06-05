package com.example.learntoearn2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Theory extends AppCompatActivity {
    private ImageButton IntroductionBtn;
    private CircleImageView Nav_Avatar;
    private TextView Username;
    private ImageView Arrow;

    String CurrentUserId;
    private FirebaseAuth mAuth;
    DatabaseReference UserReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        IntroductionBtn = findViewById(R.id.introduction);
        Nav_Avatar = findViewById(R.id.nav_avatar);
        Username = findViewById(R.id.username);
        Arrow = findViewById(R.id.backtoProfile);

        Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Theory.this, Profile.class);
                startActivity(intent);
            }
        });

        IntroductionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Theory.this , introduction.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        UserReference = FirebaseDatabase.getInstance().getReference().child("Users");
        CurrentUserId = mAuth.getCurrentUser().getUid();

        UserReference.child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String name = dataSnapshot.child("name").getValue().toString();
                    String ava = dataSnapshot.child("profileimages").getValue().toString();

                    Picasso.get().load(ava).into(Nav_Avatar);
                    Username.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
