package com.example.demo2.service;

import com.example.demo2.entity.Address;

/**
 * 处理收货地址数据业务层接口
 *
 * @author soft01
 */
public interface IAddressService {
    /**
     * 添加收货地址上限
     */
    int ADDRESS_MAX_COUNT = 30;

    /**
     * 添加用户地址
     *
     * @param username
     * @param uid
     * @param address
     */
    void addNewAddress(String username, Integer uid, Address address);

}
