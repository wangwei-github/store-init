package com.example.demo2.controller;

import com.example.demo2.entity.Address;
import com.example.demo2.service.IAddressService;
import com.example.demo2.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("addnew")
    public JsonResult<Void> addNew(HttpSession session, Address address) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        addressService.addNewAddress(username, uid, address);
        return new JsonResult<>(SUCCESS);
    }

}
