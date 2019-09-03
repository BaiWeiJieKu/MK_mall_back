package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 校验用户名是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 返回登录的角色信息
     * @param username
     * @param passowrd
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String passowrd);
}