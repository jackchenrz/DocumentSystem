package com.publicstech.documentsystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class MApplication extends Application {

	/**对外提供整个应用生命周期的Context**/
	private static Context instance;
	/**整个应用全局可访问数据集合**/
	private static Map<String, Object> gloableData = new HashMap<String, Object>();
	private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
	private static final String LOG_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/documentsystem/log/";
	private static final String LOG_NAME = getCurrentDateString() + ".txt";
	/**
	 * 对外提供Application Context
	 * @return
	 */
	public static Context gainContext() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		instance = this;
		Thread.setDefaultUncaughtExceptionHandler(handler);
	}
	UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
		 
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            writeErrorLog(ex);
        }
	};

	/**
	 * 往Application放置数据（最大不允许超过5个）
	 * @param strKey 存放属性Key
	 * @param strValue 数据对象
	 */
	public static void assignData(String strKey, Object strValue) {
//		if (gloableData.size() > 8) {
//			throw new RuntimeException("超过允许最大数");
//		}
		gloableData.put(strKey, strValue);
	}

	/**
	 * 从Applcaiton中取数据
	 * @param strKey 存放数据Key
	 * @return 对应Key的数据对象
	 */
	public static Object gainData(String strKey) {
		return gloableData.get(strKey);
	}
	
	/*
	 * 从Application中移除数据
	 */
	public static void removeData(String key){
		if(gloableData.containsKey(key)) gloableData.remove(key);
	}

	/**
	 * 将Activity压入Application栈
	 * @param task 将要压入栈的Activity对象
	 */
	public  void pushTask(WeakReference<Activity> task) {
		activitys.push(task);
	}

	/**
	 * 将传入的Activity对象从栈中移除
	 * @param task
	 */
	public  void removeTask(WeakReference<Activity> task) {
		activitys.remove(task);
	}

	/**
	 * 根据指定位置从栈中移除Activity
	 * @param taskIndex Activity栈索引
	 */
	public  void removeTask(int taskIndex) {
		if (activitys.size() > taskIndex)
			activitys.remove(taskIndex);
	}

	/**
	 * 将栈中Activity移除至栈顶
	 */
	public  void removeToTop() {
		int end = activitys.size();
		int start = 1;
		for (int i = end - 1; i >= start; i--) {
			if (!activitys.get(i).get().isFinishing()) {     
				activitys.get(i).get().finish(); 
		    }
		}
	}

	/**
	 * 移除全部（用于整个应用退出）
	 */
	public  void removeAll() {
		//finish所有的Activity
		for (WeakReference<Activity> task : activitys) {
			if (!task.get().isFinishing()) {     
				task.get().finish(); 
		    }  
		}
	}
	
	
	/**
     * 打印错误日志
     * 
     * @param ex
     */
    protected void writeErrorLog(Throwable ex) {
        String info = null;
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;
        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);

			// 先写入手机的信息
			Class<?> clazz = Class.forName("android.os.Build");
			Field[] fields = clazz.getFields();// 获得所有的字员变量
			for (Field field : fields) {
				String name = field.getName(); // 获得成员变量的名称
				Object value = field.get(null); // 获得成员变量的值
				printStream.println(name+" : "+value); // 将成员变量的信息，写出日志文件
			}
			printStream.println("=============我是一条分隔线=====================");
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            info = new String(data);
            data = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("example", "崩溃信息\n" + info);
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, LOG_NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    
    /**
     * 获取当前日期
     * 
     * @return    
     */
    private static String getCurrentDateString() {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Date nowDate = new Date();
        result = sdf.format(nowDate);
        return result;
    }
	/*******************************************Application中存放的Activity操作（压栈/出栈）API（结束）*****************************************/
}
