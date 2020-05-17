package com.zhiming.travel.oauth.granter;

import com.zhiming.travel.oauth.model.CustomUser;
import com.zhiming.travel.oauth.service.CustomUserDetailService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class CustomPasswordTokenGranter extends AbstractCustomTokenGranter {

    private final CustomUserDetailService customUserDetailService;

    public CustomPasswordTokenGranter(CustomUserDetailService customUserDetailService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "pwd");
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected CustomUser getCustomUser(Map<String, String> parameters) {
        String username = parameters.get("username");
        String password = parameters.get("password");
        return customUserDetailService.loadUserByPassword(username, password);
    }
}
