package com.gosuncn.core;

import android.app.Application;

import com.gosuncn.core.utils.AppUtil;
import com.gosuncn.core.utils.UniversalimageloaderUtil;
import com.gosuncn.core.utils.helpers.TrafficHelper;


public class AndroidApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		//初始化ImageLoader
        UniversalimageloaderUtil.initImageLoader(getApplicationContext());
        
        TrafficHelper.uid=AppUtil.getUid(getApplicationContext(), AppConfig.CFG_PACKAGE_NAME);
	}

}

