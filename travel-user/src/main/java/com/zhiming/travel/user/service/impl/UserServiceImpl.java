package com.zhiming.travel.user.service.impl;

import com.zhiming.travel.api.client.SMSClient;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.dto.user.UserDTO;
import com.zhiming.travel.api.dto.user.UsersDTO;
import com.zhiming.travel.api.form.sms.VerifySMSCodeForm;
import com.zhiming.travel.api.form.user.RegisterUserForm;
import com.zhiming.travel.api.form.user.UpdateUserForm;
import com.zhiming.travel.api.form.user.VerifyUserForm;
import com.zhiming.travel.api.query.user.UserInfosQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.enums.SMSKeyEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.user.domain.UserDO;
import com.zhiming.travel.user.repository.UserRepository;
import com.zhiming.travel.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;

    @Value("${travel.service.machineId}")
    private Long machineId;

    @Value("${travel.user.defaultAvatar}")
    private String defaultAvatar;

    @Value("${travel.user.defaultBackground}")
    private String defaultBackground;

    private final SMSClient smsClient;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SMSClient smsClient) {
        this.smsClient = smsClient;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerNewUser(RegisterUserForm form) {
        //1. 判断手机号是否被使用注册
        int hasBeenRegistered = userRepository.isTelephoneHasBeenUsed(form.getTelephone());
        if (hasBeenRegistered != 0) {
            throw new TravelServiceException(ResultEnum.REGISTER_FAILED_MOBILE_USED);
        }
        //2.判断验证码是否校验成功
        VerifySMSCodeForm verifySMSCodeForm = new VerifySMSCodeForm();
        verifySMSCodeForm.setSmsCode(form.getSmsCode());
        verifySMSCodeForm.setSmsKey(SMSKeyEnum.USER_REGISTER_CODE.getKey());
        verifySMSCodeForm.setTelephone(form.getTelephone());
        smsClient.verifySMSCode(verifySMSCodeForm);
        //3.获取user_id
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        long userId = idGenerator.nextId();
        //4.设置用户基本信息
        UserDO record = new UserDO();
        record.setUserId(userId);
        record.setNickname("用户" + form.getTelephone());
        record.setTelephone(form.getTelephone());
        record.setAvatar(defaultAvatar);
        record.setBackground(defaultBackground);
        //5.判断注册类型，如果没有传入password默认为手机验证码注册，否则为web注册
        if (form.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String afterEncode = bCryptPasswordEncoder.encode(form.getPassword());
            record.setPassword(afterEncode);
        }
        //6.完成之后写入数据库
        userRepository.insertNewUser(record);
        //7.将数据查询出来
        UserDO userDO = userRepository.selectUserById(record.getUserId());
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public SimpleUserDTO obtainSimpleUser(Long userId) {
        UserDO userDO = userRepository.selectUserById(userId);
        if (userDO == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        } else {
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO();
            BeanUtils.copyProperties(userDO, simpleUserDTO);
            return simpleUserDTO;
        }
    }

    @Override
    public UserDTO obtainUserInfo(Long userId) {
        UserDO userDO = userRepository.selectUserById(userId);
        if (userDO == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        } else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDO, userDTO);
            return userDTO;
        }
    }

    @Override
    public PageDTO obtainUserInfos(UserInfosQuery query) {
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        //设置偏移量时，设置为2倍的offset, 以便后续检查是否有下一页
        UserDO record = new UserDO();
        BeanUtils.copyProperties(query, record);
        List<UserDO> users = userRepository.selectUser(boundary, offset, record);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setBoundary(boundary);
        pageDTO.setOffset(query.getOffset());
        if (users.size() > query.getOffset()) {
            pageDTO.setHasNext(true);
            users = users.subList(0, query.getOffset());
        } else {
            pageDTO.setHasNext(false);
        }
        List<UsersDTO> results = new ArrayList<>();
        for (UserDO item : users) {
            UsersDTO result = new UsersDTO();
            BeanUtils.copyProperties(item, result);
            results.add(result);
        }
        pageDTO.setData(results);
        return pageDTO;
    }

    @Override
    public boolean verifyUserInfo(VerifyUserForm form) {
        return false;
    }

    @Override
    public void deleteUser(Long userId) {
        int isUserExist = userRepository.isUserExist(userId);
        if (isUserExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        userRepository.deleteUserById(userId);
    }

    @Override
    public void makeUserEnable(Long userId) {
        int isUserExist = userRepository.isUserExist(userId);
        if (isUserExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        userRepository.updateUserStatusToNormal(userId);
    }

    @Override
    public void makeUserDisable(Long userId) {
        int isUserExist = userRepository.isUserExist(userId);
        if (isUserExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        userRepository.updateUserStatusToDisable(userId);
    }

    @Override
    public void updateUserInfo(UpdateUserForm form) {
        int isUserExist = userRepository.isUserExist(form.getUserId());
        if (isUserExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        UserDO record = new UserDO();
        BeanUtils.copyProperties(form, record);
        userRepository.updateUserInfo(record);
    }
}
