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

    public static String fullData;
    public static String recvData;

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

                // 첫바퀴에는 recvData가 null이어서 돌지 않음
                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeLoginXmlStr(id, pw);
                    SendThread.mHandler.sendMessage(msg);

                }

                Log.d(TAG, "받은 데이터 구현!!!" + recvData);

                while(true) {
                    if(recvData == null)
                    {
                        break;
                    }
                    else {
                        if (XmlManager.ParseLoginXmlStr(recvData).equals("done")) {
                            GlobalID = id;
                            intentActivty = new Intent(this, HomeActivity.class);
                            startActivity(intentActivty);
                        }
                        break;
                    }
                }
                    m_Id.selectAll();

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
                case 2:
                    recvData = msg.obj.toString();

                    Log.d(TAG, "받은 데이터 구현!!!" + recvData);
                    String pacTpye = "";
                    pacTpye = XmlManager.ParsePre(recvData);

                    if (recvData.indexOf("Serial") > -1) {
                        deviceItem tempItem = new deviceItem();

                        Vector<String[]> temp = XmlManager.ParseDeviceListXmlStr(MainActivity.recvData);

                        String [] devarr;
                        for(int i = 0; i < temp.size(); i++)
                        {
                            devarr = temp.get(i);
                            tempItem.SetSerialNum(devarr[0]);
                            tempItem.SetAlias(devarr[1]);
                            HomeActivity.m_Device.add(tempItem);
                        }

                        HomeActivity.m_Adapter.notifyDataSetChanged();
                    }
                    else if (recvData.indexOf("Status data") > -1) {

                }

                    break;

                case 1:
                    fullData = msg.obj.toString();

                    Log.d(TAG, "FULL DATA" + fullData);
                    break;
                case 0:
                    fullData = msg.obj.toString();
                    break;
            }
        }
    };
}