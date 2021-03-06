package com.funny.utils;

import org.apache.commons.lang3.StringUtils;
import java.io.PrintWriter;


public class SystemUtil {
    private static final JvmSpecInfo JVM_SPEC_INFO = new JvmSpecInfo();
    private static final JvmInfo JVM_INFO = new JvmInfo();
    private static final JavaSpecInfo JAVA_SPEC_INFO = new JavaSpecInfo();
    private static final JavaInfo JAVA_INFO = new JavaInfo();
    private static final OsInfo OS_INFO = new OsInfo();
    private static final UserInfo USER_INFO = new UserInfo();
    private static final HostInfo HOST_INFO = new HostInfo();
    private static final JavaRuntimeInfo JAVA_RUNTIME_INFO = new JavaRuntimeInfo();




    public static JvmSpecInfo getJvmSpecInfo() {
        return JVM_SPEC_INFO;
    }


    public static JvmInfo getJvmInfo() {
        return JVM_INFO;
    }


    public static JavaSpecInfo getJavaSpecInfo() {
        return JAVA_SPEC_INFO;
    }


    public static JavaInfo getJavaInfo() {
        return JAVA_INFO;
    }


    public static JavaRuntimeInfo getJavaRuntimeInfo() {
        return JAVA_RUNTIME_INFO;
    }


    public static OsInfo getOsInfo() {
        return OS_INFO;
    }


    public static UserInfo getUserInfo() {
        return USER_INFO;
    }


    public static HostInfo getHostInfo() {
        return HOST_INFO;
    }


    public static final class JvmSpecInfo {
        private final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name",false);
        private final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version",false);
        private final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor",false);


        public String getName() {
            return JAVA_VM_SPECIFICATION_NAME;
        }

        public String getVersion() {
            return JAVA_VM_SPECIFICATION_VERSION;
        }

        public String getVendor() {
            return JAVA_VM_SPECIFICATION_VENDOR;
        }
    }




    public static final class JvmInfo {
        private final String JAVA_VM_NAME = getSystemProperty("java.vm.name",false);
        private final String JAVA_VM_VERSION = getSystemProperty("java.vm.version",false);
        private final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor",false);
        private final String JAVA_VM_INFO = getSystemProperty("java.vm.info",false);


        public String getName() {
            return JAVA_VM_NAME;
        }

        public String getVersion() {
            return JAVA_VM_VERSION;
        }

        public String getVendor() {
            return JAVA_VM_VENDOR;
        }

        public String getInfo() {
            return JAVA_VM_INFO;
        }
    }




    public static final class JavaSpecInfo {
        private final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name",false);
        private final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version",false);
        private final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor",false);


        public String getName() {
            return JAVA_SPECIFICATION_NAME;
        }

        public String getVersion() {
            return JAVA_SPECIFICATION_VERSION;
        }

        public String getVendor() {
            return JAVA_SPECIFICATION_VENDOR;
        }
    }




    public static final class JavaInfo {
        private final String JAVA_VERSION = getSystemProperty("java.version",false);
        private final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
        private final int JAVA_VERSION_INT = getJavaVersionAsInt();
        private final String JAVA_VENDOR = getSystemProperty("java.vendor",false);
        private final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url",false);

        private final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");
        private final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");
        private final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");
        private final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");
        private final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");
        private final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");
        private final boolean IS_JAVA_1_7 = getJavaVersionMatches("1.7");
        private final boolean IS_JAVA_1_8 = getJavaVersionMatches("1.8");
        private final boolean IS_JAVA_1_9 = getJavaVersionMatches("1.9");
        private final boolean IS_JAVA_1_10 = getJavaVersionMatches("1.10");
        private final boolean IS_JAVA_1_11 = getJavaVersionMatches("1.11");


        public String getVersion() {
            return JAVA_VERSION;
        }


        public float getVersionFloat() {
            return JAVA_VERSION_FLOAT;
        }

        public int getVersionInt() {
            return JAVA_VERSION_INT;
        }

        private float getJavaVersionAsFloat() {
            if (JAVA_VERSION == null) {
                return 0f;
            }

            String str = JAVA_VERSION.substring(0,3);

            if (JAVA_VERSION.length() >= 5) {
                str = str + JAVA_VERSION.substring(4,5);
            }

            return ConverterUtil.toFloat(str);
        }

        private int getJavaVersionAsInt() {
            if (JAVA_VERSION == null) {
                return 0;
            }

            String str = JAVA_VERSION.substring(0,1);
            str = str + JAVA_VERSION.substring(2,3);

            if (JAVA_VERSION.length() >= 5) {
                str = str + JAVA_VERSION.substring(4,5);
            } else {
                str = str + "0";
            }

            return Integer.parseInt(str);
        }

