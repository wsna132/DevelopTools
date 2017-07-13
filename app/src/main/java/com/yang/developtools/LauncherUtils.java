package com.yang.developtools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


/**
 * 获取手机中与launcher有关的信息，工具类
 * 
 * @author ww
 * 
 */
public class LauncherUtils {

//	private HomeKeyEventBroadCastReceiver mHomeListenter = null;
	
	//========================清除默认launcher的代码开始======================//
	
	/**
	 * 清除lanucher
	 * 并返回清除的默认桌面的包名
	 * @param context
	 */
	public static boolean clearLanucher(Context context) {
		
		PackageManager p = context.getPackageManager();
		 ComponentName cN = new
		 ComponentName(context,MainActivity.class);
		 p.setComponentEnabledSetting(cN,
		 PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
		 PackageManager.DONT_KILL_APP);
		ResolveInfo info = getCurLauncher(context);
		
		if (!("android".equals(info.activityInfo.applicationInfo.packageName)
				|| "com.miui.home".equals(info.activityInfo.applicationInfo.packageName)
//				||"com.mobi.screensaverson".equals(info.activityInfo.applicationInfo.packageName)
				)) {
			showInstalledAppDetails(context, info.activityInfo.applicationInfo.packageName);
			return false;
		} 
		return true;
	}
	
	/**
	 * 获取当前使用的launcher
	 * 
	 * @param context
	 * @return
	 */
	public static ResolveInfo getCurLauncher(Context context) {
		
		
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		ResolveInfo info = pm.resolveActivity(intent, 0);
		return info;
	}
	
	/**
	 * 显示应用程序的详细信息界面
	 * @param context
	 * @param packageName 应用的包名
	 */
	private static void showInstalledAppDetails(Context context, String packageName) {  
		
	    Intent intent = new Intent();  
	    final int apiLevel = Build.VERSION.SDK_INT;  
	    
	    if (apiLevel >= 9) { 
	    	// 2.3（ApiLevel 9）以上，使用SDK提供的接口  
	        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");  
	        Uri uri = Uri.fromParts("package", packageName, null);  
	        intent.setData(uri);  
	    } else { 
	    	// 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）  
	        // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。  
	        intent.setAction(Intent.ACTION_VIEW);  
	        final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");  
	        intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");  
	        intent.putExtra(appPkgName, packageName);  
	    }  
	    context.startActivity(intent);  
	}
	

	//========================清除默认launcher的代码结束======================//
	
	
	
	//============================指引用户将我们设为launcher的逻辑实现代码开始======================//
	/**
	 * 启动器
	 * 
	 * @param context
	 */
	public static void startLauncher(Context context) {
//		if ((GetBuildProproperties("ro.miui.ui.version.name=") != null)
//				&& (GetBuildProproperties("ro.miui.ui.version.name=")
//						.contains("V5"))) {
//			startV5Home(context);
//		} else {
			startCurHome(context);
//		}
//		PackageManager p = context.getPackageManager();
//		 ComponentName cN = new
//		 ComponentName(context,FakeLaunchActivity.class);
//		 p.setComponentEnabledSetting(cN,
//		 PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//		 PackageManager.DONT_KILL_APP);
	}

	/**
	 * 启动当前Home,小米v5在没有默认值的时，也直接默认启动自带桌面
	 * 
	 * @param context
	 */
	private static void startCurHome(Context context) {
		try{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		//防止这一句报错
		intent.setComponent(new ComponentName("android","com.android.internal.app.ResolverActivity"));
		intent.putExtra("screen.saver.tag", 3);//发送给子apk的信息，表示现在是在设置我们的软件为默认home，不用回到桌面
		context.startActivity(intent);
		}catch(Exception e){
			if ((GetBuildProproperties("ro.miui.ui.version.name=") != null)
					&& (GetBuildProproperties("ro.miui.ui.version.name=")
							.contains("V5"))) {
			startV5Home(context);
			}
		}
	}

	/**
	 * v5时去让用户主动去更换桌面
	 * 
	 * @param context
	 */
	private static void startV5Home(Context context) {
		Intent localIntent1 = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
		context.startActivity(localIntent1);
	}

	
	/***
	 * 获得固件的属性
	 */
	private static String GetBuildProproperties(String paramString) {
		try {
			BufferedInputStream localBufferedInputStream = new BufferedInputStream(
					new FileInputStream(new File("/system/build.prop")));
			BufferedReader localBufferedReader = new BufferedReader(
					new InputStreamReader(localBufferedInputStream));
			String str1;
			do {
				str1 = localBufferedReader.readLine();
				if (str1 == null) {
					localBufferedReader.close();
					localBufferedInputStream.close();
					return null;
				}
			} while (str1.indexOf(paramString) == -1);
			localBufferedReader.close();
			localBufferedInputStream.close();
			String str2 = str1.substring(1 + str1.indexOf("="));
			return str2;
		} catch (Exception localException) {
			if (localException.getMessage() != null) {
				System.out.println(localException.getMessage());
				return null;
			}
			localException.printStackTrace();
		}
		return null;
	}

	//============================指引用户将我们设为launcher的逻辑实现代码开始======================//
	
	
	//========================让用户设置加launcher开始==============================================//
	/**
	 * 获取手机中所有的launcher，如果自己是launcher，没有过滤自己
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getAllLaunchers(Context context) {
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> list = new ArrayList<ResolveInfo>();
		list = pm.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list;
	}
	//========================让用户设置加launcher结束==============================================//
	
	
	public static void removeHomeListener(Context context){
		for(ResolveInfo info:getAllLaunchers(context)){
			if(info.activityInfo.applicationInfo.packageName.equals(context.getPackageName())){
				PackageManager p = context.getPackageManager();
				 ComponentName cN = new
				 ComponentName(context,MainActivity.class);
				 p.setComponentEnabledSetting(cN,
				 PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				 PackageManager.DONT_KILL_APP);
				 return;
			}
		}
	}
	

//	/**
//	 * 获取当前屏幕的宽度
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static int getScreenWidth(Context context) {
//		Display display = ((WindowManager) context
//				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		int width = display.getWidth();
//		return width;
//	}
//
//	/**
//	 * 获取当前屏幕的高度
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static int getScreenHeight(Context context) {
//		Display display = ((WindowManager) context
//				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		int height = display.getHeight();
//		return height;
//	}

}
