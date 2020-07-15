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

    /**
     * 检验邮箱是否已经存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 查询用户的忘记密码提示问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);

    int checkPassword(@Param("password")String password,@Param("userId")Integer userId);

    /**
     * 对邮箱进行查重
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email")String email,@Param("userId")Integer userId);
}