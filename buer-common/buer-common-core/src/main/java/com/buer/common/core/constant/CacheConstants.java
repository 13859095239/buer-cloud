package com.buer.common.core.constant;

/**
 * 缓存的KEY，常量
 *
 * @author zoulan
 * @since 2023-06-08
 */
public interface CacheConstants {

    /**
     * sa-token 用户信息key
     */
    String LOGIN_USER_KEY = "loginUser";

    /**
     * sa-token USER_KEY
     */
    String USER_KEY = "userId";

    String PASSWORD_ERROR_KEY = "pwd_error_key";

    /**
     * 部门信息缓存
     */
    String DEPT_DETAILS = "dept_details";
    /**
     * 用户信息缓存
     */
    String USER_DETAILS = "user_details";

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 验证码有效期（秒）
     */
    long CAPTCHA_EXPIRATION = 120;

    /**
     * role key 前缀
     */
    String ROLE_KEY_PREFIX = "buerAuthorization:role-find-permission:";

    /**
     * roles key
     */
    String ROLES_KEY = "buerAuthorization:loginId-find-role:";


    /**
     * 配置缓存
     */
    String CONFIG = "config";

    /**
     * 字典缓存
     */
    String DICT = "dict";

    /**
     * OSS配置
     */
    String OSS_CONFIG = "sysOssConfig";
}
