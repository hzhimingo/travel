package com.zhiming.travel.oauth.repository;

import com.zhiming.travel.oauth.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository {
    UserDO selectUserByMobile(String mobile);
    UserDO selectUserByEmail(String email);
    UserDO selectUserByMobileAndPwd(@Param("mobile") String mobile, @Param("password") String password);
}
