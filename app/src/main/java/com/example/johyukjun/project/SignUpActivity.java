package com.example.johyukjun.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void mOnclick(View v)
    {
        Intent intentActivty;
        switch (v.getId())
        {
            case R.id.btnSignUp:
                // 등록 버튼
                intentActivty = new Intent(this, MainActivity.class);
                startActivity(intentActivty);
                break;
            case R.id.btnMCancel:
                // 취소 버튼
                intentActivty = new Intent(this, MainActivity.class);
                startActivity(intentActivty);
                break;
        }

    }
}
