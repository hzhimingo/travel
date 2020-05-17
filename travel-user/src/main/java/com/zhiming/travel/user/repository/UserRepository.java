package com.zhiming.travel.user.repository;

import com.zhiming.travel.user.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {
    int isTelephoneHasBeenUsed(String telephone);
    int insertNewUser(UserDO record);
    UserDO selectUserById(Long userId);
    /**
     * @param userId 用户id
     * @return int
     * 用户是否存在
     */
    int isUserExist(Long userId);
    /**
     * @param userId 用户id
     * @return int 是否更新成功
     * 根据用户id将用户状态置为1（1-封禁状态）
     */
    int updateUserStatusToDisable(Long userId);

    /**
     * @param userId 用户id
     * @return int 是否更新成功
     * 根据用户id将用户状态置为0（0-正常状态）
     */
    int updateUserStatusToNormal(Long userId);
    /**
     * @param userId 用户id
     * @return int 是否删除成功
     * 删除或注销该用户
     */
    int deleteUserById(Long userId);

    /**
     * @param record 用户更新数据
     * @return int
     * 更新用户信息
     */
    int updateUserInfo(UserDO record);

    List<UserDO> selectUser(@Param("boundary") int boundary, @Param("offset") int offset, @Param("record") UserDO record);
}