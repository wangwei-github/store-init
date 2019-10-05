package com.example.demo2.mapper;

import com.example.demo2.entity.Address;

import java.util.List;

public interface AddressMapper {

    /**
     * 插入用户提交的地址数据
     *
     * @param address
     * @return
     */
    Integer insertAddress(Address address);

    /**
     * 查找单个用户所有地址
     *
     * @param
     * @return 单个用户地址数量
     */
    Integer countByUid(Integer uid);

    /**
     * 通过用户uid查询用户所有地址
     *
     * @return 用户所有地址
     */
    List<Address> findAddressByUid(Integer uid);
}
