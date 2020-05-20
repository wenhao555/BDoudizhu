package com.example.sf.bdoudizhu.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;

public class GameActivity extends BaseActivity
{
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	private Game game;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏

		Bundle bundle = this.getIntent ().getExtras ();
		String Ano = null;
		if (bundle != null)
			Ano = bundle.getString ("widthAndHeight");
		String[] temp = new String[0];
		if (Ano != null)
			temp = Ano.split (" ");
		SCREEN_WIDTH = Integer.parseInt (temp[0]);
		SCREEN_HEIGHT = Integer.parseInt (temp[1]);
		game = Game.getGame ();
		GameView gameView = new GameView (this, game);
		game.game_begin = true;
		new Thread (sendable1).start ();
		setContentView (gameView);

	}

	Runnable sendable1 = new Runnable ()
	{
		@Override
		public void run ()
		{
			try
			{
				addition_isCorrect (game);
			}
			catch (Exception e)
			{
				Log.d ("exception", "GameActivity: " + e);
			}

		}
	};

	public void addition_isCorrect (Game game) throws Exception
	{
		while (game.game_begin)
			switch (game.status)
			{
				case NotStart:
					game.startGame ();
					break;
				case GetLandlord:
					game.callLandlord ();
					break;
				case Discard:
					game.discard ();
					break;
				case Wait:
					game.wait_final ();

					Intent intent1 = new Intent (GameActivity.this, MainActivity.class);
					startActivity (intent1);


			}

	}

	public void refresh() { onCreate(null); }//刷新
	@Override
	public void onBackPressed ()
	{
		finish ();
	}

	protected void onResume() {
		super.onResume();
	}

}
