package com.example.johyukjun.project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.security.AccessController.getContext;

public class Deivce_Activity extends AppCompatActivity {

    Intent intentActivty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deivce_);

        intentActivty = getIntent();

        // 패킷을 받아서

        // 버튼에 관한 디바이스일 경우
        if(true)
        {
            CreateButton();
        }
        // 거리측정기인 경우 텍스트뷰를 만들고 주기적으로 업데이트 해야 함
        else
        {
            CreateTextView();
        }
    }

    private void CreateButton() {

        //원래 있던 레이아웃 가져오기
        final RelativeLayout rm = (RelativeLayout) findViewById(R.id.con);

        // 새로 만들 relative layout 파라미터 정의
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT);
        // 새로 relative layout 생성
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(params);


        // textview 파라미터 설정
        RelativeLayout.LayoutParams tvControl = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        /*해당 margin값 변경*/
        tvControl.bottomMargin = 100;
//        tvControl.topMargin = 10;
        // TextView 생성
        TextView tvProdc = new TextView(this);
        tvProdc.setId(R.id.device_text_view);
        tvProdc.setText("DeviceName");
        tvProdc.setTextColor(getResources().getColor(R.color.White));
        /*변경된 값의 파라미터를 해당 레이아웃 파라미터 값에 셋팅*/
        tvProdc.setLayoutParams(tvControl);
        tvProdc.setTextSize(40);
        rl.addView(tvProdc);

        // 버튼 생성
        final Button ledonbtn = new Button(this);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params2.addRule(RelativeLayout.BELOW, tvProdc.getId());
        params2.width = 400;
        // setId 버튼에 대한 키값
        ledonbtn.setId(R.id.device_ledon_button);
        ledonbtn.setText("LED ON");


        ledonbtn.setLayoutParams(params2);

        ledonbtn.setBackground(getResources().getDrawable(R.drawable.button_default));
        ledonbtn.setMinimumWidth(0);
        //btn.setHeight(WRAP_CONTENT);
        ledonbtn.setTextColor(getResources().getColor(R.color.White));
        ledonbtn.setTextSize(20);

        final int position = 1;

        ledonbtn.setOnClickListener(new View.OnClickListener() {

            // 이 안에 클릭했을 경우 발생하는 이벤트
            public void onClick(View v) {
                Log.d("log", "position :" + position);
                Toast.makeText(getApplicationContext(), "클릭한 position:" + position, Toast.LENGTH_LONG).show();
                // LED_ON과 같은 packet 만들어서 보내기

                if (SendThread.mHandler != null) {
                    String tempSerial = intentActivty.getStringExtra("serial");

                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeCtrlDeviceXmlStr(MainActivity.GlobalID, tempSerial, "LED_ON");
                    SendThread.mHandler.sendMessage(msg);
                }
            }

        });

        //버튼 add
        rl.addView(ledonbtn);

