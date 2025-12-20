package com.buer.common.core.config;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.buer.common.core.entity.R;
import com.buer.common.core.exception.CaptchaException;
import com.buer.common.core.exception.CheckException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * RestControllerAdvice配置
 * 用于全局异常处理器
 *
 * @author zoulan
 * @since 2024-09-15
 */
@ControllerAdvice
@RestControllerAdvice
public class ControllerAdviceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdviceConfiguration.class);

    /**
     * 验证异常
     */
    @ExceptionHandler({CheckException.class})
    public R checkException(CheckException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("验证异常,请求地址:{},验证失败:{}", requestUri, ex.getMessage());
        return R.fail(HttpStatus.HTTP_INTERNAL_ERROR, ex.getMessage());
    }

    /**
     * 验证码异常
     */
    @ExceptionHandler(CaptchaException.class)
    public R captchaException(CaptchaException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("验证码异常,请求地址:{},验证码验证失败:{}", requestUri, ex.getMessage());
        return R.fail(HttpStatus.HTTP_INTERNAL_ERROR, "验证码错误");
    }

    /**
     * 请求参数异常,处理业务校验过程中碰到的非法参数异常,该异常基本由
     *
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("请求参数异常,请求地址:{},非法参数:{}", requestUri, ex.getMessage(), ex);
        return R.fail(ex.getMessage());
    }

    /**
     * 请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("请求方式不支持异常,请求地址:{},不支持请求:{}", requestUri, ex.getMethod(), ex);
        return R.fail(StrUtil.format("请求方式不支持异常,请求地址:{},不支持请求:{}", requestUri, ex.getMethod()));
    }

    /**
     * 请求缺少必需的路径变量异常
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R missingPathVariableException(MissingPathVariableException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.error("请求缺少必需的路径变量异常,请求地址:{}", requestUri, ex);
        return R.fail(StrUtil.format("请求缺少必需的路径变量异常,请求地址:{},路径变量[{}]", requestUri, ex.getVariableName()));
    }

    /**
     * 请求参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                 HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.error("请求参数类型不匹配异常,请求地址:{}", requestUri, ex);
        return R.fail(StrUtil.format("请求参数类型不匹配,请求地址:{}参数[{}]要求类型为:{},但输入值为:{}",
                requestUri,
                ex.getName(),
                ex.getRequiredType()
                        .getName(),
                ex.getValue()));
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                             HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors();
        logger.warn("参数绑定异常,请求地址:{},ex = {}", requestUri, fieldErrors.getFirst()
                .getDefaultMessage());
        return R.fail(StrUtil.format("参数绑定异常,请求地址:{},{} {}", requestUri, fieldErrors.getFirst()
                .getField(), fieldErrors.getFirst()
                .getDefaultMessage()));
    }

    /**
     * 参数异常 (以form-data形式传参)
     */
    @ExceptionHandler(BindException.class)
    public R bindException(BindException ex,
                           HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors();
        logger.warn("参数绑定异常,请求地址:{},ex:{}", requestUri, fieldErrors.getFirst()
                .getDefaultMessage());
        return R.fail(StrUtil.format("参数绑定异常,请求地址:{}", requestUri));
    }

    /**
     * 请求路径不存在，404异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public R noResourceFoundException(NoResourceFoundException ex,
                                      HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.error("请求路径不存在,请求地址:{},ex:{}", requestUri, ex.getMessage());
        return R.fail(HttpStatus.HTTP_NOT_FOUND, StrUtil.format("请求路径不存在,请求地址:{}", requestUri));
    }

    /**
     * 系统未知异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.error("系统未知异常,请求地址:{}", requestUri, ex);
        return R.fail(HttpStatus.HTTP_INTERNAL_ERROR, StrUtil.format("系统未知异常,请求地址:{}", requestUri));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeException(RuntimeException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.error("运行时异常,请求地址:{}", requestUri, ex);
        return R.fail(StrUtil.format("运行时异常,请求地址:{}", requestUri));
    }

    /**
     * 鉴权异常
     */
    @ExceptionHandler(SaTokenException.class)
    public R saTokenException(SaTokenException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (ex instanceof NotPermissionException exception) {
            // 未能通过权限认证校验
            logger.error(StrUtil.format("鉴权异常,请求地址:{},无{}权限", requestUri, exception.getPermission()), ex);
            return R.fail(StrUtil.format("鉴权异常,请求地址:{},无{}权限", requestUri, exception.getPermission()));
        }
        // 其他异常,详情见saToken官网
        logger.error("鉴权异常,请求地址:{},错误代码:{},错误信息:{}", requestUri, ex.getCode(), ex.getMessage(), ex);
        return R.fail(StrUtil.format("鉴权异常,请求地址:{},错误代码:{}", requestUri, HttpStatus.HTTP_INTERNAL_ERROR));
    }

}
