package com.buer.common.mybatis.tenant;

import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 获取tenantId
 *
 * @author zoulan
 * @since 2025-04-18
 */
public class MyTenantFactory implements TenantFactory {

    /**
     * 获取当前租户id列表，支持多个租户
     * 注意：新增无论tenantId值是什么，都会被返回的tenantId覆盖。返回多个tenantId，则默认使用第一个
     * 若返回的内容为null或者空数组，则保留对象中的tenantId值。
     */
    @Override
    public Object[] getTenantIds() {
        // 根据请求attributes获取tenantId
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Long tenantId = (Long) attributes.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST);
        return new Object[]{tenantId};
    }
}
