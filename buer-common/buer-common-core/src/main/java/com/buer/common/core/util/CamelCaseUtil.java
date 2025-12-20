package com.buer.common.core.util;

/**
 * 驼峰转换工具类
 *
 * @author zoulan
 * @since 2021-10-23
 */
public class CamelCaseUtil {
    private static final char UNDER_LINE_SEPARATOR = '_';
    private static final char LINE_SEPARATOR = '-';

    /**
     * 驼峰组合转小写字母带符号形式
     *
     * @param str
     * @return
     */
    private static String toSeparator(String str, char separator) {
        if (null == str) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean nextUpperCase = true;
            if (i < (str.length() - 1)) {
                nextUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(separator);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 驼峰组合转小写字母下划线形式
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        return toSeparator(str, UNDER_LINE_SEPARATOR);
    }

    /**
     * 驼峰组合转小写字母横线形式
     *
     * @param str
     * @return
     */
    public static String toLine(String str) {
        return toSeparator(str, LINE_SEPARATOR);
    }

    /**
     * 下划线形式组合的字符串转小驼峰
     */
    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == UNDER_LINE_SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线形式组合的字符串转大驼峰
     */
    public static String toCapitalizeCamelCase(String str) {
        if (null == str) {
            return null;
        }
        str = toCamelCase(str);
        return str.substring(0, 1)
                .toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     */
    public static String toInitialLowercase(String str) {
        return str.substring(0, 1)
                .toLowerCase() + str.substring(1);
    }

}
