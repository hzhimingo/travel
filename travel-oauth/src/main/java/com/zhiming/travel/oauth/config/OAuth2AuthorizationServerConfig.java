package com.zhiming.travel.oauth.config;

import com.zhiming.travel.oauth.granter.CustomPasswordTokenGranter;
import com.zhiming.travel.oauth.granter.SMSCodeTokenGranter;
import com.zhiming.travel.oauth.model.CustomUser;
import com.zhiming.travel.oauth.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    private JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey("123456");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("travel.key"),"123456".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("travel"));
        return converter;
    }

    @Bean
    public TokenKeyEndpoint tokenKeyEndpoint() {
        return new TokenKeyEndpoint(jwtTokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
        List<TokenGranter> tokenGranters = getTokenGranters(endpoints.getAuthorizationCodeServices(),
                endpoints.getTokenStore(), endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
        endpoints.tokenEnhancer(jwtTokenEnhancer());
    }

    //配置客户端应用
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
         //声明可以进行访问的客户端
        .withClient("TravelApp")
         //密码
        .secret(passwordEncoder().encode("123456"))
         //访问权限
        .scopes("read", "write")
         //过期时间
        .accessTokenValiditySeconds(1296000)
         //可以访问的服务
        .resourceIds("travel-gateway-service")
         //授权模式
        .authorizedGrantTypes("pwd", "sms", "refresh_token")
        .and()
        .withClient("travel-gateway-service")
        .secret(passwordEncoder().encode("123456"))
        .scopes("read", "write")
        .accessTokenValiditySeconds(1296000)
        .authorizedGrantTypes("password");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()");
    }

    private List<TokenGranter> getTokenGranters(AuthorizationCodeServices authorizationCodeServices, TokenStore tokenStore, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        return new ArrayList<>(Arrays.asList(
                new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory),
                new CustomPasswordTokenGranter(customUserDetailService, tokenServices, clientDetailsService, requestFactory),
                new SMSCodeTokenGranter(customUserDetailService, tokenServices, clientDetailsService, requestFactory)
        ));
    }
}
