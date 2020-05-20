package com.example.sf.bdoudizhu.Class;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sf.bdoudizhu.SQLite.DatabaseHelper;
import java.util.ArrayList;


public class UserService {
    private ArrayList<String> usernameList = new ArrayList<>();
    private ArrayList<String> userpassList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private String s = null;

    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    //登录用
    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }

    //注册用
    public boolean register(User user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(username,password) values(?,?)";
        Object obj[]={user.getUsername(),user.getPassword()};
        sdb.execSQL(sql, obj);
        return true;
    }

    public boolean jiance1(String username){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    public boolean jiance2(String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
//    public ArrayList<String> getdata(){
//
//        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
//        Cursor cursor = sdb.query ("user",null,null,null,null,null,null);
//        if(cursor.moveToFirst()) {
//            do{
//                userpassList.add(cursor.getString(2));
//            }while(cursor.moveToNext());
//            cursor.close();
//        }
//        return userpassList;
//    }

    public ArrayList<String> getAll() {//更新
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        Cursor cursor = sdb.query ("user",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do{
                usernameList.add(cursor.getString(1));
            }while(cursor.moveToNext());
            cursor.close();
        }

        return usernameList;

    }


}
