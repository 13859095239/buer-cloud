package com.buer.common.mybatis.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;

/**
 * FreeMarker 模板中调用Java的方法
 * 模板调用的Java的所有方法，可以写在这个类中
 *
 * @author zoulan
 * @since 2025-07-18
 */
public class FreeMarkerUtils {

    /**
     * 将数据库字段类型转换为 Java 类型
     *
     * @param sqlType 数据库字段类型（如 varchar(255)、int4 等）
     * @return 对应的 Java 类型（如 String、Integer、BigDecimal 等）
     */
    public String toJavaType(String sqlType) {
        return SqlToJavaTypeConverter.convert(sqlType);
    }

    /**
     * 将输入内容转换成驼峰
     *
     * @param str 输入内容
     * @return 转换后的驼峰式命名的字符串
     */
    public String toCamelCase(String str) {
        return StrUtil.toCamelCase(str);
    }

    /**
     * 将输入内容转换成首字母大写的驼峰
     *
     * @param str 输入内容
     * @return 转换后首字母大写的驼峰式命名的字符串
     */
    public String toUpperCamelCase(String str) {
        return StrUtil.toCamelCase(str);
    }

    /**
     * 判断是否生成列对象
     * 生成Entity
     *
     * @param str 输入内容
     * @return 是否生成对象
     */
    public Boolean generateColumn(String str) {
        List<String> noGenerateList = Arrays.asList("createUser", "createTime", "updateUser", "updateTime");
        return !noGenerateList.contains(toCamelCase(str));
    }

    /**
     * 判断是否生成列对象
     * 生成VO、DTO
     *
     * @param str 输入内容
     * @return 是否生成对象
     */
    public Boolean generateColumnForVoAndDTO(String str) {
        List<String> noGenerateList = Arrays.asList("createUser", "createTime", "updateUser", "updateTime", "tenantId", "delFlag");
        return !noGenerateList.contains(toCamelCase(str));
    }
}
