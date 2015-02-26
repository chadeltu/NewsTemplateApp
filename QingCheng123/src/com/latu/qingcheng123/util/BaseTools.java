package com.latu.qingcheng123.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;

public class BaseTools {

	/**
	 * 获取屏幕的宽度
	 * 
	 * @param activity
	 * @return
	 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断WIFI网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取应用缓存路径
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getAppCachePath(Context ctx) {
		String strRootPath = ctx.getCacheDir().getAbsolutePath()
				+ File.separator + GlobalParam.g_appName;
		File fileDir = new File(strRootPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		return strRootPath;
	}

	/**
	 * 判断SD卡
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取图片存放路径
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getImageCachePath(Context ctx) {
		String strRootPath = null;
		if (isSDCardAvailable()) {
			strRootPath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + GlobalParam.g_appName;
		} else {
			strRootPath = ctx.getCacheDir().getAbsolutePath() + File.separator
					+ GlobalParam.g_appName;
		}
		strRootPath += File.separator + "img";
		File fileDir = new File(strRootPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		return strRootPath;
	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLocalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			Bitmap bm = BitmapFactory.decodeStream(fis);
			fis.close();
			return bm;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 图片保存本地，用于缓存
	 * 
	 * @param bm
	 * @param filePath
	 */
	public static void saveBitmapInLocal(Bitmap bm, String filePath)
	{
		if (bm != null && filePath != null)
		{
			File file = new File(filePath);
			if (file.exists())
			{
				file.delete();
			}
			try {
				FileOutputStream out = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
			}
		}
	}
}
