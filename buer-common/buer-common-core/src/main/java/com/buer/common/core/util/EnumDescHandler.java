package com.buer.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.buer.common.core.annotation.EnumMapping;
import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.core.paginate.Page;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举描述填充工具类
 * <p>
 * 用于将 VO 中的枚举字段的描述值填充到对应的 "xxxDesc" 字段，
 * 前端可以直接使用 "xxxDesc" 来显示枚举的中文或描述信息。
 * <p>
 * 枚举类必须实现 {@link BaseEnum}
 * <p>
 * 示例：
 * <pre>
 * public enum EnableEnum implements BaseEnum<Integer> {
 *     ENABLE(1, "启用"),
 *     DISABLE(0, "禁用");
 *     ...
 * }
 *
 * public class UserVO {
 *     @EnumMapping(value = EnableEnum.class)
 *     private Integer status;
 *     private String statusDesc;
 * }
 * </pre>
 *
 * @author zoulan
 * @since 2025-09-26
 */
public class EnumDescHandler {

    /**
     * VO 类字段缓存：Class -> Field[]
     */
    private static final Map<Class<?>, Field[]> fieldCache = new ConcurrentHashMap<>();

    /**
     * 枚举 code->枚举实例 缓存：EnumClass -> Map<code, BaseEnum>
     */
    private static final Map<Class<?>, Map<Object, BaseEnum<?>>> enumCache = new ConcurrentHashMap<>();

    /**
     * 填充单个对象枚举描述
     */
    public static void fillEnumDesc(Object vo) {
        if (vo == null) return;

        // 获取字段数组并缓存
        Field[] fields = fieldCache.computeIfAbsent(vo.getClass(), ReflectUtil::getFields);

        for (Field field : fields) {
            EnumMapping ann = field.getAnnotation(EnumMapping.class);
            if (ann == null) continue;

            try {
                // 获取字段值（数据库中存储的 code）
                Object code = ReflectUtil.getFieldValue(vo, field);
                if (code == null) continue;

                // 描述字段名
                String descFieldName = ann.descField()
                    .isEmpty() ? field.getName() + "Desc" : ann.descField();
                Field descField = ReflectUtil.getField(vo.getClass(), descFieldName);
                if (descField == null) continue;

                // 枚举缓存：code -> BaseEnum
                Map<Object, BaseEnum<?>> codeEnumMap = enumCache.computeIfAbsent(ann.value(), cls -> {
                    Map<Object, BaseEnum<?>> map = new ConcurrentHashMap<>();
                    for (Object e : cls.getEnumConstants()) {
                        if (e instanceof BaseEnum<?> baseEnum) {
                            map.put(baseEnum.getCode(), baseEnum);
                        }
                    }
                    return map;
                });

                // 从缓存中取 BaseEnum，再取 desc
                BaseEnum<?> baseEnum = codeEnumMap.get(code);
                String desc = baseEnum != null ? baseEnum.getDesc() : null;

                // 写入 VO 的 xxxDesc 字段
                ReflectUtil.setFieldValue(vo, descField, desc);

            } catch (Exception ignored) {
                // 可根据需要打印日志
            }
        }
    }

    /**
     * 填充集合对象枚举描述
     */
    public static void fillEnumDesc(Collection<?> list) {
        if (ObjectUtil.isEmpty(list)) {
            return;
        }
        list.forEach(EnumDescHandler::fillEnumDesc);
    }

    /**
     * 填充分页对象枚举描述
     */
    public static void fillEnumDesc(Page<?> page) {
        if (page == null) {
            return;
        }
        fillEnumDesc(page.getRecords());
    }
}
