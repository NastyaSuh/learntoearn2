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

public class Introduction3 extends AppCompatActivity {
    private View first;
    private View second;
    private View third;
    private View fourth;
    private View fifth;
    private  View six;
    private TextView Text1;
    private TextView Text2;
    private TextView Text3;
    private TextView Text4;
    private Button startTest;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TextView a1;
    private SharedPreferences s1;
    private SharedPreferences.Editor e1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction3);
        s1 = getSharedPreferences("Storage", MODE_PRIVATE);
        s1.getInt("progress2",MODE_PRIVATE);

        a1 = findViewById(R.id.question);
        Text1 = findViewById(R.id.Text1);
        Text1.setText(R.string.Text7);
        Text2 = findViewById(R.id.Text2);
        Text2.setText(R.string.Text8);
        Text3 = findViewById(R.id.Text3);
        Text3.setText(R.string.Text9);
        Text4 = findViewById(R.id.Text4);
        Text4.setText(R.string.Text10);
        btn1 = findViewById(R.id.firstbutton);
        btn2 = findViewById(R.id.secondbutton);
        btn3 = findViewById(R.id.thirdbutton);
        first= findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        six = findViewById(R.id.six);
        final View arrays[] = new View[]{first, second, third, fourth,fifth,six};
        startTest = findViewById(R.id.startTest);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            e1 = s1.edit();
            e1.putInt("progress2",1);
            e1.apply();
            arrays[0].setBackgroundColor(Color.rgb(46,204,113));
            startTest.setVisibility(View.INVISIBLE);
            Text1.setText(null);
            Text2.setText(null);
            Text3.setText(null);
            Text4.setText(null);
            a1.setText(R.string.a8);
            btn1.setText(R.string.q22);
            btn2.setText(R.string.q23);
            btn3.setText(R.string.q24);
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
                            startTest.setVisibility(View.INVISIBLE);
                            btn2.setEnabled(true);
                            btn3.setEnabled(true);
                            btn1.setBackgroundColor(0x00FFFFFF);
                            e1 = s1.edit();
                            e1.putInt("progress2",2);
                            e1.apply();
                            arrays[1].setBackgroundColor(Color.rgb(46,204,113));
                            btn1.setText(R.string.q25);
                            btn2.setText(R.string.q26);
                            btn3.setText(R.string.q27);
                            a1.setText(R.string.a9);
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
                                            startTest.setVisibility(View.INVISIBLE);
                                            btn2.setEnabled(true);
                                            btn3.setEnabled(true);
                                            btn1.setBackgroundColor(0x00FFFFFF);
                                            arrays[2].setBackgroundColor(Color.rgb(46,204,113));
                                            e1 = s1.edit();
                                            e1.putInt("progress2",3);
                                            e1.apply();
                                            btn1.setText(R.string.q28);
                                            btn2.setText(R.string.q29);
                                            btn3.setText(R.string.q30);
                                            a1.setText(R.string.a10);
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    btn1.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                                    startTest.setVisibility(View.VISIBLE);
                                                    startTest.setText("Заново");
                                                    btn2.setEnabled(false);
                                                    btn3.setEnabled(false);
                                                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                                        @Override
                                                        public void onClick(View v) {
                                                            btn1.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                            btn2.setEnabled(true);                                      //разблокируем кнопки
                                                            btn3.setEnabled(true);

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
                                                            btn1.setEnabled(true);
                                                            btn3.setEnabled(true);
                                                            startTest.setVisibility(View.INVISIBLE);
                                                            btn2.setBackgroundColor(0x00FFFFFF);
                                                            arrays[3].setBackgroundColor(Color.rgb(46,204,113));
                                                            e1 = s1.edit();
                                                            e1.putInt("progress2",4);
                                                            e1.apply();
                                                            btn1.setText(R.string.q31);
                                                            btn2.setText(R.string.q32);
                                                            btn3.setText(R.string.q33);
                                                            a1.setText(R.string.a12);
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
                                                                            btn2.setEnabled(true);
                                                                            btn3.setEnabled(true);
                                                                            startTest.setVisibility(View.INVISIBLE);
                                                                            btn1.setBackgroundColor(0x00FFFFFF);
                                                                            arrays[4].setBackgroundColor(Color.rgb(46,204,113));
                                                                            e1 = s1.edit();
                                                                            e1.putInt("progress2",5);
                                                                            e1.apply();
                                                                            a1.setText(R.string.a13);
                                                                            btn1.setText(R.string.q34);
                                                                            btn2.setText(R.string.q35);
                                                                            btn3.setText(R.string.q36);
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
                                                                                            btn2.setEnabled(true);
                                                                                            btn3.setEnabled(true);
                                                                                            startTest.setVisibility(View.INVISIBLE);
                                                                                            btn2.setBackgroundColor(0x00FFFFFF);
                                                                                            arrays[5].setBackgroundColor(Color.rgb(46,204,113));
                                                                                            e1 = s1.edit();
                                                                                            e1.putInt("progress2",6);
                                                                                            e1.apply();
                                                                                            Intent intent = new Intent(Introduction3.this,introduction.class);
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
                                                                                    btn3.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                                                                    startTest.setVisibility(View.VISIBLE);
                                                                                    startTest.setText("Заново");
                                                                                    btn1.setEnabled(false);
                                                                                    btn2.setEnabled(false);
                                                                                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                                                                        @Override
                                                                                        public void onClick(View v) {
                                                                                            btn3.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                                                            btn2.setEnabled(true);                                      //разблокируем кнопки
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
                                                                    btn3.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                                                    startTest.setVisibility(View.VISIBLE);
                                                                    startTest.setText("Заново");
                                                                    btn1.setEnabled(false);
                                                                    btn2.setEnabled(false);
                                                                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            btn3.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                                            btn1.setEnabled(true);                                      //разблокируем кнопки
                                                                            btn2.setEnabled(true);

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
                                                    btn3.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                                    startTest.setVisibility(View.VISIBLE);
                                                    startTest.setText("Заново");
                                                    btn1.setEnabled(false);
                                                    btn2.setEnabled(false);
                                                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                                        @Override
                                                        public void onClick(View v) {
                                                            btn3.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                                            btn1.setEnabled(true);                                      //разблокируем кнопки
                                                            btn2.setEnabled(true);

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
                                    btn3.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                                    startTest.setVisibility(View.VISIBLE);
                                    startTest.setText("Заново");
                                    btn2.setEnabled(false);
                                    btn1.setEnabled(false);
                                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                                        @Override
                                        public void onClick(View v) {
                                            btn3.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                                            btn2.setEnabled(true);                                      //разблокируем кнопки
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
                    btn3.setBackgroundColor(Color.rgb(227,71,71));  //При неверном ответе задний фон кнопки становится красным, всплывает кномка заново, а другие ответы блокируются
                    startTest.setVisibility(View.VISIBLE);
                    startTest.setText("Заново");
                    btn2.setEnabled(false);
                    btn1.setEnabled(false);
                    startTest.setOnClickListener(new View.OnClickListener() {           //переопределяем кнопку
                        @Override
                        public void onClick(View v) {
                            btn3.setBackgroundColor(0x00FFFFFF);                        //удаляем фон
                            btn2.setEnabled(true);                                      //разблокируем кнопки
                            btn1.setEnabled(true);

                        }
                    });
                }
            });

            }
        });

    }
}


