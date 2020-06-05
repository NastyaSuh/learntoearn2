package com.example.learntoearn2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private Button saveInformation;
    private CircleImageView avatar;
    String currentuserId;
    private ProgressDialog loadingBar;
    private ImageView arrow;

    final static int Gallery_Pick = 102;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;   //user Reference
    private StorageReference  UserProfileImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        name = findViewById(R.id.change_name);
        surname = findViewById(R.id.change_surname);
        saveInformation = findViewById(R.id.button_save);
        avatar = findViewById(R.id.avatar);
        arrow = findViewById(R.id.backtoProfile);

        mAuth = FirebaseAuth.getInstance();
        currentuserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserId);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images"); //создаем папку, где будут хранится фотографии
       //получаем уникальный айдишник пользователя

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
                open_gallery();
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("profileimages")){

                        //получаем ссылку на конкретное изображение
                        String image = dataSnapshot.child("profileimages").getValue().toString();

                        //Показываем изображение на месте
                        Picasso.get().load(image).into(avatar);
                    }

                    else{
                        Toast.makeText(SetUpActivity.this, "Выберите фотографию", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetUpActivity.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK  && data != null){
            Uri ImageUri = data.getData();

            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        //Обрезаем фотографию
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            loadingBar.setTitle("Сохраняем изменения....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                final StorageReference filepath = UserProfileImageRef.child(currentuserId + ".jpg"); //создаем фолдер для хранения фотографии определенного пользователя

                //отправляем наше изображение в Firebase Storage
                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUri = uri.toString();
                                userRef.child("profileimages").setValue(downloadUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SetUpActivity.this, "Фотография загружена в базу данных", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                        else{
                                            String message = task.getException().getMessage();
                                            Toast.makeText(SetUpActivity.this, "Ошибка"+message, Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
            else{
                Toast.makeText(SetUpActivity.this, "Фотография не обрезается", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }

    private void open_gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выбрать фотографию"), Gallery_Pick);
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
            hashMap.put("name",  username);
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
