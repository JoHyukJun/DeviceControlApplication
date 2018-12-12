package com.example.johyukjun.project;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeManagerActivity extends AppCompatActivity {
    EditText m_SerialNumber, m_DeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manager);

        m_SerialNumber = (EditText) findViewById(R.id.editMSerialNumber);
        m_DeviceName = (EditText) findViewById(R.id.editMDeviceName);
    }


    public void mHomeMgrOnClick (View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnMAddDevice:
                String serial = m_SerialNumber.getText().toString();
                String alias = m_DeviceName.getText().toString();

                deviceItem tempItem = new deviceItem();

                tempItem.SetSerialNum(serial);
                tempItem.SetAlias(alias);


                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeDeviceXmlStr(MainActivity.GlobalID, tempItem.GetSerialNum(), tempItem.GetAlias());
                    SendThread.mHandler.sendMessage(msg);
                }

                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

                break;

            case R.id.btnMCancel:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

                break;

        }
    }

}