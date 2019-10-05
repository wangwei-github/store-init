package com.example.demo2.service.impl;

import com.example.demo2.entity.Address;
import com.example.demo2.mapper.AddressMapper;
import com.example.demo2.service.IAddressService;
import com.example.demo2.service.ex.AddressCountLimitException;
import com.example.demo2.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    // 封装地址插入业务
    private void insertAddress(Address address) {
        // 插入地址数据
        Integer rows = addressMapper.insertAddress(address);
        if (rows != 1) {
            throw new InsertException("地址插入失败");
        }
    }

    // 封装查询地址数量业务
    private Integer countByUid(Integer uid) {
        return addressMapper.countByUid(uid);
    }

    @Override
    public void addNewAddress(String username, Integer uid, Address address) {
        // 查询收货地址数量，是否超过上限
        Integer count = countByUid(uid);
        if (count > ADDRESS_MAX_COUNT) {
            throw new AddressCountLimitException("地址添加达到上限" + ADDRESS_MAX_COUNT + "已添加：" + count + "个！");
        }
        //如果count等于0就设为默认0，否则设为1
        Integer isDefault = count == 0 ? 0 : 1;
        address.setIsDefault(isDefault);

        //TODO 城市名字
        Date now = new Date();
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(now);
        address.setModifiedTime(now);
        address.setUid(uid);
        //插入地址
        insertAddress(address);

    }

}
