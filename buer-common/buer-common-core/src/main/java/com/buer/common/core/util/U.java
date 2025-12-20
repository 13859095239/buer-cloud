package com.buer.common.core.util;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 便捷工具类
 *
 * @author zoulan
 * @since 2023-05-08
 */
public class U {
    /**
     * @param ids                id字符串，多个以逗号隔开
     * @param errorContentPrefix 当ids为空时报错，提示异常的前缀
     * @return id列表
     */
    public static List<String> getIdListByStringWithEmptyThrow(String ids, String errorContentPrefix) {
        List<String> idList = StringUtils.arrayBySplit(ids);
        Assert.isTrue(idList != null && !idList.isEmpty(), errorContentPrefix + "不能为空!");
        return idList;
    }

    /**
     * @param ids id字符串，多个以逗号隔开
     * @return id列表, 为null是返回new ArrayList<>()
     */
    public static List<String> getIdListByStringWithDefault(String ids) {
        List<String> idList = StringUtils.arrayBySplit(ids);
        if (idList == null) {
            idList = new ArrayList<>();
        }
        return idList;
    }
}