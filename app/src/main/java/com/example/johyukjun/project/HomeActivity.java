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

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<String> m_Device;
    ArrayAdapter<String> m_Adapter;
    ListView m_ListView;
    Button m_BtnAdd, m_BtnRemove;
    EditText m_SerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_Device = new ArrayList<String>();
        m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, m_Device);

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

        switch (v.getId()) {
            case R.id.btnAddDevice:
                String text = m_SerialNumber.getText().toString();
                if (text.length() != 0) {
                    m_Device.add(text);
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
        }
    }
}
