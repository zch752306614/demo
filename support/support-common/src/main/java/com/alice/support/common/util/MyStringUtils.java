package com.alice.support.common.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 字符串处理工具类
 * @DateTime 2023/12/5 13:28
 */
public class MyStringUtils {

    /**
     * 检查字符串是否包含数组中的任何一个字符串
     *
     * @param mainString 校验的字符串
     * @return searchStrings 匹配的内容
     */
    public static boolean containsAny(String mainString, List<String> searchStrings) {
        return searchStrings.stream().anyMatch(mainString::contains);
    }

    /**
     * 检查字符串是否包含数组中的任何一个字符串
     *
     * @param mainString 校验的字符串
     * @return searchStrings 匹配的内容
     */
    public static boolean containsAny(String mainString, String... searchStrings) {
        return Arrays.stream(searchStrings).anyMatch(mainString::contains);
    }

    /**
     * 移除字符串数组中的字符串
     *
     * @param mainString 原字符串
     * @return searchStrings 需要移除的内容
     */
    private static String removeStrings(String mainString, String... stringsToRemove) {
        for (String stringToRemove : stringsToRemove) {
            mainString = mainString.replaceAll(stringToRemove, "");
        }
        return mainString;
    }

    /**
     * 移除字符串数组中的字符串
     *
     * @param mainString 原字符串
     * @return stringsToRemove 需要移除的内容
     */
    private static String removeStrings(String mainString, List<String> stringsToRemove) {
        for (String stringToRemove : stringsToRemove) {
            mainString = mainString.replaceAll(stringToRemove, "");
        }
        return mainString;
    }
}
