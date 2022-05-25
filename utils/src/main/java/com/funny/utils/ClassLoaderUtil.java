package com.funny.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


public class ClassLoaderUtil {
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    public static String getClasspath() {
        URL resource = ClassLoaderUtil.getResource("");
        return (resource == null) ? null : resource.getPath();
    }


    public static <T> Class<T> loadClass(String className) throws ClassNotFoundException {
        return loadClass(className,getContextClassLoader());
    }


    public static <T> Class<T> loadClass(String className,Class<?> referrer) throws ClassNotFoundException {
        return loadClass(className,getReferrerClassLoader(referrer));
    }


    public static <T> Class<T> loadClass(String className,ClassLoader classLoader) throws ClassNotFoundException {
        if (StringUtils.isBlank(className)) {
            return null;
        }

        return (classLoader == null) ?
                (Class<T>) Class.forName(className) :
                (Class<T>) Class.forName(className,true,classLoader);
    }


    public static <T> Class<T> loadServiceClass(String serviceId) throws ClassNotFoundException {
        return loadServiceClass(serviceId,getContextClassLoader());
    }


    public static <T> Class<T> loadServiceClass(String serviceId,Class<?> referrer) throws ClassNotFoundException {
        return loadServiceClass(serviceId,getReferrerClassLoader(referrer));
    }


    public static <T> Class<T> loadServiceClass(String serviceId,ClassLoader classLoader) throws ClassNotFoundException {
        if (StringUtils.isBlank(serviceId)) {
            return null;
        }

        serviceId = "META-INF/services/" + serviceId;

        InputStream istream = getResourceAsStream(serviceId,classLoader);
        if (istream == null) {
            throw new ClassNotFoundException("Could not find " + serviceId);
        }

        String serviceClassName;

        try {
            serviceClassName = StringUtils.trimToEmpty(InOutStreamUtil.readText(istream,"UTF-8",true));
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load " + serviceId,e);
        }

        return ClassLoaderUtil.loadClass(serviceClassName,classLoader);
    }


    public static <T> Class<T> loadServiceClass(String className,String serviceId) throws ClassNotFoundException {
        return loadServiceClass(className,serviceId,getContextClassLoader());
    }


    public static <T> Class<T> loadServiceClass(String className,String serviceId,Class<T> referrer) throws ClassNotFoundException {
        return loadServiceClass(className,serviceId,getReferrerClassLoader(referrer));
    }


    public static <T> Class<T> loadServiceClass(String className,String serviceId,ClassLoader classLoader) throws ClassNotFoundException {
        return StringUtils.isNotBlank(className) ? loadClass(className,classLoader) : loadServiceClass(serviceId,classLoader);
    }


    private static <T> ClassLoader getReferrerClassLoader(Class<T> referrer) {
        if (referrer == null) {
            return null;
        }

        ClassLoader classLoader = referrer.getClassLoader();
        return (classLoader == null) ? ClassLoader.getSystemClassLoader() : classLoader;
    }


    public static <T> T newInstance(String className) throws ClassNotFoundException {
        return newInstance(loadClass(className));
    }


    public static Object newInstance(String className,Class<?> referrer) throws ClassNotFoundException {
        return newInstance(loadClass(className,referrer));
    }


    public static Object newInstance(String className,ClassLoader classLoader) throws ClassNotFoundException {
        return newInstance(loadClass(className,classLoader));
    }


    public static <T> T newInstance(Class<T> clazz) throws ClassNotFoundException {
        if (clazz == null) {
            return null;
        }

        try {
            return (T) clazz;
        } catch (Exception e) {
            throw new ClassNotFoundException("Failed to instantiate class: " + clazz.getName());
        }
    }


