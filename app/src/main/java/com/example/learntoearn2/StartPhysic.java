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

public class StartPhysic extends AppCompatActivity {

    private View first;
    private View second;
    private View third;
    private View fourth;
    private View fifth;
    private TextView physicText;
    private TextView PhysicText1;
    private TextView PhysicText2;
    private TextView PhysicText3;
    private TextView PhysicText4;
    private Button startTest;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TextView a1;
    private SharedPreferences s1;
    private SharedPreferences.Editor e1;
    private ImageButton homeBtn;
    private ImageButton returnBtn;
    int startCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startphysic);
        s1 = getSharedPreferences("Storage", MODE_PRIVATE); //Создаём хранилище
        s1.getInt("progress",MODE_PRIVATE);
        returnBtn = findViewById(R.id.returnbtn);
        homeBtn = findViewById(R.id.homebtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPhysic.this,introduction.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPhysic.this, Education_Site.class);
                startActivity(intent);
            }
        });
        first= findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        fifth=findViewById(R.id.fifth);
        final View arrays[] = new View[]{first, second, third, fourth, fifth};
        physicText = findViewById(R.id.startPhysic);
        physicText.setText(R.string.Text1);
        physicText.setTextColor(Color.BLACK);
        PhysicText1 = findViewById(R.id.startPhysic2);
        PhysicText1.setTextColor(Color.BLACK);
        PhysicText1.setText(R.string.Text2);
        PhysicText2 = findViewById(R.id.startPhysic3);
        PhysicText2.setTextColor(Color.BLACK);
        PhysicText2.setText(R.string.Text3);
        PhysicText3 = findViewById(R.id.startPhysic4);
        PhysicText3.setTextColor(Color.BLACK);
        PhysicText3.setText(R.string.Text4);
        PhysicText4 = findViewById(R.id.startPhysic5);
        PhysicText4.setTextColor(Color.BLACK);
        PhysicText4.setText(R.string.StartPhysic4);
        startTest = findViewById(R.id.startTest);
        btn1 = findViewById(R.id.firstbutton);
        btn2 = findViewById(R.id.secondbutton);
        btn3 = findViewById(R.id.thirdbutton);
        a1 = findViewById(R.id.question);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    e1 = s1.edit();
                    e1.putInt("progress",1);
                    e1.apply();
                    startCount = s1.getInt("progress",0);


                    arrays[0].setBackgroundColor(Color.rgb(46,204,113));                //Перекрашиваем 1 ячейку прогресса в зеленый
                        physicText.setText(null);                                                        //Задаём текст
                        PhysicText1.setText(null);
                        PhysicText2.setText(null);
                        PhysicText3.setText(null);
                        PhysicText4.setText(null);
                        btn1.setText(R.string.q1);
                        btn2.setText(R.string.q2);
                        btn3.setText(R.string.q3);
                        a1.setText(R.string.a1);
                        startTest.setVisibility(View.INVISIBLE);                                    //Пропадает кнопка
                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {                                            //Верный ответ, задний фон закрашивается зеленым цветом, кнопки блокируются
                                btn1.setBackgroundColor(Color.rgb(74,237,143));
                                startTest.setVisibility(View.VISIBLE);
                                startTest.setText("Следующий вопрос");
                                btn2.setEnabled(false);
                                btn3.setEnabled(false);
                                startTest.setOnClickListener(new View.OnClickListener() {           //после того как мы нажмем на кнопку "след.вопрос" вторая ячейка закрашивается, текст на кнопках заменяется
                                    @Override
                                    public void onClick(View v) {
                                        e1 = s1.edit();
                                        e1.putInt("progress",2);
                                        e1.apply();
                                        arrays[1].setBackgroundColor(Color.rgb(46,204,113));
                                        startTest.setVisibility(View.INVISIBLE);
                                        btn1.setBackgroundColor(0x00FFFFFF);
                                        a1.setText(R.string.a2);
                                        btn1.setText(R.string.q4);
                                        btn2.setText(R.string.q5);
                                        btn3.setText(R.string.q6);
                                        btn2.setEnabled(true);
                                        btn3.setEnabled(true);
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
                                                        e1.putInt("progress",3);
                                                        e1.apply();
                                                        arrays[2].setBackgroundColor(Color.rgb(46,204,113));
                                                        startTest.setVisibility(View.INVISIBLE);
                                                        btn3.setBackgroundColor(0x00FFFFFF);
                                                        btn2.setEnabled(true);
                                                        btn1.setEnabled(true);
                                                        btn1.setText(R.string.q7);
                                                        btn2.setText(R.string.q8);
                                                        btn3.setText(R.string.q9);
                                                        a1.setText(R.string.a3);
                                                        btn1.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                btn1.setBackgroundColor(Color.rgb(74,237,143));
                                                                startTest.setVisibility(View.VISIBLE);
                                                                startTest.setText("Следующий вопрос");
                                                                btn2.setEnabled(false);
                                                                btn3.setEnabled(false);
                                                                startTest.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        e1 = s1.edit();
                                                                        e1.putInt("progress",4);
                                                                        e1.apply();
                                                                        arrays[3].setBackgroundColor(Color.rgb(46,204,113));
                                                                        startTest.setVisibility(View.INVISIBLE);
                                                                        btn1.setBackgroundColor(0x00FFFFFF);
                                                                        btn2.setEnabled(true);
                                                                        btn3.setEnabled(true);
                                                                        btn1.setText(R.string.q10);
                                                                        btn2.setText(R.string.q11);
                                                                        btn3.setText(R.string.q12);
                                                                        a1.setText(R.string.a4);
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
                                                                                startTest.setText("Завершить тест");
                                                                                btn1.setEnabled(false);
                                                                                btn3.setEnabled(false);
                                                                                startTest.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        e1 = s1.edit();
                                                                                        e1.putInt("progress",5);
                                                                                        e1.apply();
                                                                                        arrays[4].setBackgroundColor(Color.rgb(46,204,113));
                                                                                        Intent intent = new Intent(StartPhysic.this, introduction.class);
                                                                                        startActivity(intent);
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
                                    }
                                });
                            }
                        });
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btn2.setBackgroundColor(Color.rgb(227,71,71));
                                startTest.setVisibility(View.VISIBLE);
                                startTest.setText("Заново");
                                btn1.setEnabled(false);
                                btn3.setEnabled(false);
                                startTest.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startTest.setVisibility(View.INVISIBLE);
                                        btn2.setBackgroundColor(0x00FFFFFF);
                                        btn1.setEnabled(true);
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

    }







