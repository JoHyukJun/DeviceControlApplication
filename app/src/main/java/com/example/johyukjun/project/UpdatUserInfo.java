package com.example.johyukjun.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class UpdatUserInfo extends AppCompatActivity {

    private EditText m_CPassword, m_CName, m_CMobile, m_CEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updat_user_info);

        m_CPassword = (EditText) findViewById(R.id.editCPassword);
        m_CName = (EditText) findViewById(R.id.editCName);
        m_CMobile = (EditText) findViewById(R.id.editCMobileNumber);
        m_CEmail = (EditText) findViewById(R.id.editCEmail);
    }

    public void mUCOnClick(View v)
    {
        Intent intentActivty;
        switch (v.getId())
        {
            case R.id.btnChange:
                String pw = m_CPassword.getText().toString();
                String name = m_CName.getText().toString();
                String mobile = m_CMobile.getText().toString();
                String email = m_CEmail.getText().toString();


                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeChangeUserInfoXmlStr(MainActivity.GlobalID, pw, name, mobile, email);
                    SendThread.mHandler.sendMessage(msg);
                }

                // 변경 버튼
                intentActivty = new Intent(this, HomeActivity.class);
                startActivity(intentActivty);
                break;
            case R.id.btnCancel:
                // 취소 버튼
                intentActivty = new Intent(this, HomeActivity.class);
                startActivity(intentActivty);
                break;

        }

    }
}
