package com.zhiming.travel.user.service;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.form.user.RegisterUserForm;
import com.zhiming.travel.api.form.user.UpdateUserForm;
import com.zhiming.travel.api.form.user.VerifyUserForm;
import com.zhiming.travel.api.query.user.UserInfosQuery;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.dto.user.UserDTO;

/**
 * @author HuangZhiMing
 */
public interface UserService {

    /**
     * @param form 用户注册表单
     * @return UserDTO
     * 用户注册
     */
    UserDTO registerNewUser(RegisterUserForm form);

    /**
     * @param userId 用户id
     * @return SimpleUserDTO
     * 获取简略的用户信息
     */
    SimpleUserDTO obtainSimpleUser(Long userId);

    /**
     * @param userId 用户id
     * @return UserDTO
     * 获取用户信息
     */
    UserDTO obtainUserInfo(Long userId);

    /**
     * @param query 用户列表查询
     * @return PageDTO
     * 获取用户列表
     */
    PageDTO obtainUserInfos(UserInfosQuery query);

    /**
     * @param form 用户校验表单
     * @return boolean
     * 校验用户
     */
    boolean verifyUserInfo(VerifyUserForm form);

    /**
     * @param userId 用户id
     * 注销用户
     */
    void deleteUser(Long userId);

    /**
     * @param userId 用户id
     * 解封用户
     */
    void makeUserEnable(Long userId);

    /**
     * @param userId 用户id
     * 封禁用户
     */
    void makeUserDisable(Long userId);

    /**
     * @param form 需要更新的用户信息
     * 更新用户的基本信息
     */
    void updateUserInfo(UpdateUserForm form);
}
