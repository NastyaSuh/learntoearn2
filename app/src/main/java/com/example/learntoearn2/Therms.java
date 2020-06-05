package com.example.learntoearn2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Therms extends AppCompatActivity {
    private View first;
    private View second;
    private View third;
    private View fourth;
    private TextView physicText1;
    private TextView physicText2;
    private TextView physicText3;
    private TextView physicText4;
    private Button startTest;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TextView a1;
    private SharedPreferences s1;
    private SharedPreferences.Editor e1;
    int startCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therms);
        s1 = getSharedPreferences("Storage", MODE_PRIVATE);
        s1.getInt("progress1",MODE_PRIVATE);

        physicText1 = findViewById(R.id.startPhysic1);
        physicText1.setText(R.string.Text5);
        physicText1.setTextColor(Color.BLACK);
        physicText2 = findViewById(R.id.startPhysic2);
        physicText2.setText(R.string.Text6);
        physicText2.setTextColor(Color.BLACK);
        first= findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        final View arrays[] = new View[]{first, second, third, fourth};
        btn1 = findViewById(R.id.firstbutton);
        btn2 = findViewById(R.id.secondbutton);
        btn3 = findViewById(R.id.thirdbutton);
        a1 = findViewById(R.id.question);
        startTest = findViewById(R.id.startTest);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = s1.edit();
                e1.putInt("progress1",1);
                e1.apply();
                physicText1.setText(null);
                physicText2.setText(null);
                arrays[0].setBackgroundColor(Color.rgb(46,204,113));
                btn1.setText(R.string.q13);
                btn2.setText(R.string.q14);
                btn3.setText(R.string.q15);
                a1.setText(R.string.a5);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn1.setBackgroundColor(Color.rgb(227,71,71));
                        startTest.setVisibility(View.VISIBLE);
                        startTest.setText("Заново");
                        btn2.setEnabled(false);
                        btn3.setEnabled(false);
                        startTest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btn1.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                btn3.setEnabled(true);                                      //разблокируем кнопки
                                btn2.setEnabled(true);
                            }
                        });
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn2.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                        startTest.setVisibility(View.VISIBLE);
                        startTest.setText("Заново");
                        btn1.setEnabled(false);
                        btn3.setEnabled(false);
                        startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                            @Override
                            public void onClick(View v) {
                                btn2.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                btn1.setEnabled(true);                                      //разблокируем кнопки
                                btn3.setEnabled(true);

                            }
                        });
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn3.setBackgroundColor(Color.rgb(74,237,143));
                        startTest.setVisibility(View.VISIBLE);
                        startTest.setText("Следующий вопрос");
                        btn1.setEnabled(false);
                        btn2.setEnabled(false);
                        startTest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                e1 = s1.edit();
                                e1.putInt("progress1",2);
                                e1.apply();
                                arrays[1].setBackgroundColor(Color.rgb(46,204,113));
                                startTest.setVisibility(View.INVISIBLE);
                                btn3.setBackgroundColor(0x00FFFFFF);
                                a1.setText(R.string.a6);
                                btn1.setEnabled(true);
                                btn1.setText(R.string.q16);
                                btn2.setText(R.string.q17);
                                btn3.setText(R.string.q18);
                                btn2.setEnabled(true);
                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btn1.setBackgroundColor(Color.rgb(227,71,71));
                                        startTest.setVisibility(View.VISIBLE);
                                        startTest.setText("Заново");
                                        btn2.setEnabled(false);
                                        btn3.setEnabled(false);
                                        startTest.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btn1.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                btn3.setEnabled(true);                                      //разблокируем кнопки
                                                btn2.setEnabled(true);
                                            }
                                        });
                                    }
                                });
                                btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btn2.setBackgroundColor(Color.rgb(74,237,143));
                                        startTest.setVisibility(View.VISIBLE);
                                        startTest.setText("Следующий вопрос");
                                        btn1.setEnabled(false);
                                        btn3.setEnabled(false);
                                        startTest.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                e1 = s1.edit();
                                                e1.putInt("progress1",3);
                                                e1.apply();
                                                btn2.setBackgroundColor(0x00FFFFFF);
                                                arrays[2].setBackgroundColor(Color.rgb(46,204,113));
                                                btn1.setEnabled(true);
                                                btn3.setEnabled(true);
                                                a1.setText(R.string.a7);
                                                btn1.setText(R.string.q19);
                                                btn2.setText(R.string.q20);
                                                btn3.setText(R.string.q21);
                                                btn1.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        btn1.setBackgroundColor(Color.rgb(74,237,143));
                                                        startTest.setVisibility(View.VISIBLE);
                                                        startTest.setText("Завершить тест");
                                                        btn2.setEnabled(false);
                                                        btn3.setEnabled(false);
                                                        startTest.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                e1 = s1.edit();
                                                                e1.putInt("progress1",4);
                                                                e1.apply();
                                                                arrays[3].setBackgroundColor(Color.rgb(46,204,113));
                                                                Intent intent = new Intent(Therms.this,introduction.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                    }
                                                });
                                                btn2.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        btn2.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                                        startTest.setVisibility(View.VISIBLE);
                                                        startTest.setText("Заново");
                                                        btn1.setEnabled(false);
                                                        btn3.setEnabled(false);
                                                        startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                                            @Override
                                                            public void onClick(View v) {
                                                                btn2.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                                btn1.setEnabled(true);                                      //разблокируем кнопки
                                                                btn3.setEnabled(true);

                                                            }
                                                        });

                                                    }
                                                });
                                                btn3.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        btn3.setBackgroundColor(Color.rgb(227,71,71));
                                                        startTest.setVisibility(View.VISIBLE);
                                                        startTest.setText("Заново");
                                                        btn2.setEnabled(false);
                                                        btn1.setEnabled(false);
                                                        startTest.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                startTest.setVisibility(View.INVISIBLE);
                                                                btn3.setBackgroundColor(0x00FFFFFF);
                                                                btn2.setEnabled(true);
                                                                btn1.setEnabled(true);
                                                            }
                                                        });
                                                    }
                                                });



                                            }
                                        });
                                    }
                                });
                                btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btn3.setBackgroundColor(Color.rgb(227,71,71));
                                        startTest.setVisibility(View.VISIBLE);
                                        startTest.setText("Заново");
                                        btn2.setEnabled(false);
                                        btn1.setEnabled(false);
                                        startTest.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startTest.setVisibility(View.INVISIBLE);
                                                btn3.setBackgroundColor(0x00FFFFFF);
                                                btn2.setEnabled(true);
                                                btn1.setEnabled(true);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }
}