// 버튼 생성
        final Button ledoffbtn = new Button(this);

        RelativeLayout.LayoutParams params2_1 = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params2_1.topMargin = 150;
        params2_1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params2_1.addRule(RelativeLayout.BELOW, ledonbtn.getId());
        params2_1.width = 400;
        // setId 버튼에 대한 키값
        ledoffbtn.setId(R.id.device_ledoff_button);
        ledoffbtn.setText("LED OFF");


        ledoffbtn.setLayoutParams(params2_1);

        ledoffbtn.setBackground(getResources().getDrawable(R.drawable.button_default));
        ledoffbtn.setMinimumWidth(0);
        //btn.setHeight(WRAP_CONTENT);
        ledoffbtn.setTextColor(getResources().getColor(R.color.White));
        ledoffbtn.setTextSize(20);

        final int position_2 = 1;

        ledoffbtn.setOnClickListener(new View.OnClickListener() {

            // 이 안에 클릭했을 경우 발생하는 이벤트
            public void onClick(View v) {
                Log.d("log", "position :" + position_2);
                Toast.makeText(getApplicationContext(), "클릭한 position:" + position_2, Toast.LENGTH_LONG).show();
                // LED_ON과 같은 packet 만들어서 보내기
            }

        });

        //버튼 add
        rl.addView(ledoffbtn);


        final Button btnback = new Button(this);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(180, WRAP_CONTENT);
        params3.topMargin = 300;
        params3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params3.addRule(RelativeLayout.BELOW, ledoffbtn.getId());
        params3.width = 400;
        // setId 버튼에 대한 키값
        btnback.setId(R.id.back_button);
        btnback.setText("뒤로");

        btnback.setLayoutParams(params3);


        btnback.setBackground(getResources().getDrawable(R.drawable.button_default));

        btnback.setTextColor(getResources().getColor(R.color.White));
        btnback.setTextSize(20);

        final int position2 = 2;

        btnback.setOnClickListener(new View.OnClickListener() {

            // 이 안에 클릭했을 경우 발생하는 이벤트
            public void onClick(View v) {
                Log.d("log", "position :" + position2);
                Toast.makeText(getApplicationContext(), "클릭한 position:" + position2, Toast.LENGTH_LONG).show();
                // 뒤로 가기
                intentActivty = new Intent(Deivce_Activity.this,HomeActivity.class);
                startActivity(intentActivty);
            }
        });
        //버튼 add
        rl.addView(btnback);




        //LinearLayout 정의된거 add
        rm.addView(rl);

    }

    private void CreateTextView() {
        //원래 있던 레이아웃 가져오기
        final RelativeLayout rm = (RelativeLayout) findViewById(R.id.con);

        // 새로 만들 relative layout 파라미터 정의
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                WRAP_CONTENT, WRAP_CONTENT);
        // 새로 relative layout 생성
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(params);


        // textview 파라미터 설정
        RelativeLayout.LayoutParams tvControl = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        /*해당 margin값 변경*/
        tvControl.bottomMargin = 100;
//        tvControl.topMargin = 10;
        // TextView 생성
        TextView tvProdc = new TextView(this);
        tvProdc.setId(R.id.device_text_view);
        tvProdc.setText("DeviceName");
        tvProdc.setTextColor(getResources().getColor(R.color.White));
        /*변경된 값의 파라미터를 해당 레이아웃 파라미터 값에 셋팅*/
        tvProdc.setLayoutParams(tvControl);
        tvProdc.setTextSize(40);
        rl.addView(tvProdc);



        // textview 파라미터 설정
        RelativeLayout.LayoutParams tvparam = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        tvparam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tvparam.addRule(RelativeLayout.BELOW, tvProdc.getId());
        /*해당 margin값 변경*/

        // TextView 생성
        TextView tv = new TextView(this);
        tv.setId(R.id.device_tvValue);
        tv.setText("value");
        tv.setTextColor(getResources().getColor(R.color.White));
        /*변경된 값의 파라미터를 해당 레이아웃 파라미터 값에 셋팅*/
        tv.setLayoutParams(tvparam);
        tv.setTextSize(40);
        rl.addView(tv);


        final Button btnback = new Button(this);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(180, WRAP_CONTENT);
        params3.topMargin = 300;
        params3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params3.addRule(RelativeLayout.BELOW, tv.getId());
        params3.width = 400;
        // setId 버튼에 대한 키값
        btnback.setId(R.id.back_button);
        btnback.setText("뒤로");

        btnback.setLayoutParams(params3);


        btnback.setBackground(getResources().getDrawable(R.drawable.button_default));

        btnback.setTextColor(getResources().getColor(R.color.White));
        btnback.setTextSize(20);

        final int position2 = 2;

        btnback.setOnClickListener(new View.OnClickListener() {

            // 이 안에 클릭했을 경우 발생하는 이벤트
            public void onClick(View v) {
                Log.d("log", "position :" + position2);
                Toast.makeText(getApplicationContext(), "클릭한 position:" + position2, Toast.LENGTH_LONG).show();
                // 뒤로 가기
                intentActivty = new Intent(Deivce_Activity.this,HomeActivity.class);
                startActivity(intentActivty);
            }

        });
        //버튼 add
        rl.addView(btnback);




        //LinearLayout 정의된거 add
        rm.addView(rl);

    }

    private void CreateBackButton()
    {


    }
}