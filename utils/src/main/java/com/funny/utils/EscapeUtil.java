package com.funny.utils;

import com.funny.utils.constants.Entities;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.BitSet;


public class EscapeUtil {
    public static String escapeJava(String str) throws IOException {
        return escapeJavaStyleString(str,false,false);
    }


    public static String escapeJava(String str,boolean strict) throws IOException {
        return escapeJavaStyleString(str,false,strict);
    }


    public static void escapeJava(String str,Appendable out) throws IOException {
        escapeJavaStyleString(str,false,out,false);
    }


    public static void escapeJava(String str,Appendable out,boolean strict) throws IOException {
        escapeJavaStyleString(str,false,out,strict);
    }


    public static String escapeJavaScript(String str) throws IOException {
        return escapeJavaStyleString(str,true,false);
    }


    public static String escapeJavaScript(String str,boolean strict) throws IOException {
        return escapeJavaStyleString(str,true,strict);
    }


    public static void escapeJavaScript(String str,Appendable out) throws IOException {
        escapeJavaStyleString(str,true,out,false);
    }


    public static void escapeJavaScript(String str,Appendable out,boolean strict) throws IOException {
        escapeJavaStyleString(str,true,out,strict);
    }

    private static String escapeJavaStyleString(String str,boolean javascript,boolean strict) throws IOException {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(str.length() * 2);
        if (escapeJavaStyleString(str,javascript,out,strict)) {
            return out.toString();
        }

        return str;
    }

    private static boolean escapeJavaStyleString(String str,boolean javascript,Appendable out,boolean strict) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();
        for (int i=0;i<length;i++) {
            char ch = str.charAt(i);

            if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                    break;

                    case '\n':
                        out.append('\\');
                        out.append('n');
                    break;

                    case '\t':
                        out.append('\\');
                        out.append('t');
                    break;

                    case '\f':
                        out.append('\\');
                        out.append('f');
                    break;

                    case '\r':
                        out.append('\\');
                        out.append('r');
                    break;

                    default:
                        if (ch > 0xf) {
                            out.append("\\u00" + Integer.toHexString(ch).toUpperCase());
                        } else {
                            out.append("\\u000" + Integer.toHexString(ch).toUpperCase());
                        }

                    break;
                }

