package com.zhiming.travel.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//@Configuration
//@EnableResourceServer
public class GatewaySecurityConfig {
//extends ResourceServerConfigurerAdapter
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("travel-gateway-service");
//    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/authorize/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/qa/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/topic/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/spot/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/moment/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/picture/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/cr/**").permitAll()
//                .antMatchers("/user/list").authenticated()
//                .anyRequest().authenticated();
//    }
}
