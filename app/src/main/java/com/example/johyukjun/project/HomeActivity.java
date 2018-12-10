package com.example.johyukjun.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<deviceItem> m_Device;
    ArrayAdapter<deviceItem> m_Adapter;
    ListView m_ListView;
    Button m_BtnAdd, m_BtnRemove;
    EditText m_SerialNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_Device = new ArrayList<deviceItem>();
        m_Adapter = new ArrayAdapter<deviceItem>(this, android.R.layout.simple_list_item_single_choice, m_Device);

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
    }

    public void mOnClick (View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnAddDevice:
                String text = m_SerialNumber.getText().toString();
                if (text.length() != 0) {
                    m_Device.add(new deviceItem(text, ""));
                    m_SerialNumber.setText("");
                    m_Adapter.notifyDataSetChanged();
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
        }
    }

}


class deviceItem {
    private String serialNum;
    private String nickName;

    public deviceItem() {

    }

    public deviceItem(String inSerialNum, String inNickName) {
        this.serialNum = inSerialNum;
        this.nickName = inNickName;
    }

    public String GetSerialNum() {
        return serialNum;
    }

    public String GetNickName() {
        return nickName;
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
