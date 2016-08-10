package com.gosuncn.core.utils;

import java.io.File;

/**
 * 其他工具类
 * @author HWJ
 *
 */
public class CommonUtil {
    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public  static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
	
}
