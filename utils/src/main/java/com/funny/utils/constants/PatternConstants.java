package com.funny.utils.constants;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PatternConstants {
    public static final String KOR = "KOR";
    public static final String ENG = "ENG";
    public static final String ENG_UPPER = "ENG_UPPER";
    public static final String ENG_LOWER = "ENG_LOWER";
    public static final String NUM = "NUM";
    public static final String SPC_1 = "SPC_1";
    public static final String SPC_2 = "SPC_2";
    public static final String SPC_3 = "SPC_3";
    public static final String SPC_SCH = "SPC_SCH";
    public static final String SPC_4 = "SPC_4";
    public static final String NOT_SPACE = "NOT_SPACE";
    public static final String SPACE = "SPACE";
    public static final String SPACE_MSG = "띄어쓰기";
    public static final String SPACE_FORMAT = "\\s";
    public static final String KOR_MSG = "한글";
    public static final String KOR_FORMAT = "ㄱ-ㅎㅏ-ㅣ가-힣";
    public static final String ENG_MSG = "영문";
    public static final String ENG_FORMAT = "a-zA-Z";
    public static final String ENG_UPPER_MSG = "영문 대문자";
    public static final String ENG_UPPER_FORMAT = "A-Z";
    public static final String ENG_LOWER_MSG = "영문 소문자";
    public static final String ENG_LOWER_FORMAT = "a-z";
    public static final String NUM_MSG = "숫자";
    public static final String NUM_FORMAT = "0-9";
    public static final String SPC_1_MSG = "특수문자[/ &]'";
    public static final String SPC_1_FORMAT = "\\/\\&\\'";
    public static final String SPC_2_MSG = "특수문자[- _ / ? ! ~ ( ) & % [ ] + ! * - ,. · ^]";
    public static final String SPC_2_FORMAT = "-_/!~&%^,·\\.\\?\\(\\)\\[\\]\\+\\*";
    public static final String SPC_3_MSG = "특수문자[~ ! @ # $ % ^ & * \\ \" ' + = ` | ( ) [ ] : ; - _ ※ ☆ ★ ○ ● ◎ △ ▲ ▽ ▼ → ← ↑ ↓ ↔ ◁ ◀ ▷ ▶ ♡ ♥ ? / ,· . ℃ ㈜ ± ■ □ ▣ ◆ ◇ ◈ ×]";
    public static final String SPC_3_FORMAT = "~!@#%&\\\\\\\"`\\'=:;\\-_※☆★○●◎△▲▽▼→←↑↓↔◁◀▷▶♡♥,·\\.\\+\\*\\?\\^\\$\\[\\]\\{\\}\\(\\)\\|\\/∼／？！＾｜：；．｀＿，·（）［］＋＝％￦′″＄＃＆＊＠℃㈜±■□▣◆◇◈×　";
    public static final String SPC_SCH_MSG = "% \\ 특수문자는 입력할 수 없습니다.";
    public static final String SPC_SCH_FORMAT = "%\\\\";
    public static final String SPC_4_MSG = "-";
    public static final String SPC_4_FORMAT = "\\-";
    public static final String YN_PATTERN = "(Y|N){1}";
    public static final String EMPTY = "\\p{Z}";
    public static final String ENTER_STR = "(\r\n|\r|\n|\n\r)";
    private static final Map<String,String[]> constantsMap = new HashMap<>();




    public static String getConstantsFormat(String type){
        return constantsMap.containsKey(type) ? constantsMap.get(type)[1] : "";
    }

    public static String getConstantsMessage(String... types){
        List<String> messageList = new ArrayList<>();

        if(StringUtils.isNoneEmpty(types)){
            Map<String,String[]> constantsMap = PatternConstants.getConstantsMap();
            for(String type : types){
                if(constantsMap.containsKey(type) && StringUtils.isNoneEmpty(constantsMap.get(type)[0])){
                    messageList.add(constantsMap.get(type)[0]);
                }
            }
        }
        return String.join(", ",messageList);
    }


    static {
        constantsMap.put(KOR,new String[]{KOR_MSG,KOR_FORMAT});
        constantsMap.put(ENG,new String[]{ENG_MSG,ENG_FORMAT});
        constantsMap.put(ENG_UPPER,new String[]{ENG_UPPER_MSG,ENG_UPPER_FORMAT});
        constantsMap.put(ENG_LOWER,new String[]{ENG_LOWER_MSG,ENG_LOWER_FORMAT});
        constantsMap.put(NUM,new String[]{NUM_MSG,NUM_FORMAT});
        constantsMap.put(SPC_1,new String[]{SPC_1_MSG,SPC_1_FORMAT});
        constantsMap.put(SPC_2,new String[]{SPC_2_MSG,SPC_2_FORMAT});
        constantsMap.put(SPC_3,new String[]{SPC_3_MSG,SPC_3_FORMAT});
        constantsMap.put(SPC_4,new String[]{SPC_4_MSG,SPC_4_FORMAT});
        constantsMap.put(NOT_SPACE,new String[]{"",""});
        constantsMap.put(SPC_SCH,new String[]{SPC_SCH_MSG,SPC_SCH_FORMAT});
        constantsMap.put(SPACE,new String[]{SPACE_MSG,SPACE_FORMAT});
    }

    public static Map<String,String[]> getConstantsMap(){
        return constantsMap;
    }
}
