package com.zhiming.travel.oauth.service;

import com.zhiming.travel.oauth.model.CustomUser;
import com.zhiming.travel.oauth.domain.UserDO;
import com.zhiming.travel.oauth.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomUserDetailService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomUser loadUserByPassword(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            System.out.println("1");
            throw new InvalidGrantException("您输入的手机号或短信验证码不正确");
        }
        UserDO userDO = null;
        if (isMobile(username)) {
            userDO = userRepository.selectUserByMobile(username);
        }
        if (isEmail(username)) {
            userDO = userRepository.selectUserByEmail(username);
        }
        if (userDO == null) {
            System.out.println(2);
            throw new InvalidGrantException("用户名或者密码错误");
        }
        // 判断成功后返回用户细节
        if (!passwordEncoder.matches(password, userDO.getPassword())) {
            System.out.println(3);
            throw new InvalidGrantException("用户名或者密码错误");
        }
        CustomUser customUser = new CustomUser();
        customUser.setUserId(userDO.getUserId());
        customUser.setEmail(userDO.getEmail());
        customUser.setNickname(userDO.getNickname());
        customUser.setAvatar(userDO.getAvatar());
        customUser.setMobile(userDO.getTelephone());
        customUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,root"));
        return customUser;
    }

    public CustomUser loadUserByMobileAndSMSCode(String smsKey, String mobile, String smsCode) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(smsKey)) {
            throw new InvalidGrantException("您输入的手机号或短信验证码不正确");
        }
        String key = smsKey + "_" + mobile;
        String storedCheckCode = redisTemplate.opsForValue().get(key);
        if (storedCheckCode == null) {
            throw new InvalidGrantException("您输入的手机号或短信验证码不正确");
        }
        if (!storedCheckCode.equals(smsCode)) {
            throw new InvalidGrantException("您输入的手机号或短信验证码不正确");
        }
        UserDO userDO = userRepository.selectUserByMobile(mobile);
        // 判断成功后返回用户细节
        CustomUser customUser = new CustomUser();
        customUser.setUserId(userDO.getUserId());
        customUser.setEmail(userDO.getEmail());
        customUser.setNickname(userDO.getNickname());
        customUser.setAvatar(userDO.getAvatar());
        customUser.setMobile(userDO.getTelephone());
        customUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,root"));
        return customUser;
    }

    private boolean isMobile(String mobile) {
        Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
        Matcher m = regex .matcher(mobile);
        return m.matches();
    }

    private boolean isEmail(String email) {
        Pattern regex = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

}
