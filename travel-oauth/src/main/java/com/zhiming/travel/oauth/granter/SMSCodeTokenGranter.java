package com.zhiming.travel.oauth.granter;

import com.zhiming.travel.oauth.model.CustomUser;
import com.zhiming.travel.oauth.service.CustomUserDetailService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class SMSCodeTokenGranter extends AbstractCustomTokenGranter {

    protected CustomUserDetailService customUserDetailService;

    public SMSCodeTokenGranter(CustomUserDetailService customUserDetailService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "sms");
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected CustomUser getCustomUser(Map<String, String> parameters) {
        String mobile = parameters.get("mobile");
        String smsCode = parameters.get("smsCode");
        String smsKey = parameters.get("smsKey");
        return customUserDetailService.loadUserByMobileAndSMSCode(smsKey, mobile, smsCode);
    }
}
