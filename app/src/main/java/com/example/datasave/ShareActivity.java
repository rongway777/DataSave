package com.example.datasave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    public static final String TAG = "ShareActivity";
    private EditText editUserName,editUserPwd;
    private Button buttonLogin;
    private String userName,userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        editUserName = findViewById(R.id.editTextName);
        editUserPwd = findViewById(R.id.editTextpwd);
        buttonLogin = findViewById(R.id.buttonLogin);

        //从SharedPreference提取数据
        SharedPreferences share = getSharedPreferences("myshare",MODE_PRIVATE);

        //key不存在，返回第二个参数。
        String name = share.getString("user","");
        String pwd = share.getString("pwd","");
        if(!(name.equals("") || pwd.equals(""))){
            editUserName.setText(name);
            editUserPwd.setText(pwd);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                userName = editUserName.getText().toString();
                userPwd = editUserPwd.getText().toString();
                Log.i(TAG, "onClick: \n" + "user:"+userName + "\npwd:" + userPwd);

                //验证
                if(userName.equals("admin") && userPwd.equals("admin")){

                    //存储信息到SharePreference  data/data/com.xx.xx.xx/share_prefs/xxx.xml
                    //默认为xml文件，不要加后缀。如果存在sharePreference文件，获取，否则创建xxx.xml。
                    //参数1：文件名。参数2：模式。
                    SharedPreferences share = getSharedPreferences("myshare",MODE_PRIVATE);
                    //获取editor对象
                    SharedPreferences.Editor editor = share.edit();
                    //存入数据
                    //key value,key是唯一的。存在覆盖。
                    editor.putString("user",userName);
                    editor.putString("pwd",userPwd);
                    //提交
                    editor.commit();

                    Toast.makeText(ShareActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShareActivity.this,"账号或者密码错误！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}