package com.funny.utils;

import com.funny.utils.constants.Emptys;
import com.funny.utils.helper.InOutStreamHelper;
import com.funny.utils.helper.LocaleInfo;
import org.apache.commons.lang3.StringUtils;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;


public class LocaleUtil {
    private static final LocaleInfo systemLocaleInfo = new LocaleInfo();
    private static LocaleInfo defaultLocalInfo = systemLocaleInfo;
    private static final ThreadLocal<LocaleInfo> contextLocaleInfoHolder = new ThreadLocal<LocaleInfo>();

    private static final Notifier[] notifiers = getNotifiers();
    private static Notifier[] getNotifiers() {
        try {
            URL[] files = ClassLoaderUtil.getResources("META-INF/services/localeNotifiers",ClassLoaderUtil.class);
            List<Notifier> list = CollectionUtil.createLinkedList();

            for (URL file : files) {
                for (String className : StringUtils.split(InOutStreamHelper.readText(file.openStream(),"UTF-8",true),"\r\n ")) {
                    list.add(Notifier.class.cast(ClassLoaderUtil.newInstance(className,ClassLoaderUtil.class)));
                }
            }

            return list.toArray(new Notifier[list.size()]);
        } catch (Exception e) {
            return new Notifier[0];
        }
    }




    public static boolean isLocaleSupported(Locale locale) {
        return locale != null &&
                AvailableLocalesLoader.locales.AVAILABLE_LANGUAGES.contains(locale.getLanguage()) &&
                AvailableLocalesLoader.locales.AVAILABLE_COUNTRIES.contains(locale.getCountry());
    }


    public static boolean isCharsetSupported(String charset) {
        return charset != null && Charset.isSupported(charset);
    }


    public static Locale parseLocale(String localeString) {
        localeString = StringUtils.trimToNull(localeString);

        if (localeString == null) {
            return null;
        }

        String language;
        String country = Emptys.EMPTY_STRING;
        String variant = Emptys.EMPTY_STRING;

        int start = 0;
        int index = localeString.indexOf("_");

        if (index >= 0) {
            language = localeString.substring(start,index).trim();

            start = index + 1;
            index = localeString.indexOf("_",start);

            if (index >= 0) {
                country = localeString.substring(start,index).trim();
                variant = localeString.substring(index + 1).trim();
            } else {
                country = localeString.substring(start).trim();
            }
        } else {
            language = localeString.substring(start).trim();
        }

        return new Locale(language,country,variant);
    }


    public static String getCanonicalCharset(String charset) {
        return Charset.forName(charset).name();
    }


    public static List<String> calculateBundleNames(String baseName,Locale locale) {
        return calculateBundleNames(baseName,locale,false);
    }


    public static List<String> calculateBundleNames(String baseName,Locale locale,boolean noext) {
        baseName = StringUtils.trimToEmpty(baseName);

        if (locale == null) {
            locale = new Locale(Emptys.EMPTY_STRING);
        }

        String ext = Emptys.EMPTY_STRING;
        int extLength = 0;

        if (!noext) {
            int extIndex = baseName.lastIndexOf(".");

            if (extIndex != -1) {
                ext = baseName.substring(extIndex,baseName.length());
                extLength = ext.length();
                baseName = baseName.substring(0,extIndex);

                if (extLength == 1) {
                    ext = Emptys.EMPTY_STRING;
                    extLength = 0;
                }
            }
        }

        LinkedList<String> result = CollectionUtil.createLinkedList();
        String language = locale.getLanguage();
        int languageLength = language.length();

        String country = locale.getCountry();
        int countryLength = country.length();

        String variant = locale.getVariant();
        int variantLength = variant.length();

        StringBuilder builder = new StringBuilder(baseName);

        builder.append(ext);
        result.addFirst(builder.toString());
        builder.setLength(builder.length() - extLength);

        if (languageLength + countryLength + variantLength == 0) {
            return result;
        }

        if (builder.length() > 0) {
            builder.append('_');
        }

        builder.append(language);

        if (languageLength > 0) {
            builder.append(ext);
            result.addFirst(builder.toString());
            builder.setLength(builder.length() - extLength);
        }

        if (countryLength + variantLength == 0) {
            return result;
        }

        builder.append('_').append(country);

        if (countryLength > 0) {
            builder.append(ext);
            result.addFirst(builder.toString());
            builder.setLength(builder.length() - extLength);
        }

        if (variantLength == 0) {
            return result;
        }

        builder.append('_').append(variant);

        builder.append(ext);
        result.addFirst(builder.toString());
        builder.setLength(builder.length() - extLength);

        return result;
    }


