package com.example.sf.bdoudizhu.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.app.Activity;

public class MainActivity extends BaseActivity
{
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static double SCALE_VERTICAL;
	public static double SCALE_HORIAONTAL;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
		DisplayMetrics sizeMatrix = new DisplayMetrics ();//获取屏幕大小
		this.getWindowManager ().getDefaultDisplay ().getMetrics (sizeMatrix);
		SCREEN_WIDTH = sizeMatrix.widthPixels;//绝对宽度
		SCREEN_HEIGHT = sizeMatrix.heightPixels;//绝对高度
		if (SCREEN_WIDTH < SCREEN_HEIGHT)
		{
			SCREEN_HEIGHT += SCREEN_WIDTH;
			SCREEN_WIDTH = SCREEN_HEIGHT - SCREEN_WIDTH;
			SCREEN_HEIGHT = SCREEN_HEIGHT - SCREEN_WIDTH;
		}
		//长宽缩放比
		SCALE_VERTICAL = SCREEN_HEIGHT / 360.0;
		SCALE_HORIAONTAL = SCREEN_WIDTH / 520.0;

		final String widthAndHeight = MainActivity.SCREEN_WIDTH + " " + MainActivity.SCREEN_HEIGHT;

		Intent intent1 = new Intent (MainActivity.this, GameActivity.class);

		intent1.putExtra ("widthAndHeight", widthAndHeight);//将数据传递给GameActivity

		MainView mainView = new MainView (this, intent1);

		setContentView (mainView);

	}
	protected void onResume() {
		super.onResume();
	}
}
