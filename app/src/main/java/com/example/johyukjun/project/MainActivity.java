package com.example.johyukjun.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText m_Id, m_Password;
    private Button m_BtnLogIn, m_BtnSignUp;
    public static ClientThread mClientThread;

    public static String xmlData;

    public static String GlobalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        m_BtnLogIn = (Button) findViewById(R.id.btnLogIn);
        m_BtnSignUp = (Button) findViewById(R.id.btnSignUpActivity);

        m_Id = (EditText) findViewById(R.id.editId);
        m_Password = (EditText) findViewById(R.id.editPassword);

        mClientThread = new ClientThread(mMainHandler);
        mClientThread.start();
    }

    public void mOnClick(View v) {
        Intent intent;
        String tempMsg;
        Vector<deviceItem> tempdev = new Vector<deviceItem>();
        Intent intentActivty;

        switch (v.getId()) {
            case R.id.btnLogIn:
                String id = m_Id.getText().toString();
                String pw = m_Password.getText().toString();

                Log.d(TAG, "case btnLogIn");

                if(mClientThread == null) {
                    mClientThread = new ClientThread(mMainHandler);
                    mClientThread.start();
                }

                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeLoginXmlStr(id, pw);
                    SendThread.mHandler.sendMessage(msg);

                    Log.d(TAG, xmlData);

                    if (XmlManager.ParseLoginXmlStr(xmlData) == "complete") {
                        GlobalID = id;

                        //intentActivty = new Intent(this, HomeActivity.class);
                        //startActivity(intentActivty);
                    }
                    intentActivty = new Intent(this, HomeActivity.class);
                    startActivity(intentActivty);

                    m_Id.selectAll();
                }

//                if (XmlManager.ParseLoginXmlStr("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>") == "done") {
//                    GlobalID = id;
//
//                    intentActivty = new Intent(this, HomeActivity.class);
//                    startActivity(intentActivty);
//                }
                // 디바이스 선택 후에는 주기적으로 패킷을 보내서 디바이스 상태를 받음

                break;

            case R.id.btnSignUpActivity:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    xmlData = msg.obj.toString();
                    Log.d(TAG, xmlData);
                    break;
            }
        }
    };
}