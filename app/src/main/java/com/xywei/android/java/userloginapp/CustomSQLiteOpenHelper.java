package com.xywei.android.java.userloginapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 1，oncreate()，新安装的APP首次运行的时候执行，执行一次
 * 2，onUpgrade()，数据库版本大于老版本的时候执行，执行一次
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_USER = "CREATE TABLE userinfo (" +
            "id integer  primary key autoincrement," +
            "username text not null, " +
            "password text not null," +
            "imei text not null," +
            "isRememberMe integer not null)";

    private static final String SQL_DROP_USER = "DROP TABLE  IF EXISTS userinfo";

    private Context myContext;

    /**
     * @param context 直接当前 this
     * @param name    数据库名字
     * @param factory 一般是null
     * @param version 数据库版本号
     */
    public CustomSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public String getDatabaseName() {
        System.out.println("----getDatabaseName");
        return super.getDatabaseName();
    }

    @Override
    public synchronized void close() {
        System.out.println("----close");
        super.close();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        System.out.println("----onConfigure");
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        System.out.println("----onCreate创建数据表成功，首次运行程序的时候执行");
        Toast.makeText(myContext, "创建数据表成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_USER);
        onCreate(db);
        System.out.println("----onUpgrade");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("----onDowngrade");
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        System.out.println("----onOpen");
        super.onOpen(db);
    }
}
