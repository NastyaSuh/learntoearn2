package com.example.learntoearn2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Education_Site extends AppCompatActivity {
    private ImageButton toTheorybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education__site);

        toTheorybtn = findViewById(R.id.theory);
        toTheorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Education_Site.this, Theory.class);
                startActivity(intent);
            }
        });
    }
}
