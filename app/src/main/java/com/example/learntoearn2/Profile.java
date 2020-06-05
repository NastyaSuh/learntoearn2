package com.example.learntoearn2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private RecyclerView postlist;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CircleImageView Profile_ava;
    private TextView Navigation_name;
    private TextView Email;
    //private TextView Surname;
    private Switch switch_notifications;

    private FirebaseAuth mAuth;
    DatabaseReference userReference;

    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_shutter);

        drawerLayout = findViewById(R.id.info_shutter);
        navigationView = findViewById(R.id.navigate);
        mToolbar = findViewById(R.id.main_info_bar);

        switch_notifications = findViewById(R.id.switch1);

        mAuth=FirebaseAuth.getInstance();
        userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUserId = mAuth.getCurrentUser().getUid();

        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        Profile_ava = findViewById(R.id.avatar);
        Navigation_name = findViewById(R.id.userName);
        Email = findViewById(R.id.email);
        //Surname = findViewById(R.id.userSurname);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });


        setActionBar(mToolbar);
        getActionBar().setTitle("Профиль");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getActionBar().setDisplayHomeAsUpEnabled(true);

        userReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String retrieving_avatar = dataSnapshot.child("profileimages").getValue().toString();
                    String retrieving_name = dataSnapshot.child("name").getValue().toString();
                    String retrieving_email = dataSnapshot.child("email").getValue().toString();
                    String retrieving_surname = dataSnapshot.child("surname").getValue().toString();


                    Picasso.get().load(retrieving_avatar).into(Profile_ava);
                    Email.setText(retrieving_email);
                    Navigation_name.setText(retrieving_name);
                    //Surname.setText(retrieving_surname);
                    Log.d("DatabaseError", dataSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
                Log.d("DatabaseError", databaseError.getMessage());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void UserMenuSelector(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show();
                break;

            case R.id.education:
                Toast.makeText(this, "Страница обучения", Toast.LENGTH_SHORT).show();
                sendUsertoEducationSite();
                break;

            case R.id.profile_settings:
                Toast.makeText(this, "Настройки профиля", Toast.LENGTH_SHORT).show();
                sendUsertoSetUpActivity();
                break;

            case R.id.log_out:
                mAuth.signOut();
                SendUserToLoginActivity();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            SendUserToLoginActivity();
        }
        else{
            Check_forUserExistence();
        }
    }

    private void SendUserToLoginActivity(){
        Intent loginIntent = new Intent(Profile.this, Registration_Or_LogIn.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void Check_forUserExistence(){
        final String currentUser_Id = mAuth.getCurrentUser().getUid();
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(currentUser_Id)){
                    //sendUsertoSetUpActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendUsertoSetUpActivity() {
        Intent setupIntent = new Intent(this, SetUpActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void sendUsertoEducationSite() {
        Intent eduIntent = new Intent(this, Theory.class);
        eduIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(eduIntent);
        finish();
    }
}
