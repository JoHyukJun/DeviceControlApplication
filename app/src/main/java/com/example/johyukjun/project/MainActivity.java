package com.example.johyukjun.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnLogIn:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.btnSignUpActivity:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