        public String getVendor() {
            return JAVA_VENDOR;
        }

        public String getVendorURL() {
            return JAVA_VENDOR_URL;
        }

        public boolean isJava11() {
            return IS_JAVA_1_1;
        }

        public boolean isJava12() {
            return IS_JAVA_1_2;
        }

        public boolean isJava13() {
            return IS_JAVA_1_3;
        }

        public boolean isJava14() {
            return IS_JAVA_1_4;
        }

        public boolean isJava15() {
            return IS_JAVA_1_5;
        }

        public boolean isJava16() {
            return IS_JAVA_1_6;
        }

        public boolean isJava17() {
            return IS_JAVA_1_7;
        }

        public boolean isJava18() {
            return IS_JAVA_1_8;
        }

        public boolean isJava19() {
            return IS_JAVA_1_9;
        }

        public boolean isJava110() {
            return IS_JAVA_1_10;
        }

        public boolean isJava111() {
            return IS_JAVA_1_11;
        }

        private boolean getJavaVersionMatches(String versionPrefix) {
            if (JAVA_VERSION == null) {
                return false;
            }

            return JAVA_VERSION.startsWith(versionPrefix);
        }

        public boolean isJavaVersionAtLeast(float requiredVersion) {
            return getVersionFloat() >= requiredVersion;
        }

        public boolean isJavaVersionAtLeast(int requiredVersion) {
            return getVersionInt() >= requiredVersion;
        }
    }




    public static final class JavaRuntimeInfo {
        private final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name",false);
        private final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version",false);
        private final String JAVA_HOME = getSystemProperty("java.home",false);
        private final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs",false);
        private final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs",false);
        private final String JAVA_CLASS_PATH = getSystemProperty("java.class.path",false);
        private final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version",false);
        private final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path",false);
        private final String SUN_BOOT_CLASS_PATH = getSystemProperty("sun.boot.class.path",false);
        private final String SUN_ARCH_DATA_MODEL = getSystemProperty("sun.arch.data.model",false);


        public String getSunBoothClassPath() {
            return SUN_BOOT_CLASS_PATH;
        }

        public String getSunArchDataModel() {
            return SUN_ARCH_DATA_MODEL;
        }

        public String getName() {
            return JAVA_RUNTIME_NAME;
        }

        public String getVersion() {
            return JAVA_RUNTIME_VERSION;
        }

        public String getHomeDir() {
            return JAVA_HOME;
        }

        public String getExtDirs() {
            return JAVA_EXT_DIRS;
        }

        public String getEndorsedDirs() {
            return JAVA_ENDORSED_DIRS;
        }

        public String getClassPath() {
            return JAVA_CLASS_PATH;
        }

        public String[] getClassPathArray() {
            return StringUtils.split(getClassPath(),getOsInfo().getPathSeparator());
        }

        public String getClassVersion() {
            return JAVA_CLASS_VERSION;
        }

        public String getLibraryPath() {
            return JAVA_LIBRARY_PATH;
        }

        public String[] getLibraryPathArray() {
            return StringUtils.split(getLibraryPath(),getOsInfo().getPathSeparator());
        }

        public String getProtocolPackages() {
            return getSystemProperty("java.protocol.handler.pkgs",true);
        }
    }




    public static final class OsInfo {
        private final String OS_VERSION = getSystemProperty("os.version",false);
        private final String OS_ARCH = getSystemProperty("os.arch",false);
        private final String OS_NAME = getSystemProperty("os.name",false);
        private final boolean IS_OS_AIX = getOSMatches("AIX");
        private final boolean IS_OS_HP_UX = getOSMatches("HP-UX");
        private final boolean IS_OS_IRIX = getOSMatches("Irix");
        private final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");
        private final boolean IS_OS_MAC = getOSMatches("Mac");
        private final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");
        private final boolean IS_OS_OS2 = getOSMatches("OS/2");
        private final boolean IS_OS_SOLARIS = getOSMatches("Solaris");
        private final boolean IS_OS_SUN_OS = getOSMatches("SunOS");
        private final boolean IS_OS_WINDOWS = getOSMatches("Windows");
        private final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows","5.0");
        private final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9","4.0");
        private final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9","4.1");
        private final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows","4.9");
        private final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");
        private final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows","5.1");

        private final String FILE_ENCODING = LocaleUtil.getSystem().getCharset().name();
        private final String FILE_SEPARATOR = getSystemProperty("file.separator",false);
        private final String LINE_SEPARATOR = getSystemProperty("line.separator",false);
        private final String PATH_SEPARATOR = getSystemProperty("path.separator",false);


        public String getArch() {
            return OS_ARCH;
        }