                needToChange = true;
            }

            else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    out.append("\\u").append(Integer.toHexString(ch).toUpperCase());
                } else {
                    out.append("\\u0").append(Integer.toHexString(ch).toUpperCase());
                }

                needToChange = true;
            }

            else {
                switch (ch) {
                    case '\'':
                    case '/':
                        if (javascript) {
                            out.append('\\');
                            needToChange = true;
                        }

                        out.append(ch);
                    break;

                    case '"':
                        out.append('\\');
                        out.append('"');
                        needToChange = true;
                    break;

                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        needToChange = true;
                    break;

                    default:
                        out.append(ch);
                    break;
                }
            }
        }

        return needToChange;
    }


    public static String unescapeJava(String str) throws IOException {
        return unescapeJavaStyleString(str);
    }


    public static void unescapeJava(String str,Appendable out) throws IOException {
        unescapeJavaStyleString(str,out);
    }


    public static String unescapeJavaScript(String str) throws IOException {
        return unescapeJavaStyleString(str);
    }


    public static void unescapeJavaScript(String str,Appendable out) throws IOException {
        unescapeJavaStyleString(str,out);
    }


    private static String unescapeJavaStyleString(String str) throws IOException {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(str.length());

        if (unescapeJavaStyleString(str,out)) {
            return out.toString();
        }

        return str;
    }

    private static boolean unescapeJavaStyleString(String str,Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();
        StringBuilder unicode = new StringBuilder(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (inUnicode) {
                unicode.append(ch);

                if (unicode.length() == 4) {
                    String unicodeStr = unicode.toString();

                    try {
                        int value = Integer.parseInt(unicodeStr,16);

                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;

                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append("\\u" + unicodeStr);
                    }
                }

                continue;
            }

            if (hadSlash) {
                hadSlash = false;

                switch (ch) {
                    case '\\':
                        out.append('\\');
                        needToChange = true;
                    break;

                    case '\'':
                        out.append('\'');
                        needToChange = true;
                    break;

                    case '\"':
                        out.append('"');
                        needToChange = true;
                    break;

                    case 'r':
                        out.append('\r');
                        needToChange = true;
                    break;

                    case 'f':
                        out.append('\f');
                        needToChange = true;
                    break;

                    case 't':
                        out.append('\t');
                        needToChange = true;
                    break;

                    case 'n':
                        out.append('\n');
                        needToChange = true;
                    break;

                    case 'b':
                        out.append('\b');
                        needToChange = true;
                    break;

                    case 'u':
                        inUnicode = true;
                    break;

                    default:
                        out.append(ch);
                    break;
                }

                continue;
            }

            else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            out.append(ch);
        }

        if (hadSlash) {
            out.append('\\');
        }

        return needToChange;
    }


    public static String escapeHtml(String str) throws IOException {
        return escapeEntities(Entities.HTML40_MODIFIED,str);
    }


    public static void escapeHtml(String str,Appendable out) throws IOException {
        escapeEntities(Entities.HTML40_MODIFIED,str,out);
    }


    public static String escapeXml(String str) throws IOException {
        return escapeEntities(Entities.XML,str);
    }


    public static void escapeXml(String str,Appendable out) throws IOException {
        escapeEntities(Entities.XML,str,out);
    }


    public static String escapeEntities(Entities entities,String str) throws IOException {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(str.length());

        if (escapeEntitiesInternal(entities,str,out)) {
            return out.toString();
        }

        return str;
    }


    public static void escapeEntities(Entities entities,String str,Appendable out) throws IOException {
        escapeEntitiesInternal(entities,str,out);
    }


    public static String unescapeHtml(String str) throws IOException {
        return unescapeEntities(Entities.HTML40,str);
    }


    public static void unescapeHtml(String str,Appendable out) throws IOException {
        unescapeEntities(Entities.HTML40,str,out);
    }


    public static String unescapeXml(String str) throws IOException {
        return unescapeEntities(Entities.XML,str);
    }


    public static void unescapeXml(String str,Appendable out) throws IOException {
        unescapeEntities(Entities.XML,str,out);
    }


    public static String unescapeEntities(Entities entities,String str) throws IOException {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(str.length());

        if (unescapeEntitiesInternal(entities,str,out)) {
            return out.toString();
        }

        return str;
    }


    public static void unescapeEntities(Entities entities,String str,Appendable out) throws IOException {
        unescapeEntitiesInternal(entities,str,out);
    }

    private static boolean escapeEntitiesInternal(Entities entities,String str,Appendable out) throws IOException {
        boolean needToChange = false;

        if (entities == null) {
            throw new IllegalArgumentException("The Entities must not be null");
        }

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i=0,cnt=str.length();i<cnt; ++i) {
            char ch = str.charAt(i);
            String entityName = entities.getEntityName(ch);

            if (entityName == null) {
                out.append(ch);
            } else {
                out.append('&');
                out.append(entityName);
                out.append(';');
                needToChange = true;
            }
        }

        return needToChange;
    }

    private static boolean unescapeEntitiesInternal(Entities entities,String str,Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i=0,cnt=str.length();i<cnt;++i) {
            char ch = str.charAt(i);

            if (ch == '&') {
                int semi = str.indexOf(';',i + 1);

                if (semi == -1 || i + 1 >= semi - 1) {
                    out.append(ch);
                    continue;
                }

                if (str.charAt(i + 1) == '#') {
                    int firstCharIndex = i + 2;
                    int radix = 10;

                    if (firstCharIndex >= semi - 1) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }

                    char firstChar = str.charAt(firstCharIndex);

                    if (firstChar == 'x' || firstChar == 'X') {
                        firstCharIndex++;
                        radix = 16;

                        if (firstCharIndex >= semi - 1) {
                            out.append(ch);
                            out.append('#');
                            i++;
                            continue;
                        }
                    }

                    try {
                        int entityValue = Integer.parseInt(str.substring(firstCharIndex,semi),radix);
                        out.append((char) entityValue);
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }
                } else {
                    String entityName = str.substring(i + 1,semi);
                    int entityValue = -1;

                    if (entities != null) {
                        entityValue = entities.getEntityValue(entityName);
                    }

                    if (entityValue == -1) {
                        out.append('&');
                        out.append(entityName);
                        out.append(';');
                    } else {
                        out.append((char) entityValue);
                        needToChange = true;
                    }
                }

                i = semi;
            } else {
                out.append(ch);
            }
        }

        return needToChange;
    }

    public static String escapeSql(String str) {
        return StringUtils.replace(str,"'","''");
    }

    public static void escapeSql(String str,Appendable out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        String result = StringUtils.replace(str,"'","''");

        if (result != null) {
            out.append(result);
        }
    }

    private static final BitSet ALPHA = new BitSet(256);

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            ALPHA.set(i);
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            ALPHA.set(i);
        }
    }

    private static final BitSet ALPHANUM = new BitSet(256);

    static {
        ALPHANUM.or(ALPHA);

        for (int i = '0'; i <= '9'; i++) {
            ALPHANUM.set(i);
        }
    }

    private static final BitSet MARK = new BitSet(256);

    static {
        MARK.set('-');
        MARK.set('_');
        MARK.set('.');
        MARK.set('!');
        MARK.set('~');
        MARK.set('*');
        MARK.set('\'');
        MARK.set('(');
        MARK.set(')');
    }

    /** "Reserved" characters from RFC 2396. */
    private static final BitSet RESERVED = new BitSet(256);

    static {
        RESERVED.set(';');
        RESERVED.set('/');
        RESERVED.set('?');
        RESERVED.set(':');
        RESERVED.set('@');
        RESERVED.set('&');
        RESERVED.set('=');
        RESERVED.set('+');
        RESERVED.set('$');
        RESERVED.set(',');
    }

    private static final BitSet UNRESERVED = new BitSet(256);

    static {
        UNRESERVED.or(ALPHANUM);
        UNRESERVED.or(MARK);
    }

    private static char[] HEXADECIMAL = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' };

    public static String escapeURL(String str) {
        try {
            return escapeURLInternal(str,null,true);
        } catch (Exception e) {
            return str;
        }
    }

    public static String escapeURL(String str,String encoding) throws Exception {
        return escapeURLInternal(str,encoding,true);
    }

    public static String escapeURL(String str,String encoding,boolean strict) throws Exception {
        return escapeURLInternal(str,encoding,strict);
    }

    public static void escapeURL(String str,String encoding,Appendable out) throws IOException {
        escapeURLInternal(str,encoding,out,true);
    }

    public static void escapeURL(String str,String encoding,Appendable out,boolean strict) throws IOException {
        escapeURLInternal(str,encoding,out,strict);
    }

    private static String escapeURLInternal(String str,String encoding,boolean strict) throws Exception {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(64);

        if (escapeURLInternal(str,encoding,out,strict)) {
            return out.toString();
        }

        return str;
    }

    private static boolean escapeURLInternal(String str,String encoding,Appendable out,boolean strict) throws IOException {
        if (encoding == null) {
            encoding = LocaleUtil.getContext().getCharset().name();
        }

        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        char[] charArray = str.toCharArray();
        int length = charArray.length;

        for (int ch : charArray) {
            if (isSafeCharacter(ch, strict)) {
                out.append((char) ch);
            } else if (ch == ' ') {
                out.append('+');
                needToChange = true;
            } else {
                byte[] bytes = String.valueOf((char) ch).getBytes(encoding);

                for (byte toEscape : bytes) {
                    out.append('%');

                    int low = toEscape & 0x0F;
                    int high = (toEscape & 0xF0) >> 4;

                    out.append(HEXADECIMAL[high]);
                    out.append(HEXADECIMAL[low]);
                }

                needToChange = true;
            }
        }

        return needToChange;
    }

    private static boolean isSafeCharacter(int ch,boolean strict) {
        if (strict) {
            return UNRESERVED.get(ch);
        }

        return ch > ' ' && !RESERVED.get(ch) && !Character.isWhitespace((char) ch);
    }


    public static String unescapeURL(String str) {
        try {
            return unescapeURLInternal(str,null);
        } catch (Exception e) {
            return str;
        }
    }


    public static String unescapeURL(String str,String encoding) throws Exception {
        return unescapeURLInternal(str,encoding);
    }


    public static void unescapeURL(String str,String encoding,Appendable out) throws IOException {
        unescapeURLInternal(str,encoding,out);
    }

    private static String unescapeURLInternal(String str,String encoding) throws Exception {
        if (str == null) {
            return null;
        }

        StringBuilder out = new StringBuilder(str.length());

        if (unescapeURLInternal(str,encoding,out)) {
            return out.toString();
        }

        return str;
    }

    private static boolean unescapeURLInternal(String str,String encoding,Appendable out) throws IOException {
        if (encoding == null) {
            encoding = LocaleUtil.getContext().getCharset().name();
        }

        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        byte[] buffer = null;
        int pos = 0;
        int startIndex = 0;

        char[] charArray = str.toCharArray();
        int length = charArray.length;

        for (int i = 0; i < length; i++) {
            int ch = charArray[i];

            if (ch < 256) {
                if (buffer == null) {
                    buffer = new byte[length - i];
                }

                if (pos == 0) {
                    startIndex = i;
                }

                switch (ch) {
                    case '+':
                        buffer[pos++] = ' ';
                        needToChange = true;
                        break;

                    case '%':

                        if (i + 2 < length) {
                            try {
                                byte b = (byte) Integer.parseInt(str.substring(i + 1,i + 3),16);

                                buffer[pos++] = b;
                                i += 2;
                                needToChange = true;
                            } catch (NumberFormatException e) {
                                buffer[pos++] = (byte) ch;
                            }
                        } else {
                            buffer[pos++] = (byte) ch;
                        }

                        break;

                    default:
                        buffer[pos++] = (byte) ch;
                        break;
                }
                continue;
            }

            if (pos > 0) {
                String s = new String(buffer,0,pos,encoding);

                out.append(s);

                if (!needToChange && !s.equals(new String(charArray,startIndex,pos))) {
                    needToChange = true;
                }

                pos = 0;
            }
            out.append((char) ch);
        }

        if (pos > 0) {
            String s = new String(buffer,0,pos,encoding);

            out.append(s);

            if (!needToChange && !s.equals(new String(charArray,startIndex,pos))) {
                needToChange = true;
            }

            pos = 0;
        }

        return needToChange;
    }
}
