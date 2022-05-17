package com.funny.utils;

import com.funny.utils.helper.RegHelper;
import org.apache.commons.lang3.StringUtils;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.regex.Pattern;


public class StringUtil {
    public static final String NO_BREAK_SPACE = "(\r\n|\r|\n|\n\r)";




    public static String decimalFormat(double num) {
        if (num == 0) {
            return "0";
        }

        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }


    public static String decimalFormat(long num) {
        return String.format("%,d",num);
    }


    public static String zeroAddString(String numbering) {
        if (StringUtils.isEmpty(numbering)) {
            return numbering;
        }

        if (numbering.length() == 1) {
            return "0" + numbering;
        }

        return numbering;
    }


    public static String[] parseStringByBytes(String raw,int len,String encoding) {
        if (StringUtils.isEmpty(raw)) {
            return new String[0];
        }

        String[] ary = null;

        try {
            byte[] rawBytes = raw.getBytes(encoding);
            int rawLength = rawBytes.length;

            int index = 0;
            int minusByteNum = 0;
            int offset = 0;

            int hangulByteNum = encoding.equals("UTF-8") ? 3 : 2;

            if (rawLength > len) {
                int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
                ary = new String[aryLength];

                for (int i = 0; i < aryLength; i++) {
                    minusByteNum = 0;
                    offset = len;
                    if (index + offset > rawBytes.length) {
                        offset = rawBytes.length - index;
                    }
                    for (int j = 0; j < offset; j++) {
                        if (((int) rawBytes[index + j] & 0x80) != 0) {
                            minusByteNum++;
                        }
                    }
                    if (minusByteNum % hangulByteNum != 0) {
                        offset -= minusByteNum % hangulByteNum;
                    }
                    ary[i] = new String(rawBytes,index,offset,encoding);
                    index += offset;

                }
            } else {
                ary = new String[]{raw};
            }
        } catch (Exception ignored) {
        }

        return ary;
    }

    public static String cut(String s,int n) {
        byte[] utf8 = s.getBytes();
        if (utf8.length < n) n = utf8.length;
        int n16 = 0;
        boolean extraLong = false;
        int i = 0;

        while (i < n) {
            n16 += (extraLong) ? 2 : 1;
            extraLong = false;
            if ((utf8[i] & 0x80) == 0) i += 1;
            else if ((utf8[i] & 0xC0) == 0x80) i += 2;
            else if ((utf8[i] & 0xE0) == 0xC0) i += 3;
            else {
                i += 4;
                extraLong = true;
            }
        }

        return s.substring(0,n16);
    }


    public static String stringCut(String sourceText,String startKeyword,int cutLength,int startKewordPreviousLength,boolean isTag,boolean isDot) {
        String targetText = sourceText;
        int oF = 0;
        int oL = 0;
        int rF = 0;
        int rL = 0;
        int nLengthPrev = 0;

        Pattern p = RegHelper.CASE_INSENSITIVE_PATTERN;

        if (isTag) {
            targetText = p.matcher(targetText).replaceAll("");
        }

        targetText = targetText.replace("&amp;","&");
        targetText = targetText.replaceAll("(!/|\r|\n|&nbsp;)","");

        try {
            byte[] bytes = targetText.getBytes(StandardCharsets.UTF_8);
            if (startKeyword != null && !startKeyword.equals("")) {
                nLengthPrev = !targetText.contains(startKeyword) ? 0 : targetText.indexOf(startKeyword); // 일단 위치찾고
                nLengthPrev = targetText.substring(0,nLengthPrev).getBytes("MS949").length;
                nLengthPrev = Math.max(nLengthPrev - startKewordPreviousLength,0);
            }

            int j = 0;
            if (nLengthPrev > 0)
                while (j < bytes.length) {
                    if ((bytes[j] & 0x80) != 0) {
                        oF += 2;
                        rF += 3;
                        if (oF + 2 > nLengthPrev) {
                            break;
                        }
                        j += 3;
                    } else {
                        if (oF + 1 > nLengthPrev) {
                            break;
                        }
                        ++oF;
                        ++rF;
                        ++j;
                    }
                }

            j = rF;

            while (j < bytes.length) {
                if ((bytes[j] & 0x80) != 0) {
                    if (oL + 2 > cutLength) {
                        break;
                    }
                    oL += 2;
                    rL += 3;
                    j += 3;
                } else {
                    if (oL + 1 > cutLength) {
                        break;
                    }
                    ++oL;
                    ++rL;
                    ++j;
                }
            }

            targetText = new String(bytes,rF,rL,StandardCharsets.UTF_8);

            if (isDot && rF + rL + 3 <= bytes.length) {
                targetText += "...";
            }
        } catch (UnsupportedEncodingException ignored) {}

        return targetText;
    }


    public static String krStringByteSubstring(String parameterName,final int maxLength) {
        Charset utf8Charset = Charset.forName(StandardCharsets.UTF_8.displayName());
        CharsetDecoder cd = utf8Charset.newDecoder();

        try {
            byte[] sba = parameterName.getBytes(StandardCharsets.UTF_8.displayName());
            ByteBuffer bb = ByteBuffer.wrap(sba,0,maxLength);
            CharBuffer cb = CharBuffer.allocate(maxLength);
            cd.onMalformedInput(CodingErrorAction.IGNORE);
            cd.decode(bb,cb,true);
            cd.flush(cb);
            parameterName = new String(cb.array(),0,cb.position());
        } catch (UnsupportedEncodingException ignored) {}

        return parameterName;
    }


    public static String stringLengthLimit(final String str,final int limit) {
        return str.length() > limit ? str.substring(0,limit) : str;
    }


    public static String stringByteLengthLimit(final String str,final int limit) {
        return str.getBytes().length > limit ? new String(str.getBytes(),0,limit) : str;
    }


    public static String stringByteLengthLimit(final String str,final int limit,final String limitStr,final int limitByte) {
        if (str.length() > limit) {
            try {
                return cut(str,limitByte) + limitStr;
            } catch (StringIndexOutOfBoundsException e) {
                return str;
            }
        } else {
            return str;
        }
    }


    public static String[] excelEnterSplit(final String str) {
        return str.split(NO_BREAK_SPACE);
    }


    public static String stringTrim(String str) {
        return StringUtils.trim(str).replaceAll(NO_BREAK_SPACE,"");
    }


    public static String emojiRemove(String input) {
        return getEmojiRemoveString(input,"");
    }


    public static String emojiReplace(String input,String replaceStr) {
        return getEmojiRemoveString(input,replaceStr);
    }

    private static String getEmojiRemoveString(String input,String replaceStr) {
        return RegHelper.EMOJI_PATTERN.matcher(input).replaceAll(replaceStr);
    }
}
