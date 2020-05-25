package com.example.learntoearn2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private Button saveInformation;
    private CircleImageView avatar;
    String currentuserId;
    private ProgressDialog loadingBar;

    final static int Gallery_Pick = 102;

    private Uri ImageUri;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference  userProfileImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        name = findViewById(R.id.change_name);
        surname = findViewById(R.id.change_surname);
        saveInformation = findViewById(R.id.button_save);
        avatar = findViewById(R.id.avatar);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("profileimages");
        currentuserId = mAuth.getCurrentUser().getUid();

        loadingBar = new ProgressDialog(this);

        saveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_SetUp_Information();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("images/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Выбрать фотографию"), Gallery_Pick );
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("profileimages")) {
                        String image = dataSnapshot.child("profileimages").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(avatar);
                    } else {
                        Toast.makeText(SetUpActivity.this, "Сначала выберите фотографию", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                loadingBar.setTitle("Сохраняем изменения....");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();
                StorageReference filePath = userProfileImageRef.child(currentuserId + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SetUpActivity.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();

                            userRef.child("profileimages").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent setupintent = new Intent(SetUpActivity.this, SetUpActivity.class);
                                        startActivity(setupintent);
                                        Toast.makeText(SetUpActivity.this, "Фотография успешно загружена в Базу Данных", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else{
                                        String message = task.getException().getMessage();
                                        Toast.makeText(SetUpActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }

            else{
                Toast.makeText(SetUpActivity.this, "Фотография не может быть обрезана", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
            avatar.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Save_SetUp_Information() {
        String username = name.getText().toString();
        String usersurname = surname.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Введите, пожалуйста, имя!", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(usersurname)){
            Toast.makeText(this, "Введите, пожалуйста, фамилию!", Toast.LENGTH_LONG).show();
        }
        else{
            loadingBar.setTitle("Сохраняем изменения....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap hashMap = new HashMap();
            hashMap.put("name", username);
            hashMap.put("surname", usersurname);
            hashMap.put("status", "Hey there! I am using Learn to Earn to learn Physics:)");

            userRef.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        sendUserToProfile();
                        Toast.makeText(SetUpActivity.this, "Аккаунт успешно создан!", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                    else{
                        String message = task.getException().getMessage();
                        Toast.makeText(SetUpActivity.this, "Произошла ошибка"+message, Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void sendUserToProfile() {
        Intent profile_intent = new Intent(SetUpActivity.this, Profile.class);
        profile_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(profile_intent);
        finish();
    }
}