    public static LocaleInfo getSystem() {
        return systemLocaleInfo;
    }


    public static LocaleInfo getDefault() {
        return defaultLocalInfo == null ? systemLocaleInfo : defaultLocalInfo;
    }


    public static LocaleInfo setDefault(Locale locale) {
        LocaleInfo old = getDefault();
        setDefaultAndNotify(new LocaleInfo(locale,null,systemLocaleInfo));
        return old;
    }


    public static LocaleInfo setDefault(Locale locale,String charset) throws UnsupportedCharsetException {
        LocaleInfo old = getDefault();
        setDefaultAndNotify(new LocaleInfo(locale,charset,systemLocaleInfo));
        return old;
    }


    public static LocaleInfo setDefault(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        if (localeInfo == null) {
            return setDefault(null,null);
        }

        LocaleInfo old = getDefault();
        setDefaultAndNotify(localeInfo);
        return old;
    }


    private static void setDefaultAndNotify(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        defaultLocalInfo = localeInfo.assertCharsetSupported();

        for (Notifier notifier : notifiers) {
            notifier.defaultChanged(localeInfo);
        }
    }


    public static void resetDefault() {
        defaultLocalInfo = systemLocaleInfo;

        for (Notifier notifier : notifiers) {
            notifier.defaultReset();
        }
    }


    public static LocaleInfo getContext() {
        LocaleInfo contextLocaleInfo = contextLocaleInfoHolder.get();
        return contextLocaleInfo == null ? getDefault() : contextLocaleInfo;
    }


    public static LocaleInfo setContext(Locale locale) {
        LocaleInfo old = getContext();
        setContextAndNotify(new LocaleInfo(locale,null,defaultLocalInfo));
        return old;
    }


    public static LocaleInfo setContext(Locale locale,String charset) throws UnsupportedCharsetException {
        LocaleInfo old = getContext();
        setContextAndNotify(new LocaleInfo(locale,charset,defaultLocalInfo));
        return old;
    }


    public static LocaleInfo setContext(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        if (localeInfo == null) {
            return setContext(null,null);
        }

        LocaleInfo old = getContext();
        setContextAndNotify(localeInfo);
        return old;
    }

    private static void setContextAndNotify(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        contextLocaleInfoHolder.set(localeInfo.assertCharsetSupported());

        for (Notifier notifier : notifiers) {
            notifier.contextChanged(localeInfo);
        }
    }


    public static void resetContext() {
        contextLocaleInfoHolder.remove();

        for (Notifier notifier : notifiers) {
            notifier.contextReset();
        }
    }

    public interface Notifier extends EventListener {
        void defaultChanged(LocaleInfo newValue);
        void defaultReset();
        void contextChanged(LocaleInfo newValue);
        void contextReset();
    }

    private static class AvailableLocalesLoader {
        private static final AvailableLocales locales = new AvailableLocales();
    }

    private static class AvailableLocales {
        private final Set<String> AVAILABLE_LANGUAGES = CollectionUtil.createHashSet();
        private final Set<String> AVAILABLE_COUNTRIES = CollectionUtil.createHashSet();

        private AvailableLocales() {
            Locale[] availableLocales = Locale.getAvailableLocales();

            for (Locale locale : availableLocales) {
                AVAILABLE_LANGUAGES.add(locale.getLanguage());
                AVAILABLE_COUNTRIES.add(locale.getCountry());
            }
        }
    }
}
