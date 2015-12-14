package com.publicstech.documentsystem.tools;

import android.os.Handler;
import android.os.Looper;

public class ToolThread {
	// ���handler���������Զ��������߳�
	public static Handler handler = new Handler(Looper.getMainLooper());

	/**
	 * ���߳�ִ��
	 */
	public static void runInBackground(Runnable runnable) {
		new Thread(runnable).start();
	}
	
	/**
	 * ���߳�ִ��
	 */
	public static void runInMainThread(Runnable runnable) {
		handler.post(runnable);
	}
}
