package com.example.sf.bdoudizhu.Activity;

import android.os.Bundle;

import android.util.Log;
import android.app.Activity;

public class BaseActivity extends Activity {

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
