package com.xywei.android.java.userloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor userEditor = null;
    private String username = null;
    private String password = null;


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
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userEditor = sharedPreferences.edit();

        //检查一下，如果是之前保存了身份，就从文件中读取
        if (sharedPreferences.getInt("isRememberMe", 2) == 1) {
            username = sharedPreferences.getString("username", "");
            password = sharedPreferences.getString("password", "");
            v_username.setText(username);
            v_passworld.setText(password);
        }

        //点击登录事件时候，核对用户名密码是否为空
        v_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = v_username.getText().toString();
                password = v_passworld.getText().toString();

                int isRememberMe = 0;

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "用户密码不能为空！", Toast.LENGTH_LONG).show();
                } else {

                    //用户名密码都不空，进行检查身份
                    if (NAME.equals(username) && PWD.equals(password)) {

                        Toast.makeText(MainActivity.this, "用户登录成功", Toast.LENGTH_LONG).show();

                        //进行保存用户名和密码，如果文件已经存在了，就不用再次保存
                        if (v_checkBox.isChecked()) {
                            isRememberMe = 1;
                            userEditor.putString("username", username);
                            userEditor.putString("password", password);
                        }else {
                            userEditor.clear();
                        }
                        userEditor.putInt("isRememberMe", isRememberMe);
                        userEditor.commit();

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
