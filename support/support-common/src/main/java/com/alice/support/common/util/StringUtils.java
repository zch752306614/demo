package com.alice.support.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 字符串处理工具类
 * @DateTime 2023/12/5 13:28
 */
public class StringUtils {

    private static final Map<Character, Integer> chineseToArabic = new HashMap<>();

    static {
        chineseToArabic.put('零', 0);
        chineseToArabic.put('一', 1);
        chineseToArabic.put('二', 2);
        chineseToArabic.put('三', 3);
        chineseToArabic.put('四', 4);
        chineseToArabic.put('五', 5);
        chineseToArabic.put('六', 6);
        chineseToArabic.put('七', 7);
        chineseToArabic.put('八', 8);
        chineseToArabic.put('九', 9);
        chineseToArabic.put('十', 10);
        chineseToArabic.put('百', 100);
        chineseToArabic.put('千', 1000);
    }

    /**
     * 汉字转阿拉伯数字
     *
     * @param chineseNumber 汉字
     * @return int
     */
    public static int convertChineseToArabic(String chineseNumber) {
        int result = 0;
        int currentNumber = 0;

        for (char c : chineseNumber.toCharArray()) {
            int digit = chineseToArabic.get(c);

            if (digit < 10) {
                currentNumber = digit;
            } else if (digit < 100) {
                currentNumber *= digit;
            } else {
                result += currentNumber * digit;
                currentNumber = 0;
            }
        }

        result += currentNumber;
        return result;
    }

    public static void main(String[] args) {
        String chineseNumber = "二千三百四十五";
        String title = "第二千三百四十五章";
        String titleNumber = title.substring(1, title.indexOf("章"));
        int arabicNumber = convertChineseToArabic(titleNumber);
        System.out.println(chineseNumber + " 转换为阿拉伯数字为: " + arabicNumber);
    }

}
