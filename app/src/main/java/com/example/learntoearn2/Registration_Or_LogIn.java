package com.example.learntoearn2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.learntoearn2.user_information.Info_to_Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Registration_Or_LogIn extends AppCompatActivity {
    private Button button_login;
    private Button button_signup;
    private Button reset_passw;

    private FirebaseAuth mAuth;
    FirebaseAuth auth; //аутентификация пользователя
    FirebaseDatabase database; //подключаем базу данных
    DatabaseReference user; //работа с бд
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__or__log_in);
        mAuth = FirebaseAuth.getInstance();

        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignin_Window();
            }
        });

        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegister_Window();
            }
        });

        auth = FirebaseAuth.getInstance(); //подключаем firebase
        database = FirebaseDatabase.getInstance(); //подключаем Firebase Database
        user = database.getReference("Users"); //работа с отважными людьми, которые осмелились пользовать нашу прогу
        root = findViewById(R.id.root_element);

        reset_passw = (Button) findViewById(R.id.button3);
        reset_passw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetwindow();
            }
        });
    }

    private void showResetwindow() {
        final AlertDialog.Builder dial = new AlertDialog.Builder(this);
        dial.setTitle("Reset the password");
        dial.setMessage("To reset password, enter your email, press the button and check the mail to follow instructions.");
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View Reset_activity = layoutInflater.inflate(R.layout.activity_reset_password, null);
        dial.setView(Reset_activity);
        final MaterialEditText emailres = Reset_activity.findViewById(R.id.emailresetField);
        dial.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialInterface, int which) {
                dialInterface.dismiss();
            }
        });

        dial.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialInt, int which) {
                if(TextUtils.isEmpty(emailres.getText().toString())){
                    Snackbar.make(root, "Enter your Email Address", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String email = emailres.getText().toString().trim();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(root, "Check email to reset your password", Snackbar.LENGTH_SHORT).show();
                        }

                        else{
                            Snackbar.make(root, "Failed to send reset password email", Snackbar.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        dial.show();
    }

    private void showRegister_Window() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sign up");
        dialog.setMessage("Enter the information to sign up");

        LayoutInflater inflater = LayoutInflater.from(this);
        View Register_Window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(Register_Window);
        final MaterialEditText email = Register_Window.findViewById(R.id.emailField);
        final MaterialEditText password = Register_Window.findViewById(R.id.passwordField);
        final MaterialEditText name = Register_Window.findViewById(R.id.nameField);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInt, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter your Email Adress", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Enter your Name", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Your Password is too small", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //с помощью предыдущих условных операторов мы проверяли правильность введенных данных
                //если они верны, то регистрируем пользователя;)
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()) //регистрируем пользователя
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Info_to_Auth users = new Info_to_Auth();
                                users.setEmail(email.getText().toString());
                                users.setPassword(password.getText().toString());
                                users.setName(name.getText().toString());

                                user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root, "Добавлен новый пользователь!", Snackbar.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(root, "Что-то пошло не так;("+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });
            }
        });
        dialog.show();
    }

    private void showSignin_Window() {AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Log in");
        dialog.setMessage("Enter the information to log in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View Signin_Window = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(Signin_Window);
        final MaterialEditText email = Signin_Window.findViewById(R.id.emailField);
        final MaterialEditText password = Signin_Window.findViewById(R.id.passwordField);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        dialog.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInt, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter your Email Adress", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Your Password is too small", Snackbar.LENGTH_LONG).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(Registration_Or_LogIn.this, SetUpActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Autorization Error"+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }
}
