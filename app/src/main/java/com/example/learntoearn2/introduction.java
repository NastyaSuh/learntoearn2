package com.example.learntoearn2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class introduction extends AppCompatActivity {
    private TextView thermsText;
    public TextView startText;
    public TextView thermsProgress;
    private ImageButton thermsBtn;
    private ImageButton thirdPar;
    private TextView thirdText;
    private TextView progress3;
    private ImageButton first;
    private ImageView BacktoProfile;

    SharedPreferences s1;

    int startCount;
    int startCount1;
    int startCount2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        thermsProgress = findViewById(R.id.progress1);
        thermsText = findViewById(R.id.thermsText);
        thermsBtn = findViewById(R.id.therms);
        BacktoProfile = findViewById(R.id.backtoProfile);

        s1 = getSharedPreferences("Storage", MODE_PRIVATE);
        startCount = s1.getInt("progress",0);
        startCount1 = s1.getInt("progress1",0);
        startCount2 = s1.getInt("progress2",0);
        thermsProgress.setText("("+Integer.toString(startCount1)+ "/4)");

            thermsText.setTextColor(Color.BLACK);
            thermsProgress.setTextColor(Color.BLACK);
            thermsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(introduction.this, Therms.class);
                    startActivity(intent);
                }
            });


        startText = findViewById(R.id.progress);
        startText.setText("("+Integer.toString(startCount) + "/5)");
        startText.setTextColor(0xff000000);
        first = findViewById(R.id.startBtn);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(introduction.this, StartPhysic.class);
            startActivity(intent);
            }
        });
        thirdText = findViewById(R.id.thirdText);
        thirdPar = findViewById(R.id.thirdpar);
        thirdPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(introduction.this, Introduction3.class);
                startActivity(intent);
            }
        });
        progress3 = findViewById(R.id.progress2);
        progress3.setText("("+Integer.toString(startCount2)+"/6)");
        if(startCount<5){
            thermsBtn.setEnabled(false);
            thermsText.setTextColor(Color.GRAY);
            thermsProgress.setTextColor(Color.GRAY);
            thirdPar.setEnabled(false);
            thirdText.setTextColor(Color.GRAY);
            progress3.setTextColor(Color.GRAY);
        }
        if (startCount1<4){
            thirdPar.setEnabled(false);
            thirdText.setTextColor(Color.GRAY);
            progress3.setTextColor(Color.GRAY);
        }

        BacktoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(introduction.this, Theory.class);
                startActivity(intent);
            }
        });
    }
}
