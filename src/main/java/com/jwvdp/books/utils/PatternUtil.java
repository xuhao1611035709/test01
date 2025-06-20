package com.jwvdp.books.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    private static Pattern IS_NUM = Pattern.compile("^[0-9]*$");
    private static Pattern FIRST_CHAR = Pattern.compile("^[a-zA-Z].*");

    /*
    *判断是否是纯数字
    */
    public static boolean  isAllNum(String str) {
        Matcher matcherNew = IS_NUM.matcher(str);
        boolean isAllNum = matcherNew.matches();
        return  isAllNum;
    }

    /*
     *判断是否以字母开头
     */
    public static boolean  isFirstChar(String str) {
        Matcher matcherNew = IS_NUM.matcher(str);
        boolean isAllNum = matcherNew.matches();
        return  isAllNum;
    }
}
