package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @author qinfen
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
}
