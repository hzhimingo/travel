package com.zhiming.travel.user.controller;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.dto.user.UserDTO;
import com.zhiming.travel.api.form.user.RegisterUserForm;
import com.zhiming.travel.api.form.user.UpdateUserForm;
import com.zhiming.travel.api.query.user.UserInfosQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/simple/{userId}")
    public Result<SimpleUserDTO> fetchSimpleUserInfo(@NotNull @PathVariable("userId") Long userId) {
        SimpleUserDTO result = userService.obtainSimpleUser(userId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/{userId}")
    public Result<UserDTO> fetchUserInfo(@NotNull @PathVariable("userId") Long userId) {
        UserDTO result = userService.obtainUserInfo(userId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/list")
    public Result<PageDTO> fetchUserList(UserInfosQuery query) {
        PageDTO result = userService.obtainUserInfos(query);
        return ResultUtil.ok(result);
    }

    @PostMapping("/")
    public Result<UserDTO> postNewUser(@RequestBody RegisterUserForm form) {
        UserDTO result = userService.registerNewUser(form);
        return ResultUtil.ok(result);
    }

    @PutMapping("/")
    public Result updateUserInfo(@RequestBody UpdateUserForm updateUserForm) {
        userService.updateUserInfo(updateUserForm);
        return ResultUtil.ok();
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@NotNull @PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResultUtil.ok();
    }

    @PutMapping("/unlock/{userId}")
    public Result enableUser(@NotNull @PathVariable("userId") Long userId) {
        userService.makeUserEnable(userId);
        return ResultUtil.ok();
    }

    @PutMapping("/lock/{userId}")
    public Result disableUser(@NotNull @PathVariable("userId") Long userId) {
        userService.makeUserDisable(userId);
        return ResultUtil.ok();
    }
}
