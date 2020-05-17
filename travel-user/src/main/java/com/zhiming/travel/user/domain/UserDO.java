package com.zhiming.travel.user.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号码
     */
    private String telephone;

    /**
     * 用户个人简介
     */
    private String introduction;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户生日
     */
    private LocalDate birthday;

    /**
     * 用户个人主页背景图
     */
    private String background;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    private Integer gender;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 个人主页浏览次数
     */
    private Integer visitedNum;

    /**
     * 状态（0-正常 1-封禁）
     */
    private Integer status;

    /**
     * 是否被删除（0-否 1-是）
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}