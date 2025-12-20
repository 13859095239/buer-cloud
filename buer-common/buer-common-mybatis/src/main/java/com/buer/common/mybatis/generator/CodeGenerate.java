package com.buer.common.mybatis.generator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.buer.common.mybatis.enums.JavaCodeTypeEnum;
import com.buer.common.mybatis.util.CodeGenerateUtil;
import com.buer.common.mybatis.util.FreeMarkerUtils;
import com.buer.common.mybatis.util.SqlToJavaTypeConverter;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成类
 *
 * @author zoulan
 * @since 2025-07-18
 */
public class CodeGenerate {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerate.class);
    private final String serviceDirName;
    private final String packageName;
    private final String webRelativePath;
    private final HikariDataSource dataSource;

    /**
     * 生成代码
     *
     * @param jdbcUrl         jdbc链接
     * @param username        用户名
     * @param password        密码、
     * @param serviceDirName  Java输出目录
     * @param packageName     Java包名
     * @param webRelativePath 前端相对后端的路径,如: ../buer-web
     */
    @SneakyThrows
    public CodeGenerate(String jdbcUrl, String username, String password, String serviceDirName, String packageName, String webRelativePath) {
        this.serviceDirName = serviceDirName;
        this.packageName = packageName;
        this.webRelativePath = webRelativePath;
        this.dataSource = new HikariDataSource();
        // 注意：url 需添加上 useInformationSchema=true 才能正常获取表的注释
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    /**
     * 生成java文件 curd代码
     *
     * @param tableName        表名
     * @param enTableSubName   英文简要名称，为null时，默认为表驼峰
     * @param cnTableSubName   中文简要名称，为null时，默认表的描述
     * @param author           作者
     * @param isVO             是否生成VO
     * @param isDTO            是否生成DTO
     * @param javaCodeTypeEnum 自动生成Java代码类型
     * @param webModulePath    前端模块路径,如：system/config,值为null时不生成前端
     */
    public void generateCode(String tableName, String enTableSubName, String cnTableSubName, String author, boolean isVO, boolean isDTO, JavaCodeTypeEnum javaCodeTypeEnum, String webModulePath) {
        logger.info("开始自动生成代码,表:{}", tableName);
        // 设置项目目录
        String packagePath = packageName.replaceAll("\\.", "/");
        String javaPath = "src/main/java";
        String resourcesPath = "src/main/resources";
        String apiPath = StrUtil.format("{}/{}-api/{}/{}/api", serviceDirName, serviceDirName, javaPath, packagePath);
        String bizPath = StrUtil.format("{}/{}-biz/{}/{}/biz", serviceDirName, serviceDirName, javaPath, packagePath);
        String bizResourcesPath = StrUtil.format("{}/{}-biz/{}", serviceDirName, serviceDirName, resourcesPath);
        // 传入模板的参数，参数可以是任意类型
        // 可以传入方法供模板调用
        Map<String, Object> params = new HashMap<>(4);
        // Java包名
        params.put("packageName", packageName);
        // 是否生成DTO
        params.put("isDTO", isDTO);
        // 是否生成VO
        params.put("isVO", isVO);

        // 英文简要名称-首字母小写
        enTableSubName = StrUtil.lowerFirst(enTableSubName);
        params.put("enTableSubName", enTableSubName);

        // 英文简要名称-首字母大写
        String enTableSubNameUpper = StrUtil.upperFirst(enTableSubName);
        params.put("enTableSubNameUpper", enTableSubNameUpper);

        // 英文简要名称-驼峰转横线
        String enTableSubNameLine = StrUtil.toSymbolCase(enTableSubName, '-');
        params.put("cnTableSubName", cnTableSubName);
        params.put("enTableSubNameLine", enTableSubNameLine);
        // 表对象
        Table tableInfo = MetaUtil.getTableMeta(dataSource, tableName);
        String tableNameUpperCamelCase = StrUtil.upperFirst(StrUtil.toCamelCase(tableName));

        // 判断是否有时间类型，如有模板要import对应包
        Boolean isLocalDateTime = tableInfo.getColumns()
                .stream()
                .anyMatch(column -> Arrays.asList("LocalDate", "LocalTime", "LocalDateTime", "OffsetDateTime")
                        .contains(SqlToJavaTypeConverter.convert(column.getTypeName())));
        params.put("isLocalDateTime", isLocalDateTime);

        // 判断是否有BigDecimal类型，如有模板要import对应包
        Boolean isBigDecimal = tableInfo.getColumns()
                .stream()
                .anyMatch(column -> "BigDecimal".equals(SqlToJavaTypeConverter.convert(column.getTypeName())));
        params.put("isBigDecimal", isBigDecimal);

        // 逻辑删除列
        params.put("logicDeleteColumnName", "del_flag");
        // 判断是否有逻辑删除列，如有模板要import对应包
        Boolean isLogicDeleteColumn = tableInfo.getColumns()
                .stream()
                .anyMatch(column -> "del_flag".equals(column.getName()));
        params.put("isLogicDeleteColumn", isLogicDeleteColumn);

        // 作者
        params.put("author", author);
        // 创建时间
        params.put("since", DateUtil.formatDate(new Date()));
        // 表对象
        params.put("table", tableInfo);

        // Controller Package 包
        String controllerPackage = StrUtil.format("{}.biz.controller", packageName);
        params.put("controllerPackage", controllerPackage);


        // Service Package 包
        String servicePackage = StrUtil.format("{}.biz.service", packageName);
        params.put("servicePackage", servicePackage);
        // Service Import 包
        String serviceImport = StrUtil.format("{}.biz.service.{}Service", packageName, tableNameUpperCamelCase);
        params.put("serviceImport", serviceImport);

        // ServiceImpl Package 包
        String serviceImplPackage = StrUtil.format("{}.biz.service.impl", packageName);
        params.put("serviceImplPackage", serviceImplPackage);

        // Mapper 实体
        String mapperEntity = tableNameUpperCamelCase + "Mapper";
        params.put("mapperEntity", mapperEntity);
        // Mapper Package 包
        String mapperPackage = StrUtil.format("{}.biz.mapper", packageName);
        params.put("mapperPackage", mapperPackage);
        // Mapper Import 包
        String mapperImport = StrUtil.format("{}.biz.mapper.{}Mapper", packageName, tableNameUpperCamelCase);
        params.put("mapperImport", mapperImport);

        // entity实体
        params.put("entity", tableNameUpperCamelCase);
        // entity 参数
        params.put("entityParam", enTableSubName);
        // entity实体,所有字符大写
        params.put("entityAllUpper", tableName.toUpperCase());
        // entityPackage 包
        String entityPackage = StrUtil.format("{}.api.entity", packageName);
        params.put("entityPackage", entityPackage);
        // entityImport 包
        String entityImport = StrUtil.format("{}.api.entity.{}", packageName, tableNameUpperCamelCase);
        params.put("entityImport", entityImport);

        // DTO 实体
        String dtoEntity = isDTO ? enTableSubNameUpper + "DTO" : tableNameUpperCamelCase;
        params.put("dtoEntity", dtoEntity);
        // DTO 参数
        String dtoParam = isDTO ? enTableSubName + "DTO" : enTableSubName;
        params.put("dtoParam", dtoParam);
        // DTO 包
        String dtoPackage = StrUtil.format("{}.api.dto", packageName);
        params.put("dtoPackage", dtoPackage);
        // DTO Import 包
        String dtoImport = StrUtil.format("{}.api.dto.{}", packageName, dtoEntity);
        params.put("dtoImport", dtoImport);

        // VO 实体
        String voEntity = isVO ? enTableSubNameUpper + "VO" : tableNameUpperCamelCase;
        params.put("voEntity", voEntity);
        // VO 参数
        String voParam = isVO ? enTableSubName + "VO" : enTableSubName;
        params.put("voParam", voParam);
        // VO 包
        String voPackage = StrUtil.format("{}.api.vo", packageName);
        params.put("voPackage", voPackage);
        // VO Import 包
        String voImport = StrUtil.format("{}.api.vo.{}", packageName, voEntity);
        params.put("voImport", voImport);

        // Query 实体
        String queryEntity = enTableSubNameUpper + "Query";
        params.put("queryEntity", queryEntity);
        // Query 参数
        String queryParam = StrUtil.lowerFirst(queryEntity);
        params.put("queryParam", queryParam);
        // Query 包
        String queryPackage = StrUtil.format("{}.api.query", packageName);
        params.put("queryPackage", queryPackage);
        // Query Import 包
        String queryImport = StrUtil.format("{}.api.query.{}", packageName, queryEntity);
        params.put("queryImport", queryImport);

        // 模板自定义方法
        // 所有给FreeMarker模板调用的自定义方法都放在这里
        params.put("utils", new FreeMarkerUtils());
        FreeMarkerTemplate.generate(params, "/template/java/entity.java.ftl",
                StrUtil.format("{}/{}/{}.java",
                        apiPath, "entity", tableNameUpperCamelCase));
        if (1 == 2) {
            if (isDTO) {
                // 生成DTO
                FreeMarkerTemplate.generate(params,
                        "/template/java/dto.java.ftl",
                        StrUtil.format("{}/{}/{}DTO.java",
                                apiPath, "dto", enTableSubNameUpper));
            }
            if (isVO) {
                // 生成VO
                FreeMarkerTemplate.generate(params, "/template/java/vo.java.ftl",
                        StrUtil.format("{}/{}/{}VO.java",
                                apiPath, "vo", enTableSubNameUpper));
            }
            // 生成Query
            FreeMarkerTemplate.generate(params, "/template/java/query.java.ftl",
                    StrUtil.format("{}/{}/{}Query.java",
                            apiPath, "query", enTableSubNameUpper));
            // 生成 Controller
            FreeMarkerTemplate.generate(params,
                    StrUtil.format("/template/java/{}.java.ftl", CodeGenerateUtil.getJavaTemplateName(javaCodeTypeEnum, "controller")),
                    StrUtil.format("{}/{}/{}Controller.java", bizPath, "controller", tableNameUpperCamelCase));
            // 生成 Service
            FreeMarkerTemplate.generate(params,
                    StrUtil.format("/template/java/{}.java.ftl", CodeGenerateUtil.getJavaTemplateName(javaCodeTypeEnum, "service")),
                    StrUtil.format("{}/{}/{}Service.java", bizPath, "service", tableNameUpperCamelCase));
            // 生成 ServiceImpl
            FreeMarkerTemplate.generate(params,
                    StrUtil.format("/template/java/{}.java.ftl", CodeGenerateUtil.getJavaTemplateName(javaCodeTypeEnum, "serviceImpl")),
                    StrUtil.format("{}/{}/{}ServiceImpl.java", bizPath, "Service/impl", tableNameUpperCamelCase));
            // 生成 Mapper
            FreeMarkerTemplate.generate(params, "/template/java/mapper.java.ftl",
                    StrUtil.format("{}/{}/{}Mapper.java", bizPath, "mapper", tableNameUpperCamelCase));
            // 生成 MapperXml
            FreeMarkerTemplate.generate(params, "/template/java/mapper.xml.ftl",
                    StrUtil.format("{}/{}/{}Mapper.xml", bizResourcesPath, "mapper", tableNameUpperCamelCase));
        }
        // 生成 Vue edit页面
        FreeMarkerTemplate.generate(params, "/template/vue/edit.vue.ftl",
                StrUtil.format("{}/views/{}/Edit.vue", webRelativePath, webModulePath));
        logger.info("结束自动生成代码,表:{}", tableName);
    }

}
