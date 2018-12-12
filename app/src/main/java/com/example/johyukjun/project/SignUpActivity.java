package com.example.johyukjun.project;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText m_Id, m_Password, m_Name, m_Mobile, m_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_Id = (EditText) findViewById(R.id.editSId);
        m_Password = (EditText) findViewById(R.id.editSPassword);
        m_Name = (EditText) findViewById(R.id.editSName);
        m_Mobile = (EditText) findViewById(R.id.editSMobileNumber);
        m_Email = (EditText) findViewById(R.id.editSEmail);

    }

    public void mSignUpOnClick(View v)
    {
        Intent intentActivty;
        switch (v.getId())
        {
            case R.id.btnSignUp:
                String id = m_Id.getText().toString();
                String pw = m_Password.getText().toString();
                String name = m_Name.getText().toString();
                String mobile = m_Mobile.getText().toString();
                String email = m_Email.getText().toString();

                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeSingUpXmlStr(id, pw, name, mobile, email);
                    SendThread.mHandler.sendMessage(msg);
                    m_Id.selectAll();
                }


                // 등록 버튼
                intentActivty = new Intent(this, MainActivity.class);
                startActivity(intentActivty);

                break;
            case R.id.btnBack:
                // 취소 버튼
                intentActivty = new Intent(this, MainActivity.class);
                startActivity(intentActivty);
                break;
        }

    }
}
