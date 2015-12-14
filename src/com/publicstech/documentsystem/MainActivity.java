package com.publicstech.documentsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;

import com.publicstech.documentsystem.activity.LoginActivity;
import com.publicstech.documentsystem.tools.ToolThread;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ToolThread.runInBackground(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(2500);
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
