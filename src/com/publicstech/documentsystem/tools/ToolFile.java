package com.publicstech.documentsystem.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class ToolFile {
	private static final String TAG = ToolFile.class.getSimpleName();

	/**
	 * 检查是否已挂载SD卡镜像（是否存在SD卡）
	 * 
	 * @return
	 */
	public static boolean isMountedSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		} else {
			Log.w(TAG, "SDCARD is not MOUNTED !");
			return false;
		}
	}
	
	/**
	 * 获取SD卡剩余容量（单位Byte）
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long gainSDFreeSize() {
		if (isMountedSDCard()) {
			// 取得SD卡文件路径
			File path = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(path.getPath());
			// 获取单个数据块的大小(Byte)
			long blockSize = sf.getBlockSize();
			// 空闲的数据块的数量
			long freeBlocks = sf.getAvailableBlocks();

			// 返回SD卡空闲大小
			return freeBlocks * blockSize; // 单位Byte
		} else {
			return 0;
		}
	}
	
	public static File write(InputStream inputStream, String filePath) throws IOException {
		OutputStream outputStream = null;
		// 在指定目录创建一个空文件并获取文件对象
		File mFile = new File(filePath);
		if (!mFile.getParentFile().exists())
			mFile.getParentFile().mkdirs();
		try {
			outputStream = new FileOutputStream(mFile);
			byte buffer[] = new byte[4 * 1024];
			int lenght = 0 ;
			while ((lenght = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer,0,lenght);
			}
			outputStream.flush();
			return mFile;
		} catch (IOException e) {
			Log.e(TAG, "写入文件失败，原因："+e.getMessage());
			throw e;
		}finally{
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}
}
