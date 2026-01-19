package com.buer.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.buer.common.core.entity.BaseEnum;

/**
 * BaseEnum 转换器
 * <p>
 * 用于将实现了 BaseEnum 接口的枚举类型转换为字符串（使用 getDesc() 方法）
 * 所有继承 BaseEnum 的枚举都可以使用此转换器
 * </p>
 * <p>
 * 使用方法：在字段的 @ExcelProperty 注解中添加 converter = BaseEnumConverter.class
 * </p>
 *
 * @author system
 * @since 2026-01-18
 */
public class BaseEnumConverter implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        if (value instanceof BaseEnum) {
            return new WriteCellData<>(((BaseEnum<?>) value).getDesc());
        }
        return new WriteCellData<>(value.toString());
    }
}
