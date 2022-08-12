package com.risen.helper.util;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/23 9:16
 */
public class SystemTypeUtil {


    public static boolean isWindowsSystem() {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("windows")) {
            return true;
        }
        return false;
    }


    public static boolean isLinuxSystem() {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("linux")) {
            return true;
        }
        return false;
    }


}


