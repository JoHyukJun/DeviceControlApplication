package com.example.johyukjun.project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Deivce_Activity extends AppCompatActivity {

    int idindex = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deivce_);

//        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//
//        LinearLayout mRootlinear = (LinearLayout) findViewById(R.id.con);
//        mInflater.inflate(R.layout.sub, mRootlinear, true);

        // 버튼에 관한 디바이스일 경우
        if(true)
        {
            CreateButton();
        }
        // 거리측정기인 경우 텍스트뷰를 만들고 주기적으로 업데이트 해야 함
        else if(false)
        {


        }
    }

    private void CreateButton()
    {

        final LinearLayout lm = (LinearLayout) findViewById(R.id.con);

        // linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            // LinearLayout 생성
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);


//            // TextView 생성
//            TextView tvProdc = new TextView(this);
//            tvProdc.setText("Name" + "1" + " ");
//            ll.addView(tvProdc);
//
//
//            // TextView 생성
//            TextView tvAge = new TextView(this);
//            tvAge.setText("   Age" + "1" + "  ");
//            ll.addView(tvAge);


            // 버튼 생성
            final Button btn = new Button(this);
            // setId 버튼에 대한 키값
            btn.setId(idindex);
            btn.setText("Apply");
            btn.setLayoutParams(params);

            final int position = 1;

            btn.setOnClickListener(new View.OnClickListener() {

                // 이 안에 클릭했을 경우 발생하는 이벤트
                public void onClick(View v) {
                    Log.d("log", "position :" + position);
                    Toast.makeText(getApplicationContext(), "클릭한 position:" + position, Toast.LENGTH_LONG).show();
                    // LED_ON과 같은 packet 만들어서 보내기
                }

            });
            //버튼 add
            ll.addView(btn);

            //LinearLayout 정의된거 add
            lm.addView(ll);

            idindex++;
    }

}