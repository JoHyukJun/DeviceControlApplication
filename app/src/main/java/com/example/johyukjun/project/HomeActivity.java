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
    public static ArrayList<deviceItem> m_Device;
    public static ArrayAdapter<deviceItem> m_Adapter;
    ListView m_ListView;
    Button m_BtnAdd, m_BtnRemove;

    // create할때 받은 디바이스 리스트 패킷으로 디바이스 추가와 생성 실행

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // deviceitem타입을 String 타입으로 변경함
        m_Device = new ArrayList<deviceItem>();
        m_Adapter = new ArrayAdapter<deviceItem>(this, android.R.layout.simple_list_item_single_choice, m_Device) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
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
        }
    }

    public void mHomeOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnAddDevice:

                intent = new Intent(this, HomeManagerActivity.class);
                startActivity(intent);

                break;

            case R.id.btnRemoveDevice:
                int pos;
                pos = m_ListView.getCheckedItemPosition();
                deviceItem selc = m_Device.get(pos);

                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeRvItemXmlStr(MainActivity.GlobalID, selc.GetSerialNum());
                    SendThread.mHandler.sendMessage(msg);
                }

                if (pos != ListView.INVALID_POSITION) {
                    m_Device.remove(pos);
                    m_ListView.clearChoices();
                    m_Adapter.notifyDataSetChanged();
                }
                break;

            case R.id.btnSelectDevice:
                int selPos;
                selPos = m_ListView.getCheckedItemPosition();
                deviceItem selItem = m_Device.get(selPos);

                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeSelDeviceXmlStr(MainActivity.GlobalID, selItem.GetSerialNum());
                    SendThread.mHandler.sendMessage(msg);
                }


                if (selPos != ListView.INVALID_POSITION) {
                    intent = new Intent(this, Deivce_Activity.class);
                    intent.putExtra("serial", selItem.GetSerialNum());
                    startActivity(intent);
                }
                break;

            case R.id.btnChangeUserInfo:
                intent = new Intent(this, UpdatUserInfo.class);
                startActivity(intent);

                break;

            case R.id.btnLogout:
                if (SendThread.mHandler != null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = XmlManager.MakeLogoutXmlStr(MainActivity.GlobalID);
                    SendThread.mHandler.sendMessage(msg);

                    // 로그아웃 패킷 받았을 시
                    if (true) {
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
    private String alias;

    public deviceItem() {
        serialNum = "";
        alias = "";

    }

    public deviceItem(String inSerialNum, String inAlias) {
        this.serialNum = inSerialNum;
        this.alias = inAlias;
    }

    public String GetSerialNum() {
        return serialNum;
    }

    public String GetAlias() {
        return alias;
    }

    public void SetSerialNum(String inSerialNum) {
        serialNum = inSerialNum;

        return;
    }

    public void SetAlias(String inAlias) {
        alias = inAlias;

        return;
    }
}

