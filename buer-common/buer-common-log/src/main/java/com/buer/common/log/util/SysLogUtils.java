package com.buer.common.log.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.buer.common.core.constant.CommonConstants;
import com.buer.common.log.enums.LogTypeEnum;
import com.buer.common.security.util.SecurityUtils;
import com.buer.system.api.entity.SysLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * 系统日志工具类
 *
 * @author zoulan
 * @since 2023-06-17
 */
@UtilityClass
public class SysLogUtils {

    /**
     * 获取当前请求系统日志对象
     *
     * @return SysLog
     */
    public SysLog getSysLog(ProceedingJoinPoint point, com.buer.common.log.annotation.SysLog sysLogAnnotation) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String method = request.getMethod();
        SysLog log = new SysLog()
                .setTitle(sysLogAnnotation.value())
                .setType(LogTypeEnum.NORMAL.getType())
                .setRemoteAddr(JakartaServletUtil.getClientIP(request))
                .setRequestUri(URLUtil.getPath(request.getRequestURI()))
                .setMethod(method)
                .setUserAgent(request.getHeader(HttpHeaders.USER_AGENT))
                .setParams(HttpUtil.toParams(request.getParameterMap()))
                .setServiceName(SpringUtil.getApplicationName())
                .setClientId(StrUtil.toString(StpUtil.getExtra(CommonConstants.CLIENT_Id)));
        log.setCreateUser(SecurityUtils.getUserIdAsString());
        log.setUpdateUser(SecurityUtils.getUserIdAsString());
        String[] excludeParamNames = sysLogAnnotation.excludeParamNames();
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean isArgs = MapUtil.isEmpty(request.getParameterMap())
                && HttpMethod.PUT.name()
                .equals(method)
                || HttpMethod.POST.name()
                .equals(method);
        if (sysLogAnnotation.isSaveRequestData()) {
            if (isArgs) {
                String params = argsArrayToString(point.getArgs(), excludeParamNames);
                log.setParams(StringUtils.substring(params, 0, 2000));
            } else {
                MapUtil.removeAny(parameterMap, excludeParamNames);
                log.setParams(StringUtils.substring(HttpUtil.toParams(parameterMap), 0, 2000));
            }
        }
        return log;
    }

    /**
     * 获取客户端
     *
     * @return clientId
     */
    private String getClientId() {
        return null;
    }

    /**
     * 处理 Annotation ProceedingJoinPoint args对象
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        for (String excludeParamName : excludeParamNames) {
            for (Object object : paramsArray) {
                //处理非数组
                if (!ArrayUtil.isArray(object)) {
                    Field[] fields = ReflectUtil.getFields(object.getClass());
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (StrUtil.equalsAnyIgnoreCase(fieldName, excludeParamName)) {
                            ReflectUtil.setFieldValue(object, fieldName, null);
                        }
                    }
                }
            }
        }
        return ArrayUtil.toString(paramsArray);
    }
}
