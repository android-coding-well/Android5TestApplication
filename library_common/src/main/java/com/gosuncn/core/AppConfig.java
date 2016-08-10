package com.gosuncn.core;

import android.os.Environment;

/**
 * 在此类中配置一些常量值，可以参考以下的设置
 */
public class AppConfig {
		// 版本号
		public final static String CFG_VERSION = "2.2.0.300521";
		
		// 系统名称
		public final static String CFG_APP_NAME = "高新兴";
		
		// 客户热线
		public final static String CFG_CUSTOMER_SERVICE_PHONE = "4001-300098";
		
		// 版权所有
		public final static String CFG_CORP = "高新兴";

		// 根目录文件名
		private final static String CFG_ROOT_DIRECTORY_NAME = "CORE";
		
		// 包名
		public final static String CFG_PACKAGE_NAME = "com.gosuncn.core";

		//SD卡根目录
		public static final String CFG_SDCARD_ROOT_PATH = Environment
				.getExternalStorageDirectory().toString();
	
}
