package com.example.sf.bdoudizhu.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sf.bdoudizhu.Class.User;
import com.example.sf.bdoudizhu.Class.UserService;
import com.example.sf.bdoudizhu.R;

import java.util.ArrayList;


public class RegisterActivity extends Activity {
    private ArrayList<String> usernamelList;
//    private ArrayList<String> userpassList;
    EditText username;
    EditText password;
    Button register;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                UserService uService=new UserService(RegisterActivity.this);
                User user=new User();
                user.setUsername(name);
                user.setPassword(pass);
                usernamelList = uService.getAll();
                boolean flag1=uService.jiance1(name);
                boolean flag2=uService.jiance1(pass);
                if(name.equals("")||pass.equals("")){
                    Toast.makeText(RegisterActivity.this, "账号或密码不能为空", Toast.LENGTH_LONG).show();
                }
                else if(flag1||flag2){
                    Toast.makeText(RegisterActivity.this,"你输入的账号或密码已经被注册!",Toast.LENGTH_LONG).show();
                }
                else{
                    uService.register(user);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void findViews() {
        username=(EditText) findViewById(R.id.usernameRegister);
        password=(EditText) findViewById(R.id.passwordRegister);
        register=(Button) findViewById(R.id.Register);
    }

}
