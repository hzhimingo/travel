package com.zhiming.travel.oauth.model;

import lombok.Data;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
public class CustomUser implements AuthenticatedPrincipal, Serializable {

    private Long userId;
    private String nickname;
    private String mobile;
    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getName() {
        return userId.toString();
    }
}
