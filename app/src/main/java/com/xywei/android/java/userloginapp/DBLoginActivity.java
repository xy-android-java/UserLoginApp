package com.xywei.android.java.userloginapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 1，数据库必定会首先被创建
 * 2，onCreate只会被执行一次
 * 需求：
 * 1，启动activity的时候，根据上一次checkbox是否勾选，决定是否填充用户名和密码
 * 2，如果勾选了checkBox，就更新数据库字段isRemenberMe=1
 */
public class DBLoginActivity extends AppCompatActivity {

    private static final String DB_NAME = "user_info.db";

    private EditText dbUsername;
    private EditText dbPassword;
    private Button dbLogin;
    private Button dbCancel;
    private CheckBox dbCheckBox;
    private Button dbCreateDB;
    private Button dbCreateTable;
    private Button dbInsertData;

    private CustomSQLiteOpenHelper customSQLiteOpenHelper;
    private SQLiteDatabase database;
    private static final String SQL_INSERT_USER = "insert into userinfo(username, password,imei,isrememberme) values(?,?,?,?)";
    private static final String SQL_QUERY_USER = "select * from userinfo";

    private String username = null;
    private String password = null;
    private String input_username = null;
    private String input_password = null;
    private int isRemenberme = 0;
    private boolean isFirstLogin = true;
    private boolean loginOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_login);

        dbUsername = findViewById(R.id.db_username);
        dbPassword = findViewById(R.id.db_password);
        dbLogin = findViewById(R.id.db_login);
        dbCancel = findViewById(R.id.db_cancel);
        dbCheckBox = findViewById(R.id.db_checkBox);
        dbCreateDB = findViewById(R.id.db_createDB);
        dbCreateTable = findViewById(R.id.db_createTable);
        dbInsertData = findViewById(R.id.db_insertData);

        //数据库如果存在就直接打开，没有就创建
        customSQLiteOpenHelper = new CustomSQLiteOpenHelper(DBLoginActivity.this, DB_NAME, null, 5);
        //设置打开activity的时候就初始化数据库、表
        database = customSQLiteOpenHelper.getReadableDatabase();
        //有数据就查数据，没有也都是空
        Cursor cursor = database.rawQuery(SQL_QUERY_USER, null);
        //有数据就说明不是首次登陆
        System.out.println("---cursor当前位置" + cursor.getPosition());
        if (cursor.moveToFirst()) {
            isFirstLogin = false;
            username = cursor.getString(cursor.getColumnIndex("username"));
            password = cursor.getString(cursor.getColumnIndex("password"));
            isRemenberme = cursor.getInt(cursor.getColumnIndex("isRememberMe"));
            cursor.close();

        }
        //通过数据库检查用户，IMEI号，列表
        //这段的功能是用户名密码是自动填充还是用自己输入
        //上次记录的是记住用户信息，自动填充数据
        if (isRemenberme == 1) {
            dbUsername.setText(username);
            dbPassword.setText(password);
        }

        dbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_username = dbUsername.getText().toString();
                input_password = dbPassword.getText().toString();
                if (TextUtils.isEmpty(input_username) || TextUtils.isEmpty(input_password)) {
                    Toast.makeText(DBLoginActivity.this, "用户名或者密码输入为空，请重新输入", Toast.LENGTH_LONG).show();
                } else {
                    if (isFirstLogin) {
                        //是首次登陆，直接硬编码比较吧
                        if (input_username.equals("admin") && input_password.equals("123")) {
                            //提示登录成功
                            loginOK = true;
                            //插入数据到数据库
                            database.execSQL("INSERT INTO USERINFO (USERNAME,PASSWORD,IMEI,ISREMEMBERME) VALUES(?,?,?,?)",
                                    new Object[]{input_username, input_password, "999999", dbCheckBox.isChecked()});
                        }

                    } else {
                        //不是首次登陆，直接与数据库的值比较
                        if (input_username.equals(username) && input_password.equals(password)) {
                            //提示登录成功
                            loginOK = true;
                        }

                        database.execSQL("update userinfo set isrememberme=? where username=?", new Object[]{dbCheckBox.isChecked(), input_username});

                    }
                    if (loginOK) {
                        Toast.makeText(DBLoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DBLoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });
        //选择用户、密码


        //登录验证，是否保存


        //退出
        dbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //创建数据库
        dbCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android7之后，无法adb shell 使用root
                //注意readableDatabase与writableDatabase的区别
//                database = customSQLiteOpenHelper.getWritableDatabase();没空间了会报错
                //保证了数据库必定会被先创建
                database = customSQLiteOpenHelper.getReadableDatabase();
                System.out.println("首次打开的时候已创建数据库");
            }
        });


        //创建表
        dbCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database == null || !database.isOpen()) {
                    //保证了数据库必定会被先创建
                    database = customSQLiteOpenHelper.getReadableDatabase();
                    System.out.println("已创建数据库");
                    Toast.makeText(DBLoginActivity.this, "数据库已创建", Toast.LENGTH_LONG).show();
                }
            }
        });

        //插入数据，imei，默认isRememberMe=0
        dbInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = customSQLiteOpenHelper.getReadableDatabase();
                database.execSQL(SQL_INSERT_USER, new String[]{"admin", "123", "999999991001", "0"});
                System.out.println("插入数据成功");
                Toast.makeText(DBLoginActivity.this, "插入数据成功", Toast.LENGTH_LONG).show();
            }
        });


    }

}
