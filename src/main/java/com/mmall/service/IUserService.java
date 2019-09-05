package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @author qinfen
 */
public interface IUserService {
    /**登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**校验用户名或邮箱
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);

    /**获取忘记密码的问题
     * @param username
     * @return
     */
    ServerResponse<String> forgetGetQuestion(String username);

    /**验证问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> forgetCheckAnswer(String username,String question,String answer);

    /**忘记密码重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
     ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);

    /**登录状态时重置密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
     ServerResponse<String> resetPassword(String passwordOld, String passwordNew,User user);

    /**修改用户信息
     * @param user
     * @return
     */
     ServerResponse<User> updateInformation(User user);

    /**获取用户信息
     * @param userId
     * @return
     */
     ServerResponse<User> getInformation(Integer userId);

    /**校验是否是超级管理员
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);
}
