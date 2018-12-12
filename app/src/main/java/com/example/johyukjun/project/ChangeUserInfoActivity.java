package com.example.johyukjun.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChangeUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
    }

    public void mOnclick(View v)
    {
        Intent intentActivty;
        switch (v.getId())
        {
            case R.id.btnChange:
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
