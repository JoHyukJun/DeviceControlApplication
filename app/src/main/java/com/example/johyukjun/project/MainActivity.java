package com.example.johyukjun.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText m_Id, m_Password;
    private Button m_BtnLogIn, m_BtnSignUp;
    private NetworkManager m_networkmanager;
    //private XmlManager m_xmlmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        m_BtnLogIn = (Button) findViewById(R.id.btnLogIn);
        m_BtnSignUp = (Button) findViewById(R.id.btnSignUpActivity);

        m_Id = (EditText) findViewById(R.id.editId);
        m_Password = (EditText) findViewById(R.id.editPassword);

        m_networkmanager = NetworkManager.getInstance();

    }

    public void mOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnLogIn:
                String id = m_Id.getText().toString();
                String pw = m_Password.getText().toString();

                Log.d(TAG, "mOnClick()");
                //이곳 주석을 풀고 실험해볼것
                // 네트워크 연결은 메인 쓰레드에서 할 수 없음
                m_networkmanager.setFunc(NetType.SOCKET);
                m_networkmanager.execute();

                if (id.equals("jo") && pw.equals("0000")) {
                    intent = new Intent(this, HomeManagerActivity.class);
                    startActivity(intent);
                    //이곳 주석 풀면 sdcard 있는 디렉토리에 xml파일 생성
                    //XmlManager.MakeXmlFIle(id, pw);

                    //이곳 주석을 풀고 실험해볼것
                    m_networkmanager.setUserInfo(id, pw);
                    m_networkmanager.execute();
                }
                else {
                    intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btnSignUpActivity:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
