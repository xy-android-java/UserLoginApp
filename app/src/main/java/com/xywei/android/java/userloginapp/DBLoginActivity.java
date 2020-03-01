package com.xywei.android.java.userloginapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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


        //通过数据库检查用户，IMEI号，列表
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
                System.out.println("已创建数据库");
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
