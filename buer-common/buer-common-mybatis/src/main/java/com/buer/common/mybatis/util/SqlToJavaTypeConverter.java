package com.buer.common.mybatis.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Sql类型转JAVA类型
 *
 * @author zoulan
 * @since 2025-07-18
 */
public class SqlToJavaTypeConverter {
    private static final Map<String, String> SQL_TYPE_TO_JAVA_TYPE = new HashMap<>();

    static {
        // 字符串类型
        SQL_TYPE_TO_JAVA_TYPE.put("CHAR", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("VARCHAR", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("TEXT", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("TINYTEXT", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("MEDIUMTEXT", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("LONGTEXT", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("CLOB", "String");
        SQL_TYPE_TO_JAVA_TYPE.put("JSON", "String");

        // 布尔类型
        SQL_TYPE_TO_JAVA_TYPE.put("BOOLEAN", "Boolean");
        SQL_TYPE_TO_JAVA_TYPE.put("BOOL", "Boolean");
        SQL_TYPE_TO_JAVA_TYPE.put("BIT", "Boolean");

        // 数值类型
        SQL_TYPE_TO_JAVA_TYPE.put("TINYINT", "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put("SMALLINT", "Short");
        SQL_TYPE_TO_JAVA_TYPE.put("INT2", "Short");
        SQL_TYPE_TO_JAVA_TYPE.put("MEDIUMINT", "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put("INT", "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put("INTEGER", "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put("INT4", "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put("BIGINT", "Long");
        SQL_TYPE_TO_JAVA_TYPE.put("INT8", "Long");

        SQL_TYPE_TO_JAVA_TYPE.put("FLOAT", "Float");
        SQL_TYPE_TO_JAVA_TYPE.put("REAL", "Float");
        SQL_TYPE_TO_JAVA_TYPE.put("DOUBLE", "Double");
        SQL_TYPE_TO_JAVA_TYPE.put("FLOAT8", "Double");
        SQL_TYPE_TO_JAVA_TYPE.put("DECIMAL", "BigDecimal");
        SQL_TYPE_TO_JAVA_TYPE.put("NUMERIC", "BigDecimal");

        // 时间类型
        SQL_TYPE_TO_JAVA_TYPE.put("DATE", "LocalDate");
        SQL_TYPE_TO_JAVA_TYPE.put("TIME", "LocalTime");
        SQL_TYPE_TO_JAVA_TYPE.put("DATETIME", "LocalDateTime");
        SQL_TYPE_TO_JAVA_TYPE.put("TIMESTAMP", "LocalDateTime");
        SQL_TYPE_TO_JAVA_TYPE.put("TIMESTAMPTZ", "OffsetDateTime");

        // 二进制类型
        SQL_TYPE_TO_JAVA_TYPE.put("BINARY", "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put("VARBINARY", "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put("BLOB", "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put("BYTEA", "byte[]");

    }

    /**
     * 将数据库字段类型转换为 Java 类型
     *
     * @param sqlType 数据库字段类型（如 varchar(255)、int4 等）
     * @return 对应的 Java 类型（如 String、Integer、BigDecimal 等）
     */
    public static String convert(String sqlType) {
        if (sqlType == null || sqlType.isEmpty()) {
            return "Object";
        }

        String normalized = sqlType.toUpperCase(Locale.ENGLISH);
        int bracketIndex = normalized.indexOf('(');
        if (bracketIndex > 0) {
            // 去掉括号长度信息
            normalized = normalized.substring(0, bracketIndex);
        }

        return SQL_TYPE_TO_JAVA_TYPE.getOrDefault(normalized, "Object");
    }
}
