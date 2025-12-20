package com.buer.auth.biz.support.handle;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.data.model.AccessTokenModel;
import cn.dev33.satoken.oauth2.data.model.request.RequestAuthModel;
import cn.dev33.satoken.oauth2.exception.SaOAuth2Exception;
import cn.dev33.satoken.oauth2.granttype.handler.SaOAuth2GrantTypeHandlerInterface;

import java.util.List;

/**
 * 自定义 验证码 授权模式处理器认证过程
 * grantType = phone_code
 * 详情见官网
 *
 * @author zoulan
 * @since 2025-08-12
 */
public class PhoneCodeGrantTypeHandler implements SaOAuth2GrantTypeHandlerInterface {
    /**
     * 获取所要处理的 GrantType
     *
     * @return grantType
     */
    @Override
    public String getHandlerGrantType() {
        // 这里和应用的注册的grantType对应
        return "phone_code";
    }

    /**
     * 获取 AccessTokenModel 对象
     *
     * @param req      前端请求参数
     * @param clientId clientId
     * @param scopes   scopes
     * @return AccessTokenModel
     */
    @Override
    public AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes) {

        // 获取前端提交的参数
        String phone = req.getParamNotNull("phone");
        String code = req.getParamNotNull("code");
        String realCode = SaManager.getSaTokenDao()
            .get("phone_code:" + phone);

        // 1、校验验证码是否正确
        if (!code.equals(realCode)) {
            throw new SaOAuth2Exception("验证码错误");
        }

        // 2、校验通过，删除验证码
        SaManager.getSaTokenDao()
            .delete("phone_code:" + phone);

        // 3、登录
        long userId = 10001;

        // 4、构建 ra 对象
        RequestAuthModel ra = new RequestAuthModel();
        ra.clientId = clientId;
        ra.loginId = userId;
        ra.scopes = scopes;

        // 5、生成 Access-Token
        AccessTokenModel at = SaOAuth2Manager.getDataGenerate()
            .generateAccessToken(ra, true, atm -> atm.grantType = "phone_code");
        return at;
    }
}
