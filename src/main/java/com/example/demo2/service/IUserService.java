package com.example.demo2.service;

import com.example.demo2.entity.User;
import com.example.demo2.service.ex.*;

/**
 * 用于处理业务层的接口
 *
 * @author soft01
 */
public interface IUserService {
    /**
     * 用户注册
     *
     * @throws UsernameDuplicateException 用户名占用异常
     * @throws InsertException            数据插入异常
     */
    void reg(User user) throws UsernameDuplicateException, InsertException;

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     * @throws UserNotFoundException     用户未找到异常
     * @throws PasswordNotMatchException 密码不匹配异常
     */
    User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;

    /**
     * 更改密码
     *
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @throws UserNotFoundException
     * @throws UpdateException
     */
    /**
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     * @throws UserNotFoundException
     * @throws UpdateException
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword)
            throws UserNotFoundException, UpdateException;

    /**
     * 修改用户图片
     *
     * @throws UserNotFoundException
     * @throws UpdateException
     */
    void changeAvatar(Integer uid, String username, String avatar) throws UserNotFoundException, UpdateException;

    /**
     * 修改用户资料
     *
     * @param user 用户提交的信息
     * @throws UserNotFoundException
     * @throws UpdateException
     */
    void changeInfo(User user) throws UserNotFoundException, UpdateException;

    /**
     * 浏览器通过uid获取用户信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    User getUserInfo(Integer uid);
}
