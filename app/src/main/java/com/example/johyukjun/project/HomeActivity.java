package com.example.johyukjun.project;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    ArrayList<String> m_Device;
    ArrayAdapter<String> m_Adapter;
    ListView m_ListView;
    Button m_BtnAdd, m_BtnRemove;
    EditText m_SerialNumber;

    // create할때 받은 디바이스 리스트 패킷으로 디바이스 추가와 생성 실행

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // deviceitem타입을 String 타입으로 변경함
        m_Device = new ArrayList<String>();
        m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, m_Device)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.White));
                return view;
            }

        };

        m_ListView = (ListView) findViewById(R.id.listDevice);

        m_ListView.setAdapter(m_Adapter);
        m_ListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        m_BtnAdd = (Button) findViewById(R.id.btnAddDevice);
        m_BtnRemove = (Button) findViewById(R.id.btnRemoveDevice);

        m_SerialNumber = (EditText) findViewById(R.id.editSerialNumber);

        m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //디바이스 리스트 요청
        if (SendThread.mHandler != null) {
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = XmlManager.MakeReqDeviceListXmlStr(MainActivity.GlobalID);
            SendThread.mHandler.sendMessage(msg);


            Log.d(TAG, MainActivity.recvData);
        }
    }

    public void mHomeOnClick (View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnAddDevice:
                String serial = m_SerialNumber.getText().toString();

                deviceItem tempItem = new deviceItem();

                if (serial.length() != 0) {
                    m_Device.add("별명");
                    m_SerialNumber.setText("");
                    tempItem.SetSerialNum(serial);
                    m_Adapter.notifyDataSetChanged();


                    if (SendThread.mHandler != null) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = XmlManager.MakeDeviceXmlStr(tempItem.GetNickName(), tempItem.GetSerialNum(), tempItem.GetAlias());
                        SendThread.mHandler.sendMessage(msg);
                    }
                }
                break;

            case R.id.btnRemoveDevice:
                int pos;
                pos = m_ListView.getCheckedItemPosition();
                if (pos != ListView.INVALID_POSITION) {
                    m_Device.remove(pos);
                    m_ListView.clearChoices();
                    m_Adapter.notifyDataSetChanged();
                }
                break;

            case R.id.btnSelectDevice:
                int selPos;
                selPos = m_ListView.getCheckedItemPosition();
                if (selPos != ListView.INVALID_POSITION) {
                    intent = new Intent(this, Deivce_Activity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btnLogout:
                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeLogoutXmlStr(MainActivity.GlobalID);
                    SendThread.mHandler.sendMessage(msg);

                    // 로그아웃 패킷 받았을 시
                    if(true) {
                        MainActivity.GlobalID = "";
                    }
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}


class deviceItem {
    private String serialNum;
    private String nickName;
    private String alias;

    public deviceItem() {
        serialNum = "";
        nickName = "";
        alias = "";

    }

    public deviceItem(String inSerialNum, String inNickName, String inAlias) {
        this.serialNum = inSerialNum;
        this.nickName = inNickName;
        this.alias = inAlias;
    }

    public String GetSerialNum() {
        return serialNum;
    }

    public String GetNickName() {
        return nickName;
    }

    public String GetAlias() {
        return alias;
    }

    public void SetSerialNum(String inSerialNum) {
        serialNum = inSerialNum;

        return;
    }

    public void SetNickName(String inNickName) {
        nickName = inNickName;

        return;
    }
}
