package com.funny.utils.helper;

import com.funny.utils.PropertiesUtil;
import java.lang.management.OperatingSystemMXBean;


public class SysHelper {
    public static final String JVM_VERSION = PropertiesUtil.key("java.version");
    public static final String JVM_ENCODING = PropertiesUtil.key("file.encoding");
    public static final String JVM_TEMPDIR = PropertiesUtil.key("java.io.tmpdir");

    public static String HOST_IP;
    public static String HOST_NAME;

    public static String OS_ARCH = PropertiesUtil.key("os.arch");
    public static String OS_NAME = PropertiesUtil.key("os.name");
    public static String OS_VERSION = PropertiesUtil.key("os.version");
    public static String SUN_DESKTOP = PropertiesUtil.key("sun.desktop");

    public static final String FILE_SEPARATOR = PropertiesUtil.key("file.separator");
    public static String PATH_SEPARATOR = PropertiesUtil.key("path.separator");
    public static String LINE_SEPARATOR = PropertiesUtil.key("line.separator");


    public static long TotalMemorySize;
    private static OperatingSystemMXBean osmxb;
    private static int kb = 1024;




    public static long JVMtotalMem() {
        return Runtime.getRuntime().totalMemory() / kb;
    }


    public static long JVMfreeMem() {
        return Runtime.getRuntime().freeMemory() / kb;
    }


    public static long JVMmaxMem() {
        return Runtime.getRuntime().maxMemory() / kb;
    }


    public static void setHttpProxy(String host, String port, String username, String password) {
        System.getProperties().put("http.proxyHost",host);
        System.getProperties().put("http.proxyPort",port);
        System.getProperties().put("http.proxyUser",username);
        System.getProperties().put("http.proxyPassword",password);
    }


    public static void setHttpProxy(String host, String port) {
        System.getProperties().put("http.proxyHost",host);
        System.getProperties().put("http.proxyPort",port);
    }


    public static boolean legalFile(String path) {
        String regex = "[a-zA-Z]:(?:[/][^/:*?\"<>|.][^/:*?\"<>|]{0,254})+";
        return RegHelper.isMatche(commandPath(path), regex);
    }


    public static String commandPath(String file) {
        return file.replaceAll("\\\\{1,}", "/").replaceAll("\\/{2,}", "/");
    }
}
