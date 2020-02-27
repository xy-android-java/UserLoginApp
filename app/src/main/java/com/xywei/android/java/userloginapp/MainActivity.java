package com.xywei.android.java.userloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String NAME = "admin";
    public static final String PWD = "123";

    private EditText v_username;
    private EditText v_passworld;
    private Button v_login;
    private Button v_cancel;
    private CheckBox v_checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v_login = findViewById(R.id.login);
        v_cancel = findViewById(R.id.cancel);
        v_username = findViewById(R.id.username);
        v_passworld = findViewById(R.id.password);
        v_checkBox = findViewById(R.id.checkBox);

        //检测是否保存了用户名和密码
        //点击登录事件时候，核对用户名密码是否为空
        v_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = v_username.getText().toString();
                String password = v_passworld.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "用户密码不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    //用户名密码都不空，进行检查身份
                    if (NAME.equals(username) && PWD.equals(password)) {
                        Toast.makeText(MainActivity.this, "用户登录成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
        //核对用户名和密码，如果正确就提示登录，根据checkBox信息决定是否保存

        //点击取消，直接退出
        v_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
