package com.latu.qingcheng123.util;

import java.text.SimpleDateFormat;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GlobalParam {

	public final static String g_appName = "Qingcheng123";
	public static RequestQueue g_volleyRequestQueue = null;
	public static final SimpleDateFormat g_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void initParam(Context ctx)
	{
		g_volleyRequestQueue = Volley.newRequestQueue(ctx);
	}
	
}
