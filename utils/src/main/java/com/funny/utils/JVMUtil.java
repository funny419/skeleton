package com.funny.utils;

import com.funny.utils.helper.InOutStreamHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


public class JVMUtil {
    private static final String[] MANIFESTS = {"Manifest.mf","manifest.mf","MANIFEST.MF"};
    private static final JavaRuntimeInfo JAVA_RUNTIME_INFO = new JavaRuntimeInfo();




    public static boolean appendToClassPath(String name) {
        if (!FileUtil.exist(name)) {
            return false;
        }

        try {
            ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
            Method appendToClassPathMethod = clsLoader
                            .getClass()
                            .getDeclaredMethod("appendToClassPathForInstrumentation",String.class);

            appendToClassPathMethod.setAccessible(true);
            appendToClassPathMethod.invoke(clsLoader, name);

            return true;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }


    private static Manifest getManifestFromFile(File classpathItem) {
        File metaDir = new File(classpathItem,"META-INF");
        File manifestFile = null;
        if (metaDir.isDirectory()) {
            for (String m : MANIFESTS) {
                File mFile = new File(metaDir, m);
                if (mFile.isFile()) {
                    manifestFile = mFile;
                    break;
                }
            }
        }

        if (manifestFile == null) {
            return null;
        }

        return getAndClose(manifestFile);
    }


    private static Manifest getAndClose(File manifestFile) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(manifestFile);
            return new Manifest(fis);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        finally {
            InOutStreamHelper.close(fis);
        }
    }


    private static Manifest getManifestFromJar(File classpathItem) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(classpathItem);
            return new JarFile(classpathItem).getManifest();
        }
        catch (IOException e) {
            throw new RuntimeException("JVMUtil.getManifestFromJar Error", e);
        }
        finally {
            InOutStreamHelper.close(fis);
        }
    }


    public static Manifest getManifest(File classpathItem) {
        if (classpathItem == null) {
            return null;
        }

        if (classpathItem.isFile()) {
            return getManifestFromJar(classpathItem);
        }

        return getManifestFromFile(classpathItem);
    }


    private static String getClasspathItemBaseDir(File classpathItem) {
        if (classpathItem.isFile()) {
            return classpathItem.getParent();
        }

        return classpathItem.toString();
    }


    public static File[] getClasspath(ClassLoader classLoader) {
        Set<File> classpaths = CollectionUtil.createHashSet();

        while (classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) classLoader).getURLs();
                for (URL u : urls) {
                    File f = FileUtil.toFile(u);
                    if (f != null) {
                        try {
                            f = f.getCanonicalFile();
                            classpaths.add(f);
                            addInnerClasspathItems(classpaths, f);
                        } catch (IOException e) {
                            throw new RuntimeException();
                        }
                    }
                }
            }
            classLoader = classLoader.getParent();
        }

        String bootstrap = JAVA_RUNTIME_INFO.SUN_BOOT_CLASS_PATH;
        if (bootstrap != null) {
            classpaths.add(new File(bootstrap));
        }

        return classpaths.toArray(new File[classpaths.size()]);
    }

    private static void addInnerClasspathItems(Set<File> classpaths, File item) throws IOException {

        Manifest manifest = getManifest(item);
        if (manifest == null) {
            return;
        }

        Attributes attributes = manifest.getMainAttributes();
        if (attributes == null) {
            return;
        }

        String classPaths = attributes.getValue(Attributes.Name.CLASS_PATH);
        if (classPaths == null) {
            return;
        }

        String base = getClasspathItemBaseDir(item);

        String[] tokens = StringUtils.split(classPaths, ' ');
        for (String t : tokens) {
            File file = new File(base, t);
            file = file.getCanonicalFile();

            if (file.exists()) {
                classpaths.add(file);
            }
        }
    }

    public static JavaRuntimeInfo getJavaRuntimeInfo() {
        return JAVA_RUNTIME_INFO;
    }

    public static final class JavaRuntimeInfo {

        private final String SUN_BOOT_CLASS_PATH = getSystemProperty("sun.boot.class.path", false);

        private final String SUN_ARCH_DATA_MODEL = getSystemProperty("sun.arch.data.model", false);

        private final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url", false);

        private JavaRuntimeInfo() {
        }

        public final String getSunBootClassPath() {
            return SUN_BOOT_CLASS_PATH;
        }

        public final String getSunArchDataModel() {
            return SUN_ARCH_DATA_MODEL;
        }

        public final String getVendorURL() {
            return JAVA_VENDOR_URL;
        }

    }

    private static String getSystemProperty(String name, boolean quiet) {
        try {
            return System.getProperty(name);
        }
        catch (SecurityException e) {
            if (!quiet) {
                System.err.println(
                        "Caught a SecurityException reading the system property '" + name
                        + "'; the SystemUtil property value will default to null."
                );
            }

            return null;
        }
    }
}