        public String getName() {
            return OS_NAME;
        }

        public String getVersion() {
            return OS_VERSION;
        }

        public boolean isAix() {
            return IS_OS_AIX;
        }

        public boolean isHpUx() {
            return IS_OS_HP_UX;
        }

        public boolean isIrix() {
            return IS_OS_IRIX;
        }

        public boolean isLinux() {
            return IS_OS_LINUX;
        }

        public boolean isMac() {
            return IS_OS_MAC;
        }

        public boolean isMacOsX() {
            return IS_OS_MAC_OSX;
        }

        public boolean isOs2() {
            return IS_OS_OS2;
        }

        public boolean isSolaris() {
            return IS_OS_SOLARIS;
        }

        public boolean isSunOS() {
            return IS_OS_SUN_OS;
        }

        public boolean isWindows() {
            return IS_OS_WINDOWS;
        }

        public boolean isWindows2000() {
            return IS_OS_WINDOWS_2000;
        }

        public boolean isWindows95() {
            return IS_OS_WINDOWS_95;
        }

        public boolean isWindows98() {
            return IS_OS_WINDOWS_98;
        }

        public boolean isWindowsME() {
            return IS_OS_WINDOWS_ME;
        }

        public boolean isWindowsNT() {
            return IS_OS_WINDOWS_NT;
        }

        public boolean isWindowsXP() {
            return IS_OS_WINDOWS_XP;
        }

        private boolean getOSMatches(String osNamePrefix) {
            if (OS_NAME == null) {
                return false;
            }

            return OS_NAME.startsWith(osNamePrefix);
        }

        private boolean getOSMatches(String osNamePrefix,String osVersionPrefix) {
            if ((OS_NAME == null) || (OS_VERSION == null)) {
                return false;
            }

            return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
        }

        public String getFileEncoding() {
            return FILE_ENCODING;
        }

        public String getFileSeparator() {
            return FILE_SEPARATOR;
        }

        public String getLineSeparator() {
            return LINE_SEPARATOR;
        }

        public String getPathSeparator() {
            return PATH_SEPARATOR;
        }
    }




    public static final class UserInfo {
        private final String USER_NAME = getSystemProperty("user.name",false);
        private final String USER_HOME = getSystemProperty("user.home",false);
        private final String USER_DIR = getSystemProperty("user.dir",false);
        private final String USER_LANGUAGE = getSystemProperty("user.language",false);
        private final String USER_COUNTRY = ((getSystemProperty("user.country",false) == null) ? getSystemProperty("user.region",false) : getSystemProperty("user.country",false));
        private final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir",false);


        public String getName() {
            return USER_NAME;
        }

        public String getHomeDir() {
            return USER_HOME;
        }

        public String getCurrentDir() {
            return USER_DIR;
        }

        public String getTempDir() {
            return JAVA_IO_TMPDIR;
        }

        public String getLanguage() {
            return USER_LANGUAGE;
        }

        public String getCountry() {
            return USER_COUNTRY;
        }
    }




    public static final class HostInfo {
        private final String HOST_NAME;
        private final String HOST_ADDRESS;

        private HostInfo() {
            HOST_NAME = NetworkUtil.getLocalHostname();
            HOST_ADDRESS = NetworkUtil.getLocalHostIp();
        }

        public String getName() {
            return HOST_NAME;
        }

        public String getAddress() {
            return HOST_ADDRESS;
        }
    }


    public static void dumpSystemInfo() {
        dumpSystemInfo(new PrintWriter(System.out));
    }


    public static void dumpSystemInfo(PrintWriter out) {
        out.println("--------------");
        out.println(getJvmSpecInfo());
        out.println("--------------");
        out.println(getJvmInfo());
        out.println("--------------");
        out.println(getJavaSpecInfo());
        out.println("--------------");
        out.println(getJavaInfo());
        out.println("--------------");
        out.println(getJavaRuntimeInfo());
        out.println("--------------");
        out.println(getOsInfo());
        out.println("--------------");
        out.println(getUserInfo());
        out.println("--------------");
        out.println(getHostInfo());
        out.println("--------------");
        out.flush();
    }

    private static String getSystemProperty(String name,boolean quiet) {
        try {
            return System.getProperty(name);
        } catch (SecurityException e) {
            if (!quiet) {
                System.err.println("Caught a SecurityException reading the system property '" + name
                        + "'; the SystemUtil property value will default to null.");
            }

            return null;
        }
    }


    public static String getExternalVariable(String var) {
        String value = System.getProperty(var);
        if (StringUtils.isEmpty(value)) {
            value = System.getenv(var);
        }

        return StringUtils.isEmpty(value) ? null : value;
    }


    private SystemUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
