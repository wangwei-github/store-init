package com.example.demo2.mapper;

import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 处理用户数据的持久层接口
 *
 * @author soft01
 */
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user 用户数据对象
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 通过uid,username,password,modifiedUser,modifiedTime修改密码
     *
     * @return
     */
    Integer updateByUid(@Param("uid") Integer uid, @Param("password") String password,
                        @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 通过uid,avatar,,modifiedUser,modifiedTime修改用户图像
     *
     * @return
     */
    Integer updateAvatar(@Param("uid") Integer uid, @Param("avatar") String avatar,
                         @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 更新用户资料
     *
     * @param user
     * @return
     */
    Integer updateInfo(User user);

    /**
     * 根据用户uid查找用户数据
     *
     * @param uid
     * @return
     */
    User findByUid(Integer uid);

    /**
     * 根据用户名查询用户数据
     *
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

}
