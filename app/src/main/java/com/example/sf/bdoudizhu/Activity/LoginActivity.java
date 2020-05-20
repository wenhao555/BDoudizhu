package com.example.sf.bdoudizhu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.sf.bdoudizhu.Class.JsonParser;
import com.example.sf.bdoudizhu.Class.UserService;
import com.example.sf.bdoudizhu.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> usernamelList;
    private Button bt_login, bt_register;
    private ImageButton image_btn;
    private EditText zhanghao;
    private EditText mima;
    private UserService uService = null;
    private ListPopupWindow listPopupWindow;
    private ImageView xf_account, xf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initSpeech();
    }

    private Toast mToast;

    private void initSpeech() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5ec22eb1");
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    private RecognizerDialog mIatDialog;

    private void speechDialog() {
        mIatDialog = new RecognizerDialog(LoginActivity.this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
        showTip("开始语音转换");
    }

    private void speechDialog2() {
        mIatDialog = new RecognizerDialog(LoginActivity.this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener2);
        mIatDialog.show();
        showTip("开始语音转换");
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {

            printResult(results);

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));

        }

    };
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener2 = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {

            printResult2(results);

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));

        }

    };
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        zhanghao.setText(resultBuffer.toString());
        zhanghao.setSelection(zhanghao.length());
    }

    private void printResult2(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        mima.setText(resultBuffer.toString());
        mima.setSelection(mima.length());
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    private void showTip(final String str) {

        mToast.setText(str);
        mToast.show();
    }

    private void initViews() {//初始视图

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        image_btn = (ImageButton) findViewById(R.id.u_img);
        zhanghao = (EditText) findViewById(R.id.u_zhanghao);
        mima = (EditText) findViewById(R.id.u_mima);
        xf_password = (ImageView) findViewById(R.id.xf_password);
        xf_account = (ImageView) findViewById(R.id.xf_account);
        xf_account.setOnClickListener(this);
        xf_password.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        image_btn.setOnClickListener(this);
        uService = new UserService(LoginActivity.this);
        usernamelList = uService.getAll();

    }

    protected void onResume() {
        super.onResume();
        usernamelList.clear();//清除
        usernamelList = uService.getAll();//更新
    }


    private void showListPopulWindow() {//下拉列表

        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAnchorView(zhanghao);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, usernamelList));
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                zhanghao.setText(usernamelList.get(i));
                listPopupWindow.dismiss();//关闭
            }

        });

        listPopupWindow.show();//展示
    }


    @Override
    public void onClick(View v) {//设置监听

        switch (v.getId()) {
            case R.id.u_img:
                showListPopulWindow();
                break;
            case R.id.bt_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_login:
                String name = zhanghao.getText().toString();
                String pass = mima.getText().toString();
                boolean flag = uService.login(name, pass);
                if (flag) {
                    Log.i("TAG", "登录成功");
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                } else {
                    Log.i("TAG", "账号或密码错误");
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.xf_account://录入账号
                speechDialog();
                break;
            case R.id.xf_password://录入密码

                speechDialog2();
                break;
        }
    }


}