    public static <T> T newServiceInstance(String serviceId) throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId));
    }


    public static <T> T newServiceInstance(String serviceId,Class<T> referrer) throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId,referrer));
    }


    public static <T> T newServiceInstance(String serviceId,ClassLoader classLoader) throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId,classLoader));
    }


    public static <T> T newServiceInstance(String className,String serviceId) throws ClassNotFoundException {
        return newInstance(loadServiceClass(className,serviceId));
    }


    public static <T> T newServiceInstance(String className,String serviceId,Class<T> referrer) throws ClassNotFoundException {
        return newInstance(loadServiceClass(className,serviceId,referrer));
    }


    public static <T> T newServiceInstance(String className,String serviceId,ClassLoader classLoader) throws ClassNotFoundException {
        return newInstance(loadServiceClass(className,serviceId,classLoader));
    }


    public static URL[] getResources(String resourceName) {
        List<URL> urls = new ArrayList<>();
        boolean found = false;
        found = getResources(urls,resourceName,getContextClassLoader(),false);

        if (!found) {
            getResources(urls,resourceName,ClassLoaderUtil.class.getClassLoader(),false);
        }

        if (!found) {
            getResources(urls,resourceName,null,true);
        }

        return getDistinctURLs(urls);
    }


    public static URL[] getResources(String resourceName,Class<?> referrer) {
        List<URL> urls = new ArrayList<>();
        ClassLoader classLoader = getReferrerClassLoader(referrer);
        getResources(urls,resourceName,classLoader,classLoader == null);
        return getDistinctURLs(urls);
    }


    public static URL[] getResources(String resourceName,ClassLoader classLoader) {
        List<URL> urls = new ArrayList<>();
        getResources(urls,resourceName,classLoader,classLoader == null);
        return getDistinctURLs(urls);
    }


    private static boolean getResources(List<URL> urlSet,String resourceName,ClassLoader classLoader,boolean sysClassLoader) {
        if (StringUtils.isBlank(resourceName)) {
            return false;
        }

        Enumeration<URL> enums = null;
        try {
            if (classLoader != null) {
                enums = classLoader.getResources(resourceName);
            } else if (sysClassLoader) {
                enums = ClassLoader.getSystemResources(resourceName);
            }
        } catch (IOException e) {
            return false;
        }

        if (enums == null || !enums.hasMoreElements()) {
            return false;
        }

        while (enums.hasMoreElements()) {
            urlSet.add(enums.nextElement());
        }

        return true;
    }


    private static URL[] getDistinctURLs(List<URL> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return new URL[0];
        }

        Set<URL> urlSet = CollectionUtil.createHashSet();
        for (Iterator<URL> i = urls.iterator(); i.hasNext();) {
            URL url = i.next();

            if (urlSet.contains(url)) {
                i.remove();
            } else {
                urlSet.add(url);
            }
        }

        return urls.toArray(new URL[urlSet.size()]);
    }


    public static URL getResource(String resourceName) {
        if (StringUtils.isEmpty(resourceName)) {
            return null;
        }

        URL url = null;
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resourceName);

            if (url != null) {
                return url;
            }
        }

        classLoader = ClassLoaderUtil.class.getClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resourceName);

            if (url != null) {
                return url;
            }
        }

        return ClassLoader.getSystemResource(resourceName);
    }


    public static URL getResource(String resourceName,Class<?> referrer) {
        if (StringUtils.isEmpty(resourceName)) {
            return null;
        }

        ClassLoader classLoader = getReferrerClassLoader(referrer);
        return (classLoader == null) ?
                ClassLoaderUtil.class.getClassLoader().getResource(resourceName) :
                classLoader.getResource(resourceName);
    }


    public static URL getResource(String resourceName,ClassLoader classLoader) {
        if (StringUtils.isEmpty(resourceName)) {
            return null;
        }

        return (classLoader == null) ?
                ClassLoaderUtil.class.getClassLoader().getResource(resourceName) :
                classLoader.getResource(resourceName);
    }


    public static InputStream getResourceAsStream(String resourceName) {
        URL url = getResource(resourceName);
        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException ignored) {}

        return null;
    }


    public static InputStream getResourceAsStream(String resourceName,Class<?> referrer) {
        URL url = getResource(resourceName,referrer);
        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException ignored) {}

        return null;
    }


    public static InputStream getResourceAsStream(String resourceName,ClassLoader classLoader) {
        URL url = getResource(resourceName,classLoader);
        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException ignored) {}

        return null;
    }


    public static URL[] whichClasses(String className) throws IOException {
        return getResources(ClassUtil.getClassNameAsResource(className));
    }


    public static <T> URL[] whichClasses(String className,Class<T> referrer) throws IOException {
        return getResources(ClassUtil.getClassNameAsResource(className),referrer);
    }


    public static URL[] whichClasses(String className,ClassLoader classLoader) throws IOException {
        return getResources(ClassUtil.getClassNameAsResource(className),classLoader);
    }


    public static URL whichClass(String className) {
        return getResource(ClassUtil.getClassNameAsResource(className));
    }


    public static <T> URL whichClass(String className,Class<T> referrer) {
        return getResource(ClassUtil.getClassNameAsResource(className),referrer);
    }


    public static URL whichClass(String className,ClassLoader classLoader) {
        return getResource(ClassUtil.getClassNameAsResource(className),classLoader);
    }


    private ClassLoaderUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
