package com.buer.common.mybatis.util;

import com.buer.common.mybatis.enums.JavaCodeTypeEnum;

/**
 * 代码生成器通用方法
 *
 * @author zoulan
 * @since 2025-07-18
 */
public class CodeGenerateUtil {

    /**
     * 根据Java代码类型，返回模板后缀
     *
     * @param templateName 基础模板名称
     * @return 模板名称
     */
    public static String getJavaTemplateName(JavaCodeTypeEnum javaCodeTypeEnum, String templateName) {
        return switch (javaCodeTypeEnum) {
            case BLANK -> templateName + "Blank";
            case TREE_CURD -> templateName + "TreeCurd";
            default -> templateName;
        };
    }
}
