package com.buer.system.biz;

import com.buer.common.mybatis.enums.JavaCodeTypeEnum;
import com.buer.common.mybatis.generator.CodeGenerate;

/**
 * 代码生成器
 * 如果生成的文件已存在，则跳过该文件
 *
 * @author zoulan
 * @since 2025-07-18
 */
public class Generate {

    public static void main(String[] args) {
        CodeGenerate codeGenerate = new CodeGenerate(
            "jdbc:mysql://192.168.52.101:3306/buer_system?characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai",
            "root",
            "Buercloud@12356", "buer-system",
            "com.buer.system", "../buer-web");
        codeGenerate.generateCode("sys_driver", "driver", "司机", "zoulan", true, false, JavaCodeTypeEnum.CURD, "system/driver");
    }
}