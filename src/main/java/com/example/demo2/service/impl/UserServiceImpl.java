package com.example.demo2.service.impl;

import com.example.demo2.entity.User;
import com.example.demo2.mapper.UserMapper;
import com.example.demo2.service.IUserService;
import com.example.demo2.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    /**
     * 处理用户注册的实现类
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 处理注册业务
     */
    @Override
    public void reg(User user) throws UsernameDuplicateException, InsertException {
        // 根据参数user对象得到用户输入的用户名
        String username = user.getUsername();
        // 通过用户名查询是否存在
        User us = userMapper.findByUsername(username);
        // 如果不为空，用户名已占用抛出异常UsernameDupalicate
        if (us != null)
            throw new UsernameDuplicateException("注册失败！尝试注册用户名(" + username + ")已经存在!");

        // 可以注册
        // 得到盐值
        String salt = UUID.randomUUID().toString();
        // 加密user里面的密码
        String password = user.getPassword();
        String md5Password = getMd5Password(password, salt);
        // 将加密后的密码和盐值封装到user
        user.setSalt(salt);
        user.setPassword(md5Password);
        // 设置isDelete为0
        user.setIsDelete(0);
        // 封装创建人、时间，修改人、时间
        Date now = new Date();
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 通过新得到的user，执行插入操作
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("注册失败!写入数据时出现未知错误!请联系系统管理员!");
        }

    }

    /**
     * 处理登陆业务
     */
    @Override
    public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
        // 通过提交的用户名查找用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("用户名不存在");
        }
        // 获得查询到的isDelete
        if (user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户名标记为删除");
        }
        // 获得查询到的盐值和密码
        String salt = user.getSalt();
        // 通过提交的用户密码按注册时方式得到加密后密码在于查询的到的密码比较
        String md5Password = getMd5Password(password, salt);
        if (!user.getPassword().equals(md5Password)) {
            throw new PasswordNotMatchException("密码不匹配！");
        }
        user.setPassword(null);
        user.setSalt(null);
        user.setIsDelete(null);
        return user;
    }

    /**
     * 处理修改业务
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 查找并判断用户是否存在
        User user = userMapper.findByUid(uid);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }
        // 判断是否标记为删除
        if (user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户已删除");
        }

        // 判断密码是否正确
        String MD5OldPassword = getMd5Password(oldPassword, user.getSalt());
        String MD5NewPassword = getMd5Password(newPassword, user.getSalt());

        if (!MD5OldPassword.equals(user.getPassword())) {
            throw new PasswordNotMatchException("旧密码不正确");
        }
        // 更新密码
        Integer rows = userMapper.updateByUid(uid, MD5NewPassword, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新资料失败");
        }
    }

    /**
     * 通过uid查找用户信息
     *
     * @param uid 用户id
     * @return 用户数据
     */
    public User getUserInfo(Integer uid) {
        User user = userMapper.findByUid(uid);
        if (user != null) {
            user.setPassword(null);
            user.setSalt(null);
            user.setIsDelete(null);
        }
        return user;
    }

    public void changeInfo(User user) throws UserNotFoundException, UpdateException {
        // 通过uid查询用户
        User getUser = userMapper.findByUid(user.getUid());
        if (getUser == null) {
            throw new UserNotFoundException("修改用户不存在");
        }
        if (getUser.getIsDelete() == 1) {
            throw new UserNotFoundException("修改用户不存在");
        }
        // 创建当前时间对象
        Date now = new Date();
        user.setModifiedTime(now);
        Integer rows = userMapper.updateInfo(user);
        if (rows != 1) {
            throw new UpdateException("修改个人资料失败");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) throws UserNotFoundException, UpdateException {
        // 通过uid检查用户是否存在
        User result = userMapper.findByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("更新图片用户不存在");
        }

        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("更新图片用户不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        Integer rows = userMapper.updateAvatar(uid, avatar, username, now);
        if (rows != 1) {
            throw new UpdateException("更新图片失败");
        }

    }

    private String getMd5Password(String password, String salt) {
        // 规则：对password+salt进行3次加密
        String str = password + salt;
        for (int i = 0; i < 3; i++) {
            str = DigestUtils.md5DigestAsHex(str.getBytes());
        }
        return str;

    }

}
