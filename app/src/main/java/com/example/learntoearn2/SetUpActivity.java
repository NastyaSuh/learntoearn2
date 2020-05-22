package com.example.learntoearn2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
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
                startActivityForResult(galleryIntent, Gallery_Pick );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null){
            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(requestCode == RESULT_OK){
                loadingBar.setTitle("Сохраняем изменения....");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();
                StorageReference filePath = userProfileImageRef.child(currentuserId + ".png");
                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        userProfileImageRef.child(currentuserId + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SetUpActivity.this, "Фотография загружена", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    final String dowloadUrl = task.getResult().toString();
                                    userRef.child("Profile Images").setValue(dowloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(SetUpActivity.this, "Фотография успешно загружена в базу данных Firebase", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else{
                                                String message = task.getException().getMessage();
                                                Toast.makeText(SetUpActivity.this, "Произошла ошибка"+message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                });
            }

            else{
                Toast.makeText(SetUpActivity.this, "Фотография не может быть обрезана", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
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
